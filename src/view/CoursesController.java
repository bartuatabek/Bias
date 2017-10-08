package view;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

import application.CoursePane;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.CacheHint;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import model.AutocompletionSearchField;
import model.CourseCreator;
import model.CourseData;
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

public class CoursesController implements Initializable {

	private static final String usersDirMac = System.getProperty("user.home") 
			+ "/Library/Application Support/Bias/users.ser";

	private static final String usersDirWin = System.getenv("APPDATA") + "\\Bias\\users.ser";

	private Users users = new Users();

	private Profile userProfile = new Profile();

	private Settings settings = new Settings();

	private EnrolledCourses courses = new EnrolledCourses();

	private CourseData courseData = new CourseData();

	private CustomEvent ce = new CustomEvent();

	private String courseDir;

	private String courseDataDir;

	private String settingsDir;

	private String eventsDir;

	private String qpDir;


	@FXML
	private ImageView homeButtonLec;

	@FXML
	private ImageView calendarButtonLec;

	@FXML
	private ImageView lectureButtonLec;

	@FXML
	private Circle profileButtonLec;

	@FXML
	private AnchorPane root;

	@FXML
	private void handleMenuAction(MouseEvent e) throws IOException{

		AnchorPane pane;

		if (coursePane.getChildren().size() > 1) {
			courseData.updateData(((CoursePane) coursePane.getChildren().get(1)).getGrades(), index);
			new SerializationUtil();
			SerializationUtil.serialize(courseData, courseDataDir);
		}

		if (e.getSource() == profileButtonLec) {
			pane = FXMLLoader.load(getClass().getResource("/view/Profile.fxml"));
		}
		else if (e.getSource() == homeButtonLec) {
			pane = FXMLLoader.load(getClass().getResource("/view/Dashboard.fxml"));
		}
		else {
			pane = FXMLLoader.load(getClass().getResource("/view/Calendar.fxml"));
		}

		root.getChildren().setAll(pane);
	}

	@FXML
	private Pane coursePane;

	@FXML
	private Pane course1;

	@FXML
	private Pane course2;

	@FXML
	private Pane course3;

	@FXML
	private Pane course4;

	@FXML
	private Pane course5;

	@FXML
	private Pane course6;

	@FXML
	private Label courseName1;

	@FXML
	private Label courseName2;

	@FXML
	private Label courseName3;

	@FXML
	private Label courseName4;

	@FXML
	private Label courseName5;

	@FXML
	private Label courseName6;

	@FXML
	private VBox results;

	@FXML
	private Pane searchFocusPane;

	@FXML
	private Pane searchPane;

	@FXML
	private Pane addPane;

	@FXML 
	private ImageView add;

	@FXML
	private Label cancel;

	@FXML
	private Region space;

	AutocompletionSearchField search;

	ArrayList<String> enrolled_courses;

	ArrayList<Pane> cp = new ArrayList<Pane>();

	ArrayList<Pane> cPanes = new ArrayList<Pane>();

	boolean clear = false;

	int index = -1;

	public void searchAction(MouseEvent e) {
		boolean selected = false;
		for (int i = 0; i < enrolled_courses.size(); i++) {
			if (search.getText().equals(enrolled_courses.get(i))) {
				cPanes.get(i).setVisible(false);
				selected = true;
				index = i;
				continue;
			}
			if (selected) {
				TranslateTransition swipe = new TranslateTransition(new Duration(250), cPanes.get(i));
				swipe.setToX(-60);
				swipe.play();
			}
		}
		if (selected)
			flipFader(coursePane.getChildren().get(coursePane.getChildren().size() - 1), index, prevIndex);
	}

	int prevIndex = -1;

	public void flipFader(Node node, int index, int prevIndex) {

		RotateTransition rotator = new RotateTransition(Duration.millis(500), node);
		rotator.setAxis(Rotate.Y_AXIS);
		rotator.setFromAngle(0);
		rotator.setToAngle(90);
		rotator.setInterpolator(Interpolator.LINEAR);

		FadeTransition fader = new FadeTransition(Duration.millis(500), node);
		fader.setToValue(0);

		ParallelTransition flipFader = new ParallelTransition();
		flipFader.getChildren().addAll(rotator, fader);
		flipFader.setOnFinished(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					if (clear) {
						coursePane.getChildren().get(0).setRotate(0);
						FadeTransition fader2 = new FadeTransition(Duration.millis(500), coursePane.getChildren().get(0));
						fader2.setToValue(1);
						coursePane.getChildren().remove(coursePane.getChildren().size() - 1);
						fader2.play();
						courseDataChecker.stop();
						clear = false;
					}
					else {

						if (coursePane.getChildren().size() > 1) {
							courseData.updateData(((CoursePane) coursePane.getChildren().get(1)).getGrades(), prevIndex);
							new SerializationUtil();
							SerializationUtil.serialize(courseData, courseDataDir);
						}

						CoursePane selectedCourse = new CoursePane(enrolled_courses.get(index));
						try {
							selectedCourse.load(courseData.getGradeList(index));
							selectedCourse.check();
							courseDataChecker(selectedCourse, index);
						} catch (Exception e) {}
						coursePane.getChildren().add(selectedCourse);
						if (coursePane.getChildren().size() > 2)
							coursePane.getChildren().remove(1);
						coursePane.getChildren().get(coursePane.getChildren().size() - 1).setOpacity(0);
						FadeTransition fader2 = new FadeTransition(Duration.millis(500), coursePane.getChildren().get(1));
						fader2.setToValue(1);
						fader2.play();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}});
		flipFader.play();
	}

	public void clear(MouseEvent e) throws IOException {

		if (coursePane.getChildren().size() > 1) {
			courseData.updateData(((CoursePane) coursePane.getChildren().get(1)).getGrades(), index);
			new SerializationUtil();
			SerializationUtil.serialize(courseData, courseDataDir);
		}

		if (coursePane.getChildren().size() > 1) {
			for (int i = coursePane.getChildren().size() - 1; i > 1; i--){
				coursePane.getChildren().remove(i);
			}
			clear = true;
			flipFader(coursePane.getChildren().get(coursePane.getChildren().size() - 1), -1, prevIndex);
			reset();
		}
	}

	public void reset() {
		for (Pane c : cp) {
			TranslateTransition swipe = new TranslateTransition(new Duration(250), c);
			c.setVisible(true);
			swipe.setToX(0);
			swipe.play();
		}

		for (int i = coursePane.getChildren().size() - 1; i > 1; i--){
			coursePane.getChildren().remove(i);
		}
	}

	boolean hasActive;
	
	public void pickAction(MouseEvent e) {

		boolean selected = false;
		hasActive = selected;
		reset();

		if (!cPanes.isEmpty()) {

			if (prevIndex != -1)
			{
				prevIndex = index;
			}
			for (int i = 0; i < enrolled_courses.size(); i++) {
				if (e.getSource().equals(cPanes.get(i))) {
					cPanes.get(i).setVisible(false);
					selected = true;
					hasActive = selected;
					index = i;
					continue;
				}
				if (selected) {
					TranslateTransition swipe = new TranslateTransition(new Duration(250), cPanes.get(i));
					swipe.setToX(-60);
					swipe.play();
				}
			}
			if (prevIndex == -1)
			{
				prevIndex = index;
			}
			if (selected)
				flipFader(coursePane.getChildren().get(coursePane.getChildren().size() - 1), index, prevIndex);
		}
	}

	public void cancel(MouseEvent e) {
		search = new AutocompletionSearchField(results, addPane);
		searchPane.getChildren().add(search);
		search.getEntries().addAll(courses.getCourseNames());	
		search.clear();

		search.setOnMouseClicked(actionEvent -> {
			searchFocusPane.setPrefWidth(250.0);
			cancel.setVisible(true);
		});

		searchFocusPane.setPrefWidth(300.0);
		cancel.setVisible(false);
		space.requestFocus();
	}

	public void add(MouseEvent e) {
		CourseCreator creator  = new CourseCreator();

		ArrayList<String> depts = creator.getAllFaculties();
		ArrayList<String> codes = creator.getAllCodes();
		ArrayList<String> names = creator.getAllCourseNames();

		String[] searchData = new String[2234];

		for (int i = 0; i < 2234; i++) {
			if ((depts.get(i) + " " + codes.get(i) + " " + names.get(i)).length() > 40)
				searchData[i] = (depts.get(i) + " " + codes.get(i) + " " + names.get(i)).substring(0, 40) + "…";
			else
				searchData[i] = depts.get(i) + " " + codes.get(i) + " " + names.get(i);
		}

		Set<String> data = new HashSet<String>(Arrays.asList(searchData));
		search = new AutocompletionSearchField(results, new Pane());
		searchPane.getChildren().clear();
		searchPane.getChildren().add(search);
		search.getEntries().addAll(data);
		search.requestFocus();
	}

	Timeline courseDataChecker;

	public void courseDataChecker(CoursePane cp, int index) {	
		courseDataChecker = new Timeline(new KeyFrame(Duration.seconds(0.5), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (hasActive) {
					if (cp.isDeleted())
						courseData.remove(index);
				}
			}}));
		courseDataChecker.setCycleCount(Timeline.INDEFINITE);
		courseDataChecker.play();
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

				courseDataDir = System.getProperty("user.home") + "/Library/Application Support/Bias/" 
						+ userProfile.getDirectory()  +"/course_data.ser";

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

				courseDataDir = System.getenv("APPDATA") + "\\Bias\\" + userProfile.getDirectory() + "\\course_data.ser";

				eventsDir = System.getenv("APPDATA") + "\\Bias\\" + userProfile.getDirectory() + "\\events.ser";

				qpDir = System.getenv("APPDATA") + "\\Bias\\" + userProfile.getDirectory() + "\\qp.ser";
			}

			if (!userProfile.getProfilePic().isEmpty()) {
				profileButtonLec.setFill(new ImagePattern(new Image(userProfile.getProfilePic())));
			}

			courses =  (EnrolledCourses) SerializationUtil.deserialize(courseDir);
			courseData = (CourseData) SerializationUtil.deserialize(courseDataDir);
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

		enrolled_courses = courses.getCourseNames();
		cp.add(course1);
		cp.add(course2);
		cp.add(course3);
		cp.add(course4);
		cp.add(course5);
		cp.add(course6);

		try {
			courseName1.setText(enrolled_courses.get(0));
			courseName2.setText(enrolled_courses.get(1));
			courseName3.setText(enrolled_courses.get(2));
			courseName4.setText(enrolled_courses.get(3));
			courseName5.setText(enrolled_courses.get(4));
			courseName6.setText(enrolled_courses.get(5));
		} catch (Exception e) {}

		if (settings.getEnableCourses()) {
			for (int i = cp.size() - 1; i >= enrolled_courses.size(); i--) {
				cp.remove(i).setVisible(false);
			}

			if (course1.isVisible())
				cPanes.add(course1);

			if (course2.isVisible())
				cPanes.add(course2);

			if (course3.isVisible())
				cPanes.add(course3);

			if (course4.isVisible())
				cPanes.add(course4);

			if (course5.isVisible())
				cPanes.add(course5);

			if (course6.isVisible())
				cPanes.add(course6);

			for (Pane c : cp) {
				c.setCache(true);
				c.setCacheHint(CacheHint.SPEED);
			}

			search = new AutocompletionSearchField(results, addPane);
			searchPane.getChildren().add(search);
			search.getEntries().addAll(courses.getCourseNames());	

			search.setOnMouseClicked(actionEvent -> {
				searchFocusPane.setPrefWidth(250.0);
				cancel.setVisible(true);
			});

			searchPane.setOnKeyPressed(e -> {
				if (e.getCode() == KeyCode.ENTER) {
					boolean selected = false;
					for (int i = 0; i < enrolled_courses.size(); i++) {
						if (search.getText().equals(enrolled_courses.get(i))) {
							cPanes.get(i).setVisible(false);
							selected = true;
							index = i;
							continue;
						}
						if (selected) {
							TranslateTransition swipe = new TranslateTransition(new Duration(250), cPanes.get(i));
							swipe.setToX(-60);
							swipe.play();
						}
					}
					if (selected)
						flipFader(coursePane.getChildren().get(coursePane.getChildren().size() - 1), index, prevIndex);
				}});
		}

		else {
			for (Pane course : cp) {
				course.setVisible(false);
			}
		}

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				searchFocusPane.requestFocus();
			}});		
	}
}