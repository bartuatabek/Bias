package view;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import application.UserIcon;
import javafx.animation.Animation.Status;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;
import model.Profile;
import model.SerializationUtil;
import model.Settings;
import model.Users; 

public class LoginController implements Initializable {

	private static final String usersDirMac = System.getProperty("user.home") 
			+ "/Library/Application Support/Bias/users.ser";
	
	private static final String usersDirWin = System.getenv("APPDATA") + "\\Bias\\users.ser";

	private Users users = new Users();

	private Profile userProfile = new Profile();

	private Settings settings = new Settings();

	private String settingsDir;

	private boolean fade_allowed = true;

	private boolean buffer_enabled = false;

	private boolean new_user_buffer = false;

	@FXML
	private MediaView background;

	@FXML
	private Pane blurPane;

	@FXML
	private Pane splash;

	@FXML
	private JFXTextField username;

	@FXML
	private JFXPasswordField password;

	@FXML
	private ImageView login;

	@FXML
	private ImageView back;

	@FXML
	private ImageView cancel;

	@FXML
	private ImageView switch_user;

	@FXML
	private ImageView new_user;

	@FXML
	private VBox newUserBox;

	@FXML
	private HBox userbox;

	@FXML
	private HBox passbox;

	@FXML
	private HBox buttonBox;

	@FXML
	private HBox splash_continue;

	@FXML
	private Label signup_login;

	@FXML
	private AnchorPane root;

	public void cancel(MouseEvent e) {

		blurPane.setEffect(null);
		fade_allowed = true;
		buffer_enabled = false;
		new_user_buffer = false;

		password.setText("");
		username.setText("");
		username.setStyle("-fx-text-fill: white; -fx-border-color: white; -fx-border-radius: 10; -fx-padding: 10");
		password.setStyle("-fx-text-fill: white; -fx-border-color: white; -fx-border-radius: 10; -fx-padding: 10");

		if (passbox.getLayoutY() != 320)
			passbox.setLayoutY(320);

		if (signup_login.getText().equals("Sign Up"))
			signup_login.setText("Login");

		if (users.getUsers().isEmpty()) {
			newUserBox.setVisible(false);
			splash.setVisible(true);
			username.setVisible(false);
			buttonBox.setVisible(false);
			passbox.setVisible(false);
			userbox.getChildren().clear();
			FadeTransition ft = new FadeTransition(Duration.seconds(5), splash);
			ft.setFromValue(0.0);
			ft.setToValue(1);
			ft.setCycleCount(1);
			ft.play();
		}

		else {
			users.setOffline();

			for (Profile user : users.getUsers()) {
				try {
					userbox.getChildren().clear();
					userbox.getChildren().add(new UserIcon(user.getProfilePic(), user.getUsername()));
				} catch (IOException e1) {
					//e1.printStackTrace();
				}
			}
			newUserBox.setVisible(true);
			back.setVisible(true);
			username.setVisible(false);
			buttonBox.setVisible(false);
			passbox.setVisible(false);
		}
	}

	public void newUser(MouseEvent e) throws IOException {

		new_user_buffer = true;
		
		if (splash.isVisible()) {
			FadeTransition fade = new FadeTransition(Duration.seconds(0.5), blurPane);
			fade.setFromValue(1);
			fade.setToValue(0.5);
			fade.setAutoReverse(true);
			fade.setCycleCount(2);
			fade.play();
			FadeTransition ft = new FadeTransition(Duration.seconds(1), splash);
			ft.setToValue(0.0);
			ft.setCycleCount(1);
			ft.play();
			ft.statusProperty().addListener(new ChangeListener<Status>() {
				@Override
				public void changed(ObservableValue<? extends Status> observableValue,
						Status oldValue, Status newValue) {
					if (newValue==Status.STOPPED){
						splash.setVisible(false);
						newUserBox.setVisible(false);
						passbox.setLayoutY(400);
						username.setVisible(true);
						username.requestFocus();
						passbox.setVisible(true);
						signup_login.setText("Sign Up");
						buttonBox.setVisible(true);
						switch_user.setOpacity(0.5);
						switch_user.setDisable(true);
						back.setVisible(false);
						try {
							userbox.getChildren().add(new UserIcon("", ""));
						} catch (IOException e) {
							//e.printStackTrace();
						}
					}}});
		}

		else {
			userbox.getChildren().clear();
			username.setVisible(true);
			username.requestFocus();
			newUserBox.setVisible(false);
			signup_login.setText("Sign Up");
			passbox.setLayoutY(400);
			passbox.setVisible(true);
			buttonBox.setVisible(true);
			back.setVisible(false);
			try {
				userbox.getChildren().add(new UserIcon("", ""));
			} catch (IOException e1) {
				//e1.printStackTrace();
			}
		}

		blurPane.setEffect(new BoxBlur(60,60,3));
	}

	@FXML
	public void login_signup(MouseEvent e) throws IOException {

		new_user_buffer = false;
		
		if (signup_login.getText().equals("Login")) {

			if (userProfile.getPassword().equals(password.getText())) {
				AnchorPane pane = FXMLLoader.load(getClass().getResource("/view/Dashboard.fxml"));
				root.getChildren().setAll(pane);
				userCheck.stop();
			}

			else {
				ShakeTransition anim = new ShakeTransition(passbox);
				anim.playFromStart();
				password.selectAll();
			}
		}

		if (signup_login.getText().equals("Sign Up")) {
			boolean clean = true;

			for (Profile user : users.getUsers()) {
				if (user.getUsername().equals(username.getText())) {
					username.setStyle("-fx-text-fill: white; -fx-border-color: #FF3B30; -fx-border-radius: 10; -fx-padding: 10");
					password.setStyle("-fx-text-fill: white; -fx-border-color: #FF3B30; -fx-border-radius: 10; -fx-padding: 10");
					ShakeTransition err1 = new ShakeTransition(username);
					ShakeTransition err2 = new ShakeTransition(passbox);
					err1.playFromStart();
					err2.playFromStart();
					clean = false;
				}
			}

			if (username.getText().isEmpty() || password.getText().isEmpty()) {
				username.setStyle("-fx-text-fill: white; -fx-border-color: #FF3B30; -fx-border-radius: 10; -fx-padding: 10");
				password.setStyle("-fx-text-fill: white; -fx-border-color: #FF3B30; -fx-border-radius: 10; -fx-padding: 10");
				ShakeTransition err1 = new ShakeTransition(username);
				ShakeTransition err2 = new ShakeTransition(passbox);
				err1.playFromStart();
				err2.playFromStart();
				clean = false;
			}

			if (clean) {
				userProfile = new Profile();
				settings = new Settings();

				userProfile.setUsername(username.getText());
				userProfile.setPassword(password.getText());			
				users.newUser(userProfile);

				userProfile.setOnline(true);

				String os = System.getProperty("os.name").toUpperCase();

				if (os.contains("MAC")) {
					String strDirectory = System.getProperty("user.home") + "/Library/Application Support/Bias/" 
							+ userProfile.getDirectory();

					File file = new File(strDirectory);

					if (!file.exists() && !file.isDirectory()) {
						(new File(strDirectory)).mkdirs();
					}
					
					settingsDir = System.getProperty("user.home") + "/Library/Application Support/Bias/" 
							+ userProfile.getDirectory()  +"/settings.ser";
				}
				else if (os.contains("WIN")) {
					String strDirectory = System.getenv("APPDATA") + "\\" + "Bias\\" + userProfile.getDirectory();

					File file = new File(strDirectory);

					if (!file.exists() && !file.isDirectory()) {
						(new File(strDirectory)).mkdirs();
					}
					
					settingsDir = System.getenv("APPDATA") + "\\Bias\\" + userProfile.getDirectory() + "\\settings.ser";
				}

				try {
					new SerializationUtil();
					if (os.contains("MAC")) 
						SerializationUtil.serialize(users, usersDirMac);
					else if (os.contains("WIN")) 
						SerializationUtil.serialize(users, usersDirWin);	
					SerializationUtil.serialize(settings, settingsDir);
				} catch (IOException e1) {
					System.out.println("Serialization Failed");
					e1.printStackTrace();
				}

				AnchorPane pane = FXMLLoader.load(getClass().getResource("/view/Splash.fxml"));
				root.getChildren().setAll(pane);
				userCheck.stop();
			}
		}
	} 

	public void loginWithKey(KeyEvent e) throws IOException {
		
		new_user_buffer = false;
		
		if (signup_login.getText().equals("Login") && e.getCode().equals(KeyCode.ENTER)) 
			if (userProfile.getPassword().equals(password.getText())) {

				AnchorPane pane = FXMLLoader.load(getClass().getResource("/view/Dashboard.fxml"));
				root.getChildren().setAll(pane);
				userCheck.stop();
			}

			else {
				ShakeTransition anim = new ShakeTransition(passbox);
				anim.playFromStart();
				password.selectAll();
			}
	}

	Timeline userCheck = new Timeline(new KeyFrame(Duration.seconds(0.1), new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent event) {

			if (!new_user_buffer) {

				ObservableList<Node> user_list = userbox.getChildren();

				for (int i = 0; i < user_list.size(); i++) {
					if (((UserIcon) user_list.get(i)).isSelected()) {
						userProfile = users.setActiveUser(i);
					}
				}

				if (users.hasActiveUser() && !buffer_enabled) {

					userbox.getChildren().clear();

					try {
						userbox.getChildren().add(new UserIcon(userProfile.getProfilePic(), userProfile.getUsername()));
					} catch (IOException e) {
						//e.printStackTrace();
					}

					if (fade_allowed) {
						FadeTransition fade = new FadeTransition(Duration.seconds(0.5), blurPane);
						fade.setFromValue(1);
						fade.setToValue(0.75);
						fade.setAutoReverse(true);
						fade.setCycleCount(2);
						fade.play();
						fade_allowed = false;

						try {
							new SerializationUtil();
							String os = System.getProperty("os.name").toUpperCase();

							if (os.contains("MAC")) 
								SerializationUtil.serialize(users, usersDirMac);
							else if (os.contains("WIN")) 
								SerializationUtil.serialize(users, usersDirWin);		
						} catch (IOException e) {
							System.out.println("Serialization Failed");
							//e.printStackTrace();
						}
					}

					blurPane.setEffect(new BoxBlur(60,60,3));
					newUserBox.setVisible(false);
					buttonBox.setVisible(true);

					String os = System.getProperty("os.name").toUpperCase();

					if (os.contains("MAC")) {
						settingsDir = System.getProperty("user.home") + "/Library/Application Support/Bias/" 
								+ userProfile.getDirectory()  +"/settings.ser";
					}
					else if (os.contains("WIN")) {
						settingsDir = System.getenv("APPDATA") + "\\Bias\\" + userProfile.getDirectory() + "\\settings.ser";
					}

					try {
						new SerializationUtil();
						settings = (Settings) SerializationUtil.deserialize(settingsDir);			
					} catch (ClassNotFoundException | IOException e) {
						//e.printStackTrace();
					}

					if (settings.getRememberMe()) {
						password.setText(userProfile.getPassword());
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								login.requestFocus();
							}});				
					} passbox.setVisible(true);
					buffer_enabled = true;
				}}}}));

	public void selectUser() {
		userCheck.setCycleCount(Timeline.INDEFINITE);
		userCheck.play();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		try {
			new SerializationUtil();
			String os = System.getProperty("os.name").toUpperCase();

			if (os.contains("MAC")) 
				users = (Users) SerializationUtil.deserialize(usersDirMac);
			else if (os.contains("WIN")) 
				users = (Users) SerializationUtil.deserialize(usersDirWin);
			
			users.setOffline();	
			for (Profile user : users.getUsers()) {
				userbox.getChildren().add(new UserIcon(user.getProfilePic(), user.getUsername()));
			}
		} catch (ClassNotFoundException | IOException e) {
			//e.printStackTrace();
		}

		for (Profile user : users.getUsers()) {
			if (user.getKeepLogged()) {
				try {
					user.setOnline(true);
					AnchorPane pane;
					pane = FXMLLoader.load(getClass().getResource("/view/Dashboard.fxml"));
					root.getChildren().setAll(pane);
				} catch (IOException e) {
					e.printStackTrace();
				}}
		}

		if (users.getUsers().isEmpty()) {
			newUserBox.setVisible(false);
			splash.setVisible(true);
			passbox.setVisible(false);
			FadeTransition ft = new FadeTransition(Duration.seconds(5), splash);
			ft.setFromValue(0.0);
			ft.setToValue(1);
			ft.setCycleCount(1);
			ft.play();
		}

		selectUser();

		// initialize the media player
		MediaPlayer player = new MediaPlayer(new Media(getClass().getResource("/resources/Plexus.mp4").toExternalForm()));
		player.setAutoPlay(true);
		player.setCycleCount(MediaPlayer.INDEFINITE);
		background.setMediaPlayer(player);
	}
}