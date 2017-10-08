package view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import org.controlsfx.control.ToggleSwitch;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import model.CustomEvent;
import model.EnrolledCourses;
import model.Profile;
import model.QuizzesAndPresentations;
import model.SerializationUtil;
import model.Settings;
import model.Users;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * Profile.fxml Controller Class
 */
public class ProfileController implements Initializable {

	private static final String usersDirMac = System.getProperty("user.home") 
			+ "/Library/Application Support/Bias/users.ser";
	
	private static final String usersDirWin = System.getenv("APPDATA") + "\\Bias\\users.ser";
	
	private Users users = new Users();

	private Profile userProfile = new Profile();

	private EnrolledCourses courses = new EnrolledCourses();

	private Settings settings = new Settings();

	private CustomEvent ce = new CustomEvent();

	private String courseDir;

	private String settingsDir;

	private String eventsDir;

	private String qpDir;

	private double xOffset = 0;

	private double yOffset = 0;

	@FXML
	private ImageView homeButtonP;

	@FXML
	private ImageView calendarButtonP;

	@FXML
	private ImageView lectureButtonP;

	@FXML
	private Circle profileButtonP;

	@FXML
	private AnchorPane root;

	@FXML
	private void handleMenuAction(MouseEvent e) throws IOException{

		AnchorPane pane;

		if (e.getSource() == homeButtonP) {
			pane = FXMLLoader.load(getClass().getResource("/view/Dashboard.fxml"));
		}
		else if (e.getSource() == calendarButtonP) {
			pane = FXMLLoader.load(getClass().getResource("/view/Calendar.fxml"));
		}
		else {
			pane = FXMLLoader.load(getClass().getResource("/view/Courses.fxml"));
		}

		root.getChildren().setAll(pane);
	}

	@FXML
	private Circle profilePic;

	@FXML
	private JFXButton imageButton;

	// new add-ons
	@FXML
	private void handlePicAction(ActionEvent event) throws IOException{
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Choose Image");
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
		File selectedFile = fileChooser.showOpenDialog(profilePic.getScene().getWindow());

		if (selectedFile != null) {

			String os = System.getProperty("os.name").toUpperCase();
			File icon;

			if (os.contains("MAC")) {
				icon = new File(System.getProperty("user.home") + "/Library/Application Support/Bias/" 
						+ userProfile.getDirectory() + "/profile.jpg");
			}
			else {
				icon = new File(System.getenv("APPDATA") + "\\Bias\\" + userProfile.getDirectory() + "\\profile.jpg");
			}

			copyFile(selectedFile, icon);

			String imageFile = icon.toURI().toURL().toString();
			Image image = new Image(imageFile);
			profilePic.setFill(new ImagePattern(image));
			profileButtonP.setFill(new ImagePattern(image));
			users.getActiveUser().setProfilePic(imageFile);
		} 
	}

	@SuppressWarnings("resource")
	public static void copyFile(File sourceFile, File destFile) throws IOException {
		if (!destFile.exists()) {
			destFile.createNewFile();
		}

		FileChannel source = null;
		FileChannel destination = null;
		try {
			source = new FileInputStream(sourceFile).getChannel();
			destination = new FileOutputStream(destFile).getChannel();

			long count = 0;
			long size = source.size();
			while ((count += destination.transferFrom(source, count, size
					- count)) < size)
				;
		} finally {
			if (source != null) {
				source.close();
			}
			if (destination != null) {
				destination.close();
			}
		}
	}

	@FXML
	private JFXTextField name;

	@FXML
	private JFXTextField email;

	@FXML
	private JFXTextField username;

	@FXML
	private JFXTextField id;

	@FXML
	private JFXPasswordField password;

	@FXML
	private Label verifyLabel;

	@FXML
	private JFXPasswordField verify;

	@FXML
	private JFXButton edit;

	// alterations made
	public void editAndSave(ActionEvent e) {
		// edit
		if (edit.getText().equals("Edit")) {
			edit.setText("Save");
			imageButton.setVisible(true);
			verifyLabel.setVisible(true);
			verify.setVisible(true);
			name.setEditable(true);
			email.setEditable(true);
			username.setEditable(true);
			id.setEditable(true);
			password.setEditable(true);
			name.setFocusColor(Color.web("#007aff"));
			email.setFocusColor(Color.web("#007aff"));
			username.setFocusColor(Color.web("#007aff"));
			id.setFocusColor(Color.web("#007aff"));
			password.setFocusColor(Color.web("#007aff"));

			if (!course1.getText().equals("Course Name")) 
				remove_course_1.setVisible(true);
			if (!course2.getText().equals("Course Name")) 
				remove_course_2.setVisible(true);
			if (!course3.getText().equals("Course Name")) 
				remove_course_3.setVisible(true);
			if (!course4.getText().equals("Course Name")) 
				remove_course_4.setVisible(true);
			if (!course5.getText().equals("Course Name")) 
				remove_course_5.setVisible(true);
			if (!course6.getText().equals("Course Name")) 
				remove_course_6.setVisible(true);	
		}
		// save
		else {
			if (password.getText().equals(verify.getText())) {
				edit.setText("Edit");
				imageButton.setVisible(false);
				verifyLabel.setVisible(false);
				verify.setVisible(false);			
				name.setEditable(false);			
				email.setEditable(false);	
				username.setEditable(false);	
				id.setEditable(false);	
				password.setEditable(false);
				name.setFocusColor(Color.web("BLACK"));
				email.setFocusColor(Color.web("transparent"));
				username.setFocusColor(Color.web("transparent"));
				id.setFocusColor(Color.web("transparent"));
				password.setFocusColor(Color.web("transparent"));


				users.getActiveUser().setName(name.getText());
				users.getActiveUser().setMail(email.getText());
				users.getActiveUser().setUsername(username.getText());
				users.getActiveUser().setID(id.getText());
				users.getActiveUser().setPassword(password.getText());

				if (!course1.getText().equals("Course Name")) 
					remove_course_1.setVisible(false);
				if (!course2.getText().equals("Course Name")) 
					remove_course_2.setVisible(false);
				if (!course3.getText().equals("Course Name")) 
					remove_course_3.setVisible(false);
				if (!course4.getText().equals("Course Name")) 
					remove_course_4.setVisible(false);
				if (!course5.getText().equals("Course Name")) 
					remove_course_5.setVisible(false);
				if (!course6.getText().equals("Course Name")) 
					remove_course_6.setVisible(false);	

				try {
					new SerializationUtil();
					String os = System.getProperty("os.name").toUpperCase();

					if (os.contains("MAC")) 
						SerializationUtil.serialize(users, usersDirMac);
					else if (os.contains("WIN")) 
						SerializationUtil.serialize(users, usersDirWin);
				} catch (IOException e1) {
					System.out.println("Serialization Failed");
					e1.printStackTrace();
				}
			}

			else {
				password.setUnFocusColor(Color.web("#FF3B30"));
				verify.setUnFocusColor(Color.web("#FF3B30"));
			}
		}		
	}

	@FXML
	private Pane pane1;

	@FXML
	private Pane pane2;

	@FXML
	private Pane pane3;

	@FXML
	private Pane pane4;

	@FXML
	private Pane pane5;

	@FXML
	private Pane pane6;

	@FXML
	private Label course1;

	@FXML
	private Label course2;

	@FXML
	private Label course3;

	@FXML
	private Label course4;

	@FXML
	private Label course5;

	@FXML
	private Label course6;

	@FXML
	private ImageView add1;

	@FXML
	private ImageView add2;

	@FXML
	private ImageView add3;

	@FXML
	private ImageView add4;

	@FXML
	private ImageView add5;

	@FXML
	private ImageView add6;

	@FXML
	private JFXButton remove_course_1;

	@FXML
	private JFXButton remove_course_2;

	@FXML
	private JFXButton remove_course_3;

	@FXML
	private JFXButton remove_course_4;

	@FXML
	private JFXButton remove_course_5;

	@FXML
	private JFXButton remove_course_6;

	public void popCourseMenu(MouseEvent e) throws IOException { 
		Stage stage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/view/AddLecture.fxml"));

		root.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				xOffset = event.getSceneX();
				yOffset = event.getSceneY();
			}
		});
		root.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				stage.setX(event.getScreenX() - xOffset);
				stage.setY(event.getScreenY() - yOffset);
			}
		});
		Scene scene = new Scene(root);
		scene.setFill(Color.TRANSPARENT);
		stage.setScene(scene);
		stage.initStyle(StageStyle.TRANSPARENT);
		stage.show();
	}

	public void removeCourse(ActionEvent e) {
		if (e.getSource().equals(remove_course_1) && !course1.getText().equals("Course Name")) {
			courses.remove(0);
			add1.setVisible(true);
			pane1.setOpacity(0.25);
			course1.setVisible(false);
			course1.setText("Course Name");
			remove_course_1.setVisible(false);
			pane1.setStyle("-fx-border-color: white; -fx-border-radius: 10; -fx-border-style: segments(10, 10, 10, 10);");	
		}
		else if (e.getSource().equals(remove_course_2) && !course2.getText().equals("Course Name")) {
			courses.remove(1);
			add2.setVisible(true);
			pane2.setOpacity(0.25);
			course2.setVisible(false);
			course2.setText("Course Name");
			remove_course_2.setVisible(false);
			pane2.setStyle("-fx-border-color: white; -fx-border-radius: 10; -fx-border-style: segments(10, 10, 10, 10);");	
		}
		else if (e.getSource().equals(remove_course_3) && !course3.getText().equals("Course Name")) {
			courses.remove(2);
			add3.setVisible(true);
			pane3.setOpacity(0.25);
			course3.setVisible(false);
			course3.setText("Course Name");
			remove_course_3.setVisible(false);
			pane3.setStyle("-fx-border-color: white; -fx-border-radius: 10; -fx-border-style: segments(10, 10, 10, 10);");	
		}
		else if (e.getSource().equals(remove_course_4) && !course4.getText().equals("Course Name")) {
			courses.remove(3);
			add4.setVisible(true);
			pane4.setOpacity(0.25);
			course4.setVisible(false);
			course4.setText("Course Name");
			remove_course_4.setVisible(false);
			pane4.setStyle("-fx-border-color: white; -fx-border-radius: 10; -fx-border-style: segments(10, 10, 10, 10);");	
		}
		else if (e.getSource().equals(remove_course_5) && !course5.getText().equals("Course Name")) {
			courses.remove(4);
			add5.setVisible(true);
			pane5.setOpacity(0.25);
			course5.setVisible(false);
			course5.setText("Course Name");
			remove_course_5.setVisible(false);
			pane5.setStyle("-fx-border-color: white; -fx-border-radius: 10; -fx-border-style: segments(10, 10, 10, 10);");	
		}
		else if (e.getSource().equals(remove_course_6) && !course6.getText().equals("Course Name")) {
			courses.remove(5);
			add6.setVisible(true);
			pane6.setOpacity(0.25);
			course6.setVisible(false);
			course6.setText("Course Name");
			remove_course_6.setVisible(false);
			pane6.setStyle("-fx-border-color: white; -fx-border-radius: 10; -fx-border-style: segments(10, 10, 10, 10);");	
		}

		try {
			new SerializationUtil();
			SerializationUtil.serialize(courses, courseDir);
		} catch (IOException e1) {
			System.out.println("Serialization Failed");
			e1.printStackTrace();
		}
	}

	public void courseHandler() {
		try {
			new SerializationUtil();
			courses =  (EnrolledCourses) SerializationUtil.deserialize(courseDir);
			ArrayList<String> enrolled_courses = courses.getCourseNames();

			try {
				course1.setText(enrolled_courses.get(0));
				course2.setText(enrolled_courses.get(1));
				course3.setText(enrolled_courses.get(2));
				course4.setText(enrolled_courses.get(3));
				course5.setText(enrolled_courses.get(4));
				course6.setText(enrolled_courses.get(5));
			} catch (Exception e) {
				//e.printStackTrace();
			}

		} catch (ClassNotFoundException | IOException e) {
			//e.printStackTrace();
		}	

		if (!course1.getText().equals("Course Name")) {
			pane1.setOpacity(1.0);
			pane1.setStyle("-fx-border-color: white; -fx-border-radius: 10;");
			course1.setVisible(true);
			add1.setVisible(false);
		}

		if (!course2.getText().equals("Course Name")) {
			pane2.setOpacity(1.0);
			pane2.setStyle("-fx-border-color: white; -fx-border-radius: 10;");
			course2.setVisible(true);
			add2.setVisible(false);
		}

		if (!course3.getText().equals("Course Name")) {
			pane3.setOpacity(1.0);
			pane3.setStyle("-fx-border-color: white; -fx-border-radius: 10;");
			course3.setVisible(true);
			add3.setVisible(false);
		}

		if (!course4.getText().equals("Course Name")) {
			pane4.setOpacity(1.0);
			pane4.setStyle("-fx-border-color: white; -fx-border-radius: 10;");
			course4.setVisible(true);
			add4.setVisible(false);
		}

		if (!course5.getText().equals("Course Name")) {
			pane5.setOpacity(1.0);
			pane5.setStyle("-fx-border-color: white; -fx-border-radius: 10;");
			course5.setVisible(true);
			add5.setVisible(false);
		}

		if (!course6.getText().equals("Course Name")) {
			pane6.setOpacity(1.0);
			pane6.setStyle("-fx-border-color: white; -fx-border-radius: 10;");
			course6.setVisible(true);
			add6.setVisible(false);
		}
	}

	// settings

	@FXML
	private Pane settingsPane;

	@FXML
	private ToggleSwitch rememberMe;

	@FXML
	private ToggleSwitch keepMeLoggedIn;

	@FXML
	private ToggleSwitch allowNotifications;

	@FXML
	private ToggleSwitch removePastAssignments;

	@FXML
	private ToggleSwitch enableNotes;

	@FXML
	private ToggleSwitch saveNotesAsText;

	@FXML
	private ToggleSwitch enableCourses;

	@FXML
	private ToggleSwitch enableEditing;

	@FXML
	private JFXButton signOut;

	@FXML
	private JFXButton about;
	
	public void signOut(ActionEvent e) {
		FadeTransition ft = new FadeTransition(Duration.seconds(0.5), root);
		ft.setToValue(0.0);
		ft.setCycleCount(1);
		ft.play();
		ft.setOnFinished(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				try {
					settings.setKeepMeLoggedIn(false);
					try {
						new SerializationUtil();
						settings = (Settings) SerializationUtil.deserialize(settingsDir);	
					} catch (ClassNotFoundException | IOException e) {
						//e.printStackTrace();
					}
					keepMeLoggedIn.setSelected(false);
					
					AnchorPane pane;
					pane = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
					root.getChildren().setAll(pane);
					
					FadeTransition ft = new FadeTransition(Duration.seconds(0.5), root);
					ft.setToValue(1.0);
					ft.setCycleCount(1);
					ft.play();
				} catch (IOException e) {
					//e.printStackTrace();
				}}});
	}

	public void ShowAbout(ActionEvent e) throws IOException {
		Stage stage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/view/About.fxml"));
		Scene scene = new Scene(root);
		stage.setResizable(false);
		stage.setScene(scene);
		stage.show();
	}

	public void underline(MouseEvent e) {
		about.setUnderline(true);
	}

	public void disableUnderline(MouseEvent e) {
		about.setUnderline(false);
	}

	@FXML
	private ImageView settingsButton;
	
	boolean is_deployed = false;	
		
	public void deploy() {
		settingsPane.setVisible(true);
		is_deployed = true;
		TranslateTransition deploy = new TranslateTransition(new Duration(350), settingsPane);
		deploy.setFromX(1400);
		deploy.setToX(1055);
		deploy.play();
	}

	public void hideSettings() {
		is_deployed = false;
		TranslateTransition hide = new TranslateTransition(new Duration(350), settingsPane);
		hide.setFromX(1055);
		hide.setToX(1400);
		hide.play();
		hide.setOnFinished(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				settingsPane.setVisible(false);
			}});
	}

	Timeline notifyCheck;

	public void notifyActivities() {
		notifyCheck = new Timeline(new KeyFrame(Duration.seconds(59), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				try {
					new SerializationUtil();
					ce =  (CustomEvent) SerializationUtil.deserialize(eventsDir);			
				} catch (ClassNotFoundException | IOException ex) {
				}

				Date date = new Date();  
				Calendar calendar = GregorianCalendar.getInstance(); 
				calendar.setTime(date); 
				String hour = "" + calendar.get(Calendar.HOUR_OF_DAY);

				if (calendar.get(Calendar.MINUTE) < 10)
					hour += "0" + calendar.get(Calendar.MINUTE);
				else
					hour += calendar.get(Calendar.MINUTE);  

				for (int i = 0; i < ce.getDays().size(); i++) {
					String eventTime = "" + ce.getStartHours().get(i).intValue();

					if (ce.getStartMins().get(i).intValue() < 10)
						eventTime = eventTime + "0" + ce.getStartMins().get(i).intValue();
					else
						eventTime = eventTime + ce.getStartMins().get(i).intValue();

					int timeLeft = Integer.valueOf(eventTime) - Integer.valueOf(hour);
					if (!ce.getSilenced().get(i)) {
						if (timeLeft == 100 || timeLeft == 70 || timeLeft == 30) {
							String os = System.getProperty("os.name").toUpperCase();

							if (os.contains("WIN")) {
								NotificationType notification = NotificationType.NOTICE;
								TrayNotification tray = new TrayNotification("Upcoming Event Alert", "You have an activity coming up next.",  notification);
								tray.setAnimationType(AnimationType.POPUP);
								tray.showAndDismiss(Duration.seconds(5));
							}

							if (os.contains("MAC")) {
								try {
									Runtime.getRuntime().exec(new String[] { "osascript", "-e",
											"display notification \"Make sure you're ready!\""
													+ " with title \"Upcoming Event Alert\" subtitle "
													+ "\"You have an activity coming up next.\" sound name "
													+ "\"Tri-tone\"" });
								} catch (IOException e) {
									e.printStackTrace();
								}
							}	

						}
					}
				}
			}}));
		notifyCheck.setCycleCount(Timeline.INDEFINITE);
		notifyCheck.play();
	}

	Timeline notifyLectures;
	QuizzesAndPresentations qp = new QuizzesAndPresentations();

	public void notifyLectures() {
		notifyLectures = new Timeline(new KeyFrame(Duration.seconds(59), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				try {
					new SerializationUtil();
					qp =  (QuizzesAndPresentations) SerializationUtil.deserialize(qpDir);			
				} catch (ClassNotFoundException | IOException ex) {
				}

				Date date = new Date();  
				Calendar calendar = GregorianCalendar.getInstance(); 
				calendar.setTime(date); 
				String hour = "" + calendar.get(Calendar.HOUR_OF_DAY);

				if (calendar.get(Calendar.MINUTE) < 10)
					hour += "0" + calendar.get(Calendar.MINUTE);
				else
					hour += calendar.get(Calendar.MINUTE);

				ArrayList<String> hours = new ArrayList<>();
				hours.add("840");
				hours.add("940");
				hours.add("1040");
				hours.add("1140");
				hours.add("1340");
				hours.add("1440");
				hours.add("1540");
				hours.add("1640");

				for (int i = 0; i < qp.loadQuizzes().size(); i++) {
					String qpTime = hours.get(qp.loadQuizzes().get(i));
					int timeLeft = Integer.valueOf(qpTime) - Integer.valueOf(hour);
					if (timeLeft == 10) {
						String os = System.getProperty("os.name").toUpperCase();

						if (os.contains("WIN")) {
							NotificationType notification = NotificationType.NOTICE;
							TrayNotification tray = new TrayNotification("Upcoming Event Alert", "You have a quiz coming up next.",  notification);
							tray.setAnimationType(AnimationType.POPUP);
							tray.showAndDismiss(Duration.seconds(5));
						}

						if (os.contains("MAC")) {
							try {
								Runtime.getRuntime().exec(new String[] { "osascript", "-e",
										"display notification \"Make sure you're ready!\""
												+ " with title \"Upcoming Event Alert\" subtitle "
												+ "\"You have a quiz coming up next.\" sound name "
												+ "\"Tri-tone\"" });
							} catch (IOException e) {
								e.printStackTrace();
							}
						}	

					}
				}

				for (int i = 0; i < qp.loadPresentations().size(); i++) {
					String qpTime = hours.get(qp.loadPresentations().get(i));
					int timeLeft = Integer.valueOf(qpTime) - Integer.valueOf(hour);

					if (timeLeft == 10) {
						String os = System.getProperty("os.name").toUpperCase();

						if (os.contains("WIN")) {
							NotificationType notification = NotificationType.NOTICE;
							TrayNotification tray = new TrayNotification("Upcoming Event Alert", "You have a presentatation coming up next.",  notification);
							tray.setAnimationType(AnimationType.POPUP);
							tray.showAndDismiss(Duration.seconds(5));
						}

						if (os.contains("MAC")) {
							try {
								Runtime.getRuntime().exec(new String[] { "osascript", "-e",
										"display notification \"Make sure you're ready!\""
												+ " with title \"Upcoming Event Alert\" subtitle "
												+ "\"You have a presentation coming up next.\" sound name "
												+ "\"Tri-tone\"" });
							} catch (IOException e) {
								e.printStackTrace();
							}
						}	

					}
				}

			}}));
		notifyLectures.setCycleCount(Timeline.INDEFINITE);
		notifyLectures.play();
	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {

		try {
			new SerializationUtil();
			String os = System.getProperty("os.name").toUpperCase();

			if (os.contains("MAC")) {
				users = (Users) SerializationUtil.deserialize(usersDirMac);
				userProfile = users.getActiveUser();
				
				courseDir = System.getProperty("user.home") + "/Library/Application Support/Bias/" 
						+ userProfile.getDirectory()  +"/courses.ser";

				settingsDir = System.getProperty("user.home") + "/Library/Application Support/Bias/" 
						+ userProfile.getDirectory()  +"/settings.ser";

				eventsDir = System.getProperty("user.home") + "/Library/Application Support/Bias/" 
						+ userProfile.getDirectory()  +"/events.ser";

				qpDir = System.getProperty("user.home") + "/Library/Application Support/Bias/"
						+ userProfile.getDirectory() + "/qp.ser";
			}
			else if (os.contains("WIN")) {
				users = (Users) SerializationUtil.deserialize(usersDirWin);
				userProfile = users.getActiveUser();
				
				courseDir = System.getenv("APPDATA") + "\\Bias\\" + userProfile.getDirectory() + "\\courses.ser";

				settingsDir = System.getenv("APPDATA") + "\\Bias\\" + userProfile.getDirectory() + "\\settings.ser";

				eventsDir = System.getenv("APPDATA") + "\\Bias\\" + userProfile.getDirectory() + "\\events.ser";

				qpDir = System.getenv("APPDATA") + "\\Bias\\" + userProfile.getDirectory() + "\\qp.ser";
			}

			if (!userProfile.getProfilePic().isEmpty()) {
				profilePic.setFill(new ImagePattern(new Image(userProfile.getProfilePic())));
				profileButtonP.setFill(new ImagePattern(new Image(userProfile.getProfilePic())));
			}

			name.setText(userProfile.getName());
			email.setText(userProfile.getMail());
			username.setText(userProfile.getUsername());
			id.setText(userProfile.getID());
			password.setText(userProfile.getPassword());
			verify.setText(userProfile.getPassword());

		} catch (ClassNotFoundException | IOException e) {
			//e.printStackTrace();
		}

		// settings
		try {
			new SerializationUtil();
			settings = (Settings) SerializationUtil.deserialize(settingsDir);	
		} catch (ClassNotFoundException | IOException e) {
			//e.printStackTrace();
		}
		
		if (settings.getAllowNotifications()) {
			notifyActivities();
			notifyLectures();
		}

		root.setOnScrollStarted(new EventHandler<ScrollEvent>() {
			@Override
			public void handle(ScrollEvent event) {	
				if (event.getDeltaX() > 0 && is_deployed) {
					hideSettings();
					is_deployed = false;
				}
				else if (event.getDeltaX() < 0 && !is_deployed) {
					deploy();
					is_deployed = true;
				}	
				else if (event.getDeltaX() < 0 && is_deployed) {
					TranslateTransition deploy = new TranslateTransition(new Duration(175), settingsPane);
					deploy.setFromX(1055);
					deploy.setToX(1000);
					deploy.setAutoReverse(true);
					deploy.setCycleCount(2);
					deploy.play();
				}
				event.consume();
			}});

		rememberMe.setSelected(settings.getRememberMe());
		keepMeLoggedIn.setSelected(settings.getKeepMeLoggedIn());
		allowNotifications.setSelected(settings.getAllowNotifications());
		removePastAssignments.setSelected(settings.getRemovePastAssignments());
		enableNotes.setSelected(settings.getEnableNotes());
		saveNotesAsText.setSelected(settings.getSaveNotesAsText());
		enableCourses.setSelected(settings.getEnableCourses());
		enableEditing.setSelected(settings.getEnableEditing());

		rememberMe.selectedProperty().addListener((observable, oldValue, newValue) -> {
			settings.setRememberMe(rememberMe.isSelected());
			try {
				new SerializationUtil();
				SerializationUtil.serialize(settings, settingsDir);
			} catch (IOException e) {
				System.out.println("Serialization Failed");
				e.printStackTrace();
			}});

		keepMeLoggedIn.selectedProperty().addListener((observable, oldValue, newValue) -> {
			settings.setKeepMeLoggedIn(keepMeLoggedIn.isSelected());
			userProfile.setKeepLogged(keepMeLoggedIn.isSelected());

			if (keepMeLoggedIn.isSelected()) {
				for (Profile user : users.getUsers()) {
					if (!user.isOnline()) {
						user.setKeepLogged(false);
					}}}
			else {
				for (Profile user : users.getUsers()) {
					user.setKeepLogged(false);
				}}

			try {
				new SerializationUtil();
				SerializationUtil.serialize(settings, settingsDir);
				String os = System.getProperty("os.name").toUpperCase();

				if (os.contains("MAC")) 
					SerializationUtil.serialize(users, usersDirMac);
				else if (os.contains("WIN"))
					SerializationUtil.serialize(users, usersDirWin);
			} catch (IOException e) {
				System.out.println("Serialization Failed");
				e.printStackTrace();
			}});

		allowNotifications.selectedProperty().addListener((observable, oldValue, newValue) -> {
			settings.setAllowNotifications(allowNotifications.isSelected());
			try {
				new SerializationUtil();
				SerializationUtil.serialize(settings, settingsDir);
			} catch (IOException e) {
				System.out.println("Serialization Failed");
				e.printStackTrace();
			}});

		removePastAssignments.selectedProperty().addListener((observable, oldValue, newValue) -> {
			settings.setRemovePastAssignments(removePastAssignments.isSelected());
			try {
				new SerializationUtil();
				SerializationUtil.serialize(settings, settingsDir);
			} catch (IOException e) {
				System.out.println("Serialization Failed");
				e.printStackTrace();
			}});

		enableNotes.selectedProperty().addListener((observable, oldValue, newValue) -> {
			settings.setEnableNotes(enableNotes.isSelected());
			try {
				new SerializationUtil();
				SerializationUtil.serialize(settings, settingsDir);
			} catch (IOException e) {
				System.out.println("Serialization Failed");
				e.printStackTrace();
			}});

		saveNotesAsText.selectedProperty().addListener((observable, oldValue, newValue) -> {
			settings.setSaveNotesAsText(saveNotesAsText.isSelected());
			try {
				new SerializationUtil();
				SerializationUtil.serialize(settings, settingsDir);
			} catch (IOException e) {
				System.out.println("Serialization Failed");
				e.printStackTrace();
			}});

		enableCourses.selectedProperty().addListener((observable, oldValue, newValue) -> {
			settings.setEnableCourses(enableCourses.isSelected());
			try {
				new SerializationUtil();
				SerializationUtil.serialize(settings, settingsDir);
			} catch (IOException e) {
				System.out.println("Serialization Failed");
				e.printStackTrace();
			}});

		enableEditing.selectedProperty().addListener((observable, oldValue, newValue) -> {
			settings.setEnableEditing(enableEditing.isSelected()); 
			try {
				new SerializationUtil();
				SerializationUtil.serialize(settings, settingsDir);
			} catch (IOException e) {
				System.out.println("Serialization Failed");
				e.printStackTrace();
			}});

		// courses

		courseHandler();

		Timeline courseChecker = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				courseHandler();
			}
		}));

		courseChecker.setCycleCount(Timeline.INDEFINITE);
		courseChecker.play();
	}
}