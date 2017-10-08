package view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import model.Profile;
import model.SerializationUtil;
import model.Users;

public class SplashController implements Initializable {

	private static final String usersDirMac = System.getProperty("user.home") 
			+ "/Library/Application Support/Bias/users.ser";

	private static final String usersDirWin = System.getenv("APPDATA") + "\\Bias\\users.ser";

	private Users users = new Users();

	private Profile userProfile = new Profile();

	@FXML
	private ImageView splash;

	@FXML
	private JFXButton getStarted;

	@FXML
	private AnchorPane root;

	public void getStarted(ActionEvent e) throws IOException {
		TranslateTransition tt = new TranslateTransition(Duration.millis(500), splash);
		tt.setFromY(0);
		tt.setToY(-700);
		tt.play();
		tt.setOnFinished(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0) {
				try {
					AnchorPane pane;
					pane = FXMLLoader.load(getClass().getResource("/view/Profile.fxml"));
					root.getChildren().setAll(pane);
				} catch (IOException e) {
					e.printStackTrace();
				}}});	
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

			userProfile = users.getActiveUser();

			if (userProfile.isSplashActive()) {
				userProfile.setSplashActive(false);
				TranslateTransition tt = new TranslateTransition(Duration.millis(500), splash);
				FadeTransition fade = new FadeTransition(Duration.millis(500), splash);
				fade.setFromValue(0);
				fade.setToValue(1);
				fade.setDelay(Duration.millis(50));
				tt.setDelay(Duration.millis(50));
				tt.setFromY(700);
				tt.setToY(0);
				tt.play();
				fade.play();
			}

			if (os.contains("MAC")) 
				SerializationUtil.serialize(users, usersDirMac);
			else if (os.contains("WIN")) 
				SerializationUtil.serialize(users, usersDirWin);		
		} catch (Exception e) {
			//e.printStackTrace();
		}
	}
}