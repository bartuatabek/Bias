package view;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

import com.jfoenix.controls.JFXButton;

import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.AutocompletionlTextField;
import model.CourseCreator;
import model.EnrolledCourses;
import model.Profile;
import model.SerializationUtil;
import model.Users;

public class AddLectureController implements Initializable {

	private static final String usersDirMac = System.getProperty("user.home") 
			+ "/Library/Application Support/Bias/users.ser";
	
	private static final String usersDirWin = System.getenv("APPDATA") + "\\Bias\\users.ser";

	private Users users = new Users();

	private Profile userProfile = new Profile();

	private EnrolledCourses courses = new EnrolledCourses();

	private CourseCreator courseCreator = new CourseCreator();

	private String courseDir;

	private String dept = "";
	private String code = "";
	private String sec = "";
	private String instructor2 = "";
	private String courseName2 = "";
	private int indexOfLecture;

	@FXML
	private AutocompletionlTextField department;

	@FXML
	private AutocompletionlTextField section;

	@FXML
	private AutocompletionlTextField courseCode;

	@FXML
	private Label instructor;

	@FXML 
	private Label courseName;

	@FXML
	private Label section2;

	@FXML
	private Label lectureCode;

	@FXML
	private ImageView verified_dept;

	@FXML
	private ImageView verified_course;

	@FXML
	private ImageView verified_sec;

	@FXML
	private JFXButton clean;

	@FXML
	private JFXButton add;

	@FXML
	private ImageView close;

	@FXML
	private ImageView minimize;

	@FXML
	private ImageView closed;

	@FXML
	private ImageView minimized;

	public void setDepartment () {
		department.textProperty().addListener((observable, oldValue, newValue) -> {
			dept = department.getText().toUpperCase();;
		});
	}

	public void setCourse () {
		courseCode.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!dept.isEmpty()) {
				courseCreator.setNonRepeatCodes(dept);				
				Set<String> courses = new HashSet<String>(courseCreator.getCodes());
				courseCode.getEntries().addAll(courses);
				code = courseCode.getText();
				verified_dept.setVisible(true);
			}			
		});
	}

	public void setSection () {
		if (!code.isEmpty()) {
			courseCreator.setNonRepeatSections(dept, code);
			Set<String> sections = new HashSet<String>(courseCreator.getSections());
			courseCode.getEntries().addAll(sections);
			sec = section.getText();
			verified_course.setVisible(true);
			build();
		}
	}

	public void build() {
		if (!sec.isEmpty()) { 
			courseCreator.addIndexes (dept, code, sec);
			indexOfLecture = courseCreator.getCoursePlaceOnArrays().get(courseCreator.getCoursePlaceOnArrays().size() - 1);

			courseName2 = courseCreator.getAllCourseNames().get(indexOfLecture);
			instructor2 = courseCreator.getAllTeacherNames().get(indexOfLecture);

			lectureCode.setText(dept + " " + code);
			courseName.setText(courseName2);
			instructor.setText(instructor2);
			section2.setText(sec);

			verified_sec.setVisible(true);
			setGraphs();
			add.setDisable(false);

			department.setDisable(true);
			section.setDisable(true);
			courseCode.setDisable(true);
		}	
	}

	public void clean(ActionEvent e) {

		courseCreator = new CourseCreator();

		department.setDisable(false);
		section.setDisable(false);
		courseCode.setDisable(false);
		add.setDisable(true);
		department.requestFocus();

		department.clear();
		section.clear();
		courseCode.clear();

		instructor.setText("");
		courseName.setText("");
		section2.setText("");
		lectureCode.setText("Course");

		dept = "";
		code = "";
		sec = "";
		instructor2 = "";
		courseName2 = "";

		verified_dept.setVisible(false);
		verified_course.setVisible(false);
		verified_sec.setVisible(false);

		sun.setHeight(0);
		mon.setHeight(0);
		tue.setHeight(0);
		wed.setHeight(0);
		thu.setHeight(0);
		fri.setHeight(0);
		sat.setHeight(0);
	}

	public void addCourse (ActionEvent e) {
		courses.save(courseCreator.getCoursePlaceOnArrays(), lectureCode.getText());
		add.setDisable(true);
		try {
			new SerializationUtil();
			SerializationUtil.serialize(courses, courseDir);
		} catch (IOException e1) {
			System.out.println("Course Serialization Failed");
			//e1.printStackTrace();
		}
	}

	@FXML
	private Rectangle sun;

	@FXML
	private Rectangle mon;

	@FXML
	private Rectangle tue;

	@FXML
	private Rectangle wed;

	@FXML
	private Rectangle thu;

	@FXML
	private Rectangle fri;

	@FXML
	private Rectangle sat;

	public void setGraphs() {

		sun.setHeight(0);
		mon.setHeight(0);
		tue.setHeight(0);
		wed.setHeight(0);
		thu.setHeight(0);
		fri.setHeight(0);
		sat.setHeight(0);		

		double[] heights = new double[7];
		ArrayList<String> lectures = courseCreator.getAllLectureDates().get(indexOfLecture);

		for (int i = 0; i < lectures.size(); i++) {
			String current = lectures.get(i);
			int time;
			time = Integer.valueOf(current.substring(current.indexOf('-') + 1,
					current.indexOf('-') + 3)) - Integer.valueOf(current.substring(current.indexOf(' ') + 1,
							current.indexOf(':')));

			if (lectures.get(i).startsWith("Mon")) {
				heights[1] = heights[1] + time * 10;
			}
			else if (lectures.get(i).startsWith("Tue")) {
				heights[2] = heights[2] + time * 10;
			}
			else if (lectures.get(i).startsWith("Wed")) {
				heights[3] = heights[3] + time * 10;
			}
			else if (lectures.get(i).startsWith("Thu")) {
				heights[4] = heights[4] + time * 10;
			}
			else if (lectures.get(i).startsWith("Fri")) {
				heights[5] = heights[5] + time * 10;
			}
		}

		Timeline eGraphAnimator = new Timeline(new KeyFrame(Duration.seconds(0.017), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {				
				if (sun.getHeight() < heights[0])  {
					sun.setHeight(sun.getHeight() + 5);
				}

				if (mon.getHeight() < heights[1])  {
					mon.setHeight(mon.getHeight() + 5);
				}

				if (tue.getHeight() < heights[2])  {
					tue.setHeight(tue.getHeight() + 5);
				}

				if (wed.getHeight() < heights[3])  {
					wed.setHeight(wed.getHeight() + 5);
				}

				if (thu.getHeight() < heights[4])  {
					thu.setHeight(thu.getHeight() + 5);
				}

				if (fri.getHeight() < heights[5])  {
					fri.setHeight(fri.getHeight() + 5);
				}

				if (sat.getHeight() < heights[6])  {
					sat.setHeight(sat.getHeight() + 5);
				}			
			}
		}));

		eGraphAnimator.setCycleCount(13);
		eGraphAnimator.play();
	}

	public void showButtons(MouseEvent e) {
		if (e.getSource().equals(close)) {
			closed.setVisible(true);
			minimized.setVisible(true);
		}
		else if (e.getSource().equals(minimize)) {
			closed.setVisible(true);
			minimized.setVisible(true);
		}
	}

	public void hideButtons(MouseEvent e) {
		if (e.getSource().equals(closed)) {
			closed.setVisible(false);
			minimized.setVisible(false);
		}
		else if (e.getSource().equals(minimized)) {
			closed.setVisible(false);
			minimized.setVisible(false);
		}
	}

	public void minimize(MouseEvent e) {
		Stage stage=(Stage) minimized.getScene().getWindow();
		stage.setIconified(true);
	}

	public void close(MouseEvent e) {
		Stage stage=(Stage) closed.getScene().getWindow();
		stage.close();
	}

	@Override
	public void initialize (URL location, ResourceBundle resources) {

		try {
			new SerializationUtil();
			String os = System.getProperty("os.name").toUpperCase();
			
			if (os.contains("MAC")) {
				users = (Users) SerializationUtil.deserialize(usersDirMac);
				userProfile = users.getActiveUser();
				courseDir = System.getProperty("user.home") + "/Library/Application Support/Bias/" 
						+ userProfile.getDirectory()  +"/courses.ser";
			}
			else if (os.contains("WIN")) {
				users = (Users) SerializationUtil.deserialize(usersDirWin);
				userProfile = users.getActiveUser();
				courseDir = System.getenv("APPDATA") + "\\Bias\\" + userProfile.getDirectory() + "\\courses.ser";
			}
		} catch (ClassNotFoundException | IOException e) {
			//e.printStackTrace();
		}	

		try {
			new SerializationUtil();
			courses = (EnrolledCourses) SerializationUtil.deserialize(courseDir);	
		} catch (ClassNotFoundException | IOException e) {
			//e.printStackTrace();
		}
		
		String[] courseNew = new String[courseCreator.getFaculties().size()];

		for (int i = 0; i < courseCreator.getFaculties().size(); i++) {
			courseNew[i] = courseCreator.getFaculties().get(i);
		}

		Set<String> departments = new HashSet<String>(Arrays.asList(courseNew));
		department.getEntries().addAll(departments);

		setDepartment();
		setCourse();
		setSection();

		PauseTransition pause = new PauseTransition(Duration.seconds(1));
		section.textProperty().addListener((observable, oldValue, newValue) -> {
			pause.setOnFinished(event -> setSection());
			pause.playFromStart();
		});		
	}
}