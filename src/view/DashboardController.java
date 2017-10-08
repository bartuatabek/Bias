package view;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.ResourceBundle;
import java.util.TimeZone;

import org.controlsfx.control.PopOver;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import application.Activity;
import application.Assignment;
import application.EventInfo;
import application.NoteCell;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import model.Assignments;
import model.CourseCreator;
import model.CustomEvent;
import model.DocFile;
import model.EnrolledCourses;
import model.LectureEventInfo;
import model.LectureNote;
import model.NoteList;
import model.Profile;
import model.QuizzesAndPresentations;
import model.SerializationUtil;
import model.Settings;
import model.StudentAssignment;
import model.Users;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * Dashboard.fxml Controller Class
 */
public class DashboardController implements Initializable {

	private static final String usersDirMac = System.getProperty("user.home") 
			+ "/Library/Application Support/Bias/users.ser";
	
	private static final String usersDirWin = System.getenv("APPDATA") + "\\Bias\\users.ser";

	private Users users = new Users();

	private Profile userProfile = new Profile();

	private Assignments assignments = new Assignments();

	private NoteList noteList = new NoteList();

	private Settings settings = new Settings();

	private CustomEvent ce = new CustomEvent();

	private LectureEventInfo lectureEventInfo = new LectureEventInfo();

	private QuizzesAndPresentations qp = new QuizzesAndPresentations();

	private EnrolledCourses ec = new EnrolledCourses();

	private ArrayList<Label> lectures = new ArrayList<>();

	private ArrayList<Label> rooms = new ArrayList<>();

	private ArrayList<JFXButton> quizzes = new ArrayList<>();

	private ArrayList<JFXButton> presentations = new ArrayList<>();

	private String courseDir;

	private String assDir;

	private String noteDir;

	private String settingsDir;

	private String eventsDir;

	private String qpDir;

	private String eventInfoDir;

	@FXML
	private ImageView homeButton;

	@FXML
	private ImageView calendarButton;

	@FXML
	private ImageView lectureButton;

	@FXML
	private Circle profileButton;

	@FXML
	private AnchorPane root;

	@FXML
	private void handleMenuAction(MouseEvent e) throws IOException{

		AnchorPane pane;

		if (e.getSource() == profileButton) {
			pane = FXMLLoader.load(getClass().getResource("/view/Profile.fxml"));
		}
		else if (e.getSource() == calendarButton) {
			pane = FXMLLoader.load(getClass().getResource("/view/Calendar.fxml"));
		}
		else {
			pane = FXMLLoader.load(getClass().getResource("/view/Courses.fxml"));
		}

		root.getChildren().setAll(pane);

		try {
			new SerializationUtil();
			SerializationUtil.serialize(assignments, assDir);
		} catch (IOException e1) {
			System.out.println("Serialization Failed");
			e1.printStackTrace();
		}

		try {
			new SerializationUtil();
			SerializationUtil.serialize(noteList, noteDir);
		} catch (IOException e1) {
			System.out.println("Serialization Failed");
			e1.printStackTrace();
		}
		arrowCheck.stop();
		assCheck.stop();
		noteCheck.stop();
		notifyCheck.stop();
		notifyLectures.stop();	
		updatePanes.stop();
		silentCheck.stop();
	}

	@FXML
	private ImageView arrow1;

	@FXML
	private ImageView arrow2;

	@FXML
	private ImageView arrow3;

	@FXML
	private ImageView arrow4;

	@FXML
	private ImageView arrow5;

	@FXML
	private ImageView arrow6;

	@FXML
	private ImageView arrow7;

	@FXML
	private ImageView arrow8;

	// method checks and updates current time in the today view panel

	Timeline arrowCheck;

	public void arrowUpdate() {
		arrowCheck = new Timeline(new KeyFrame(Duration.seconds(0.1), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Date date = new Date();  
				Calendar calendar = GregorianCalendar.getInstance(); 
				calendar.setTime(date); 
				String hour = "" + calendar.get(Calendar.HOUR_OF_DAY);

				if (calendar.get(Calendar.MINUTE) < 10)
					hour += "0" + calendar.get(Calendar.MINUTE);
				else
					hour += calendar.get(Calendar.MINUTE);  

				if (Integer.parseInt(hour) >= 840 && Integer.parseInt(hour) < 940) {
					arrow1.setVisible(true);
				}

				else if (Integer.parseInt(hour) >= 940 && Integer.parseInt(hour) < 1040) {
					arrow1.setVisible(false);
					arrow2.setVisible(true);
				}

				else if (Integer.parseInt(hour) >= 1040 && Integer.parseInt(hour) < 1140) {
					arrow2.setVisible(false);
					arrow3.setVisible(true);
				}
				else if (Integer.parseInt(hour) >= 1140 && Integer.parseInt(hour) < 1240) {
					arrow3.setVisible(false);
					arrow4.setVisible(true);
				}

				else if (Integer.parseInt(hour) >= 1240 && Integer.parseInt(hour) < 1440) {
					arrow4.setVisible(false);
					arrow5.setVisible(true);
				}

				else if (Integer.parseInt(hour) >= 1440 && Integer.parseInt(hour) < 1540) {
					arrow6.setVisible(false);
					arrow7.setVisible(true);
				}

				else if (Integer.parseInt(hour) >= 1540 && Integer.parseInt(hour) < 1640) {
					arrow7.setVisible(false);
					arrow8.setVisible(true);
				}

				else {
					arrow7.setVisible(false);
					arrow8.setVisible(true);
				}
			}
		}));

		arrowCheck.setCycleCount(Timeline.INDEFINITE);
		arrowCheck.play();
	}

	// lecture events

	@FXML
	private Pane todayPane1;

	@FXML
	private Pane todayPane2;

	@FXML
	private Pane todayPane3;

	@FXML
	private Pane todayPane4;

	@FXML
	private Pane todayPane5;

	@FXML
	private Pane todayPane6;

	@FXML
	private Pane todayPane7;

	@FXML
	private Pane todayPane8;

	// peek event info method allows to peek into event details 
	PopOver eventInfo = new PopOver();
	ArrayList<EventInfo> allLectureInfo = new ArrayList<>();

	public void peekEventInfo(MouseEvent e) throws IOException {

		eventInfo.setAutoHide(true);
		eventInfo.setAnimated(true);
		eventInfo.setDetachable(false);
		eventInfo.setArrowLocation(PopOver.ArrowLocation.LEFT_TOP);
		eventInfo.setCornerRadius(10.0); 

		EventInfo ei;

		if (e.getSource() == todayPane1 && !lectureName1.getText().equals("")) {

			ei = allLectureInfo.get(0);
			ei.setEditableFalse();
			ei.setMailAddress(userProfile.getMail());
			eventInfo.setContentNode(ei); 
			eventInfo.show(todayPane1);
		}

		else if (e.getSource() == todayPane2 && !lectureName2.getText().equals("")) {

			ei = allLectureInfo.get(1);
			ei.setEditableFalse();
			ei.setMailAddress(userProfile.getMail());
			eventInfo.setContentNode(ei); 
			eventInfo.show(todayPane2);
		}

		else if (e.getSource() == todayPane3 && !lectureName3.getText().equals("")) {

			ei = allLectureInfo.get(2);
			ei.setEditableFalse();
			ei.setMailAddress(userProfile.getMail());
			eventInfo.setContentNode(ei); 
			eventInfo.show(todayPane3);
		}

		else if (e.getSource() == todayPane4 && !lectureName4.getText().equals("")) {

			ei = allLectureInfo.get(3);
			ei.setEditableFalse();
			ei.setMailAddress(userProfile.getMail());
			eventInfo.setContentNode(ei); 
			eventInfo.show(todayPane4);
		}

		else if (e.getSource() == todayPane5 && !lectureName5.getText().equals("")) {

			ei = allLectureInfo.get(4);
			ei.setEditableFalse();
			ei.setMailAddress(userProfile.getMail());
			eventInfo.setContentNode(ei); 
			eventInfo.show(todayPane5);
		}

		else if (e.getSource() == todayPane6 && !lectureName6.getText().equals("")) {

			ei = allLectureInfo.get(5);
			ei.setEditableFalse();
			ei.setMailAddress(userProfile.getMail());
			eventInfo.setContentNode(ei); 
			eventInfo.show(todayPane6);
		}

		else if (e.getSource() == todayPane7 && !lectureName7.getText().equals("")) {

			ei = allLectureInfo.get(6);
			ei.setEditableFalse();
			ei.setMailAddress(userProfile.getMail());
			eventInfo.setContentNode(ei); 
			eventInfo.show(todayPane7);
		}

		else if (e.getSource() == todayPane8 && !lectureName8.getText().equals("")) {

			ei = allLectureInfo.get(7);
			ei.setEditableFalse();
			ei.setMailAddress(userProfile.getMail());
			eventInfo.setContentNode(ei); 
			eventInfo.show(todayPane8);
		}
	}

	public void setLectureInfos() throws IOException {
		Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
		String day = ("" + calendar.get(Calendar.DATE));

		Date date = new Date();  
		Calendar cal = GregorianCalendar.getInstance(); 
		cal.setTime(date); 
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		String today = "";

		if (dayOfWeek == 1)
			today = "Sun";
		else if (dayOfWeek == 2)
			today = "Mon";
		else if (dayOfWeek == 3)
			today = "Tue";
		else if (dayOfWeek == 4)
			today = "Wed";
		else if (dayOfWeek == 5)
			today = "Thu";
		else if (dayOfWeek == 6)
			today = "Fri";
		else if (dayOfWeek % 7 == 0)
			today = "Sat";

		ArrayList<String> days = lectureEventInfo.loadDays();
		ArrayList<Integer> hours = lectureEventInfo.loadHours();

		allLectureInfo = new ArrayList<>();

		for (int i = 0; i < 8; i++) {
			allLectureInfo.add(null);
		}

		for (int i = 0; i < lectureEventInfo.loadDays().size(); i++) {
			if (hours.get(i) == 8 && days.get(i).equalsIgnoreCase(today)) {
				EventInfo ei = new EventInfo(lectureName1.getText(), room1.getText(), day, "8:40 AM" , "9:30 AM");
				ei.setNotes(lectureEventInfo.loadNotes().get(i));
				ei.setSilentAndBell(lectureEventInfo.loadSilents().get(i));
				allLectureInfo.set(0, ei);
				ei = null;
			}

			else if (hours.get(i) == 9 && days.get(i).equalsIgnoreCase(today)) {
				EventInfo ei = new EventInfo(lectureName2.getText(), room2.getText(), day, "9:40 AM" , "10:30 AM");
				ei.setNotes(lectureEventInfo.loadNotes().get(i));
				ei.setSilentAndBell(lectureEventInfo.loadSilents().get(i));
				allLectureInfo.set(1, ei);
				ei = null;
			}

			else if (hours.get(i) == 10 && days.get(i).equalsIgnoreCase(today)) {
				EventInfo ei = new EventInfo(lectureName3.getText(), room3.getText(), day, "10:40 AM" , "11:30 AM");
				ei.setNotes(lectureEventInfo.loadNotes().get(i));
				ei.setSilentAndBell(lectureEventInfo.loadSilents().get(i));
				allLectureInfo.set(2, ei);
				ei = null;
			}

			else if (hours.get(i) == 11 && days.get(i).equalsIgnoreCase(today)) {
				EventInfo ei = new EventInfo(lectureName4.getText(), room4.getText(), day, "11:40 AM" , "12:30 AM");
				ei.setNotes(lectureEventInfo.loadNotes().get(i));
				ei.setSilentAndBell(lectureEventInfo.loadSilents().get(i));
				allLectureInfo.set(3, ei);
				ei = null;
			}

			else if (hours.get(i) == 13 && days.get(i).equalsIgnoreCase(today)) {
				EventInfo ei = new EventInfo(lectureName5.getText(), room5.getText(), day, "13:40 AM" , "14:30 AM");
				ei.setNotes(lectureEventInfo.loadNotes().get(i));
				ei.setSilentAndBell(lectureEventInfo.loadSilents().get(i));
				allLectureInfo.set(4, ei);
				ei = null;
			}

			else if (hours.get(i) == 14 && days.get(i).equalsIgnoreCase(today)) {
				EventInfo ei = new EventInfo(lectureName6.getText(), room6.getText(), day, "14:40 AM" , "15:30 AM");
				ei.setNotes(lectureEventInfo.loadNotes().get(i));
				ei.setSilentAndBell(lectureEventInfo.loadSilents().get(i));
				allLectureInfo.set(5, ei);
				ei = null;
			}

			else if (hours.get(i) == 15 && days.get(i).equalsIgnoreCase(today)) {
				EventInfo ei = new EventInfo(lectureName7.getText(), room7.getText(), day, "15:40 AM" , "16:30 AM");
				ei.setNotes(lectureEventInfo.loadNotes().get(i));
				ei.setSilentAndBell(lectureEventInfo.loadSilents().get(i));
				allLectureInfo.set(6, ei);
				ei = null;
			}

			else if (hours.get(i) == 16 && days.get(i).equalsIgnoreCase(today)) {
				EventInfo ei = new EventInfo(lectureName8.getText(), room8.getText(), day, "16:40 AM" , "17:30 AM");
				ei.setNotes(lectureEventInfo.loadNotes().get(i));
				ei.setSilentAndBell(lectureEventInfo.loadSilents().get(i));
				allLectureInfo.set(7, ei);
				ei = null;
			}
		}
	}

	Timeline updatePanes;

	public void saveLectureInfos () {
		updatePanes = new Timeline(new KeyFrame(Duration.seconds(0.1), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Date date = new Date();
				Calendar calendar = GregorianCalendar.getInstance();
				calendar.setTime(date);
				int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
				String today = "";

				if (dayOfWeek == 1)
					today = "Sun";
				else if (dayOfWeek == 2)
					today = "Mon";
				else if (dayOfWeek == 3)
					today = "Tue";
				else if (dayOfWeek == 4)
					today = "Wed";
				else if (dayOfWeek == 5)
					today = "Thu";
				else if (dayOfWeek == 6)
					today = "Fri";
				else if (dayOfWeek % 7 == 0)
					today = "Sat";

				ArrayList<String> days = lectureEventInfo.loadDays();
				ArrayList<Integer> hours = lectureEventInfo.loadHours();

				if (allLectureInfo.get(0) != null) {
					for (int i = 0; i < lectureEventInfo.loadDays().size(); i++) {
						if (hours.get(i) == 8 && days.get(i).equalsIgnoreCase(today)) {
							lectureEventInfo.loadNotes().set(i, allLectureInfo.get(0).getNotes());
							lectureEventInfo.loadSilents().set(i, allLectureInfo.get(0).getSilent());
						}
					}
				}

				if (allLectureInfo.get(1) != null) {
					for (int i = 0; i < lectureEventInfo.loadDays().size(); i++) {
						if (hours.get(i) == 9 && days.get(i).equalsIgnoreCase(today)) {
							lectureEventInfo.loadNotes().set(i, allLectureInfo.get(1).getNotes());
							lectureEventInfo.loadSilents().set(i, allLectureInfo.get(1).getSilent());
						}
					}
				}

				if (allLectureInfo.get(2) != null) {
					for (int i = 0; i < lectureEventInfo.loadDays().size(); i++) {
						if (hours.get(i) == 10 && days.get(i).equalsIgnoreCase(today)) {
							lectureEventInfo.loadNotes().set(i, allLectureInfo.get(2).getNotes());
							lectureEventInfo.loadSilents().set(i, allLectureInfo.get(2).getSilent());
						}
					}
				}

				if (allLectureInfo.get(3) != null) {
					for (int i = 0; i < lectureEventInfo.loadDays().size(); i++) {
						if (hours.get(i) == 11 && days.get(i).equalsIgnoreCase(today)) {
							lectureEventInfo.loadNotes().set(i, allLectureInfo.get(3).getNotes());
							lectureEventInfo.loadSilents().set(i, allLectureInfo.get(3).getSilent());
						}
					}
				}

				if (allLectureInfo.get(4) != null) {
					for (int i = 0; i < lectureEventInfo.loadDays().size(); i++) {
						if (hours.get(i) == 13 && days.get(i).equalsIgnoreCase(today)) {
							lectureEventInfo.loadNotes().set(i, allLectureInfo.get(4).getNotes());
							lectureEventInfo.loadSilents().set(i, allLectureInfo.get(4).getSilent());
						}
					}
				}

				if (allLectureInfo.get(5) != null) {
					for (int i = 0; i < lectureEventInfo.loadDays().size(); i++) {
						if (hours.get(i) == 14 && days.get(i).equalsIgnoreCase(today)) {
							lectureEventInfo.loadNotes().set(i, allLectureInfo.get(5).getNotes());
							lectureEventInfo.loadSilents().set(i, allLectureInfo.get(5).getSilent());
						}
					}
				}

				if (allLectureInfo.get(6) != null) {
					for (int i = 0; i < lectureEventInfo.loadDays().size(); i++) {
						if (hours.get(i) == 15 && days.get(i).equalsIgnoreCase(today)) {
							lectureEventInfo.loadNotes().set(i, allLectureInfo.get(6).getNotes());
							lectureEventInfo.loadSilents().set(i, allLectureInfo.get(6).getSilent());
						}
					}
				}

				if (allLectureInfo.get(7) != null) {
					for (int i = 0; i < lectureEventInfo.loadDays().size(); i++) {
						if (hours.get(i) == 16 && days.get(i).equalsIgnoreCase(today)) {
							lectureEventInfo.loadNotes().set(i, allLectureInfo.get(7).getNotes());
							lectureEventInfo.loadSilents().set(i, allLectureInfo.get(7).getSilent());
						}
					}
				}
			}
		}));
		updatePanes.setCycleCount(Timeline.INDEFINITE);
		updatePanes.play();	
	}

	public void serializeLectureInfos (MouseEvent e) {
		try {
			new SerializationUtil();
			SerializationUtil.serialize(lectureEventInfo, eventInfoDir);
		} catch (IOException e1) {
			System.out.println("Serialization Failed");
			e1.printStackTrace();
		}
	}

	@FXML
	private Label lectureName1;

	@FXML
	private Label lectureName2;

	@FXML
	private Label lectureName3;

	@FXML
	private Label lectureName4;

	@FXML
	private Label lectureName5;

	@FXML
	private Label lectureName6;

	@FXML
	private Label lectureName7;

	@FXML
	private Label lectureName8;


	@FXML
	private Label room1;

	@FXML
	private Label room2;

	@FXML
	private Label room3;

	@FXML
	private Label room4;

	@FXML
	private Label room5;

	@FXML
	private Label room6;

	@FXML
	private Label room7;

	@FXML
	private Label room8;


	@FXML 
	private JFXButton q1;

	@FXML 
	private JFXButton q2;

	@FXML 
	private JFXButton q3;

	@FXML 
	private JFXButton q4;

	@FXML 
	private JFXButton q5;

	@FXML 
	private JFXButton q6;

	@FXML 
	private JFXButton q7;

	@FXML 
	private JFXButton q8;


	@FXML 
	private JFXButton p1;

	@FXML 
	private JFXButton p2;

	@FXML 
	private JFXButton p3;

	@FXML 
	private JFXButton p4;

	@FXML 
	private JFXButton p5;

	@FXML 
	private JFXButton p6;

	@FXML 
	private JFXButton p7;

	@FXML 
	private JFXButton p8;

	public void setToday () {

		Date date = new Date();
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(date);
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		String today = "";

		if (dayOfWeek == 1)
			today = "Sun";
		else if (dayOfWeek == 2)
			today = "Mon";
		else if (dayOfWeek == 3)
			today = "Tue";
		else if (dayOfWeek == 4)
			today = "Wed";
		else if (dayOfWeek == 5)
			today = "Thu";
		else if (dayOfWeek == 6)
			today = "Fri";
		else if (dayOfWeek % 7 == 0)
			today = "Sat";

		ArrayList<Integer> enrolledCourses = ec.load();
		CourseCreator cc = new CourseCreator();

		lectures.add(lectureName1);
		lectures.add(lectureName2);
		lectures.add(lectureName3);
		lectures.add(lectureName4);
		lectures.add(lectureName5);
		lectures.add(lectureName6);
		lectures.add(lectureName7);
		lectures.add(lectureName8);

		rooms.add(room1);
		rooms.add(room2);
		rooms.add(room3);
		rooms.add(room4);
		rooms.add(room5);
		rooms.add(room6);
		rooms.add(room7);
		rooms.add(room8);

		quizzes.add(q1);
		quizzes.add(q2);
		quizzes.add(q3);
		quizzes.add(q4);
		quizzes.add(q5);
		quizzes.add(q6);
		quizzes.add(q7);
		quizzes.add(q8);

		presentations.add(p1);
		presentations.add(p2);
		presentations.add(p3);
		presentations.add(p4);
		presentations.add(p5);
		presentations.add(p6);
		presentations.add(p7);
		presentations.add(p8);

		for (int i = 0; i < 8; i++) {
			lectures.get(i).setText("");
			rooms.get(i).setText("");
		}

		ArrayList<String> days = new ArrayList<>();
		ArrayList<Integer> hours = new ArrayList<>();
		ArrayList<String> lecRooms = new  ArrayList<>();

		for (int i = 0; i < enrolledCourses.size(); i++) {
			int index = enrolledCourses.get(i);

			for (int j = 0; j < cc.getAllLectureDates().get(index).size(); j++)  {
				String current = cc.getAllLectureDates().get(index).get(j);
				int start1 = Integer.valueOf(current.substring(current.indexOf(' ') + 1, current.indexOf(':')));
				int end1 = Integer.valueOf(current.substring(current.indexOf('-') + 1, current.indexOf('-') + 3));

				//LECTURE EVENT INFO

				for (int k = 0; k < end1 - start1; k++) {
					days.add(current.substring(0, current.indexOf(" ")));
					hours.add(start1 + k);
					lecRooms.add(current.substring(current.lastIndexOf(":") + 4));
				}

				//LECTURE EVENT INFO

				if (current.startsWith(today))  {
					int arrayStartIndex = 0;

					if (start1 == 9)
						arrayStartIndex = 1;
					else if (start1 == 10)
						arrayStartIndex = 2;
					else if (start1 == 11)
						arrayStartIndex = 3;
					else if (start1 == 13)
						arrayStartIndex = 4;
					else if (start1 == 14)
						arrayStartIndex = 5;
					else if (start1 == 15)
						arrayStartIndex = 6;
					else if (start1 == 16)
						arrayStartIndex = 7;

					String lecture = "";
					String room = "";	

					for (int k = arrayStartIndex; k < arrayStartIndex + end1 - start1; k++) {
						if (k == arrayStartIndex) {	
							lecture = cc.getAllFaculties().get(index) + "-" + cc.getAllCodes().get(index);
							room = current.substring(current.lastIndexOf(":") + 4);
						}

						lectures.get(k).setText(lecture);
						rooms.get(k).setText(room);
					}
				}
			}
		}

		lectureEventInfo.saveHoursAndDays(hours, days, lecRooms);

		if (lectureEventInfo.loadNotes().size() != lectureEventInfo.loadDays().size()) {
			lectureEventInfo.initializeNotesAndSilents(lectureEventInfo.loadDays().size());
		}

		try {
			new SerializationUtil();
			SerializationUtil.serialize(lectureEventInfo, eventInfoDir);
		} catch (IOException e) {
			System.out.println("Serialization Failed");
			e.printStackTrace();
		}

		for (int i = 0; i < quizzes.size(); i++) {
			for (int j = 0; j < qp.loadPresentations().size(); j++) {
				if (i == qp.loadPresentations().get(j))
					presentations.get(i).setText("✓");
			}

			for (int j = 0; j < qp.loadQuizzes().size(); j++) {
				if (i == qp.loadQuizzes().get(j))
					quizzes.get(i).setText("✓");
			}
		}

		for (int i = 0; i < quizzes.size(); i++) {
			if (lectures.get(i).getText().equals("")) {
				quizzes.get(i).setVisible(false);
				presentations.get(i).setVisible(false);
			}
		}
	}

	public void addQP( ActionEvent e) {
		if (e.getSource() == q1) {
			if (q1.getText().equals("Q")) {
				q1.setText("✓");
			}
			else {
				q1.setText("Q");
			}
		}

		else if (e.getSource() == q2) {
			if (q2.getText().equals("Q")) {
				q2.setText("✓");
			}
			else {
				q2.setText("Q");
			}
		}

		else if (e.getSource() == q3) {
			if (q3.getText().equals("Q")) {
				q3.setText("✓");
			}
			else {
				q3.setText("Q");
			}
		}

		else if (e.getSource() == q4) {
			if (q4.getText().equals("Q")) {
				q4.setText("✓");
			}
			else {
				q4.setText("Q");
			}
		}

		else if (e.getSource() == q5) {
			if (q5.getText().equals("Q")) {
				q5.setText("✓");
			}
			else {
				q5.setText("Q");
			}
		}

		else if (e.getSource() == q6) {
			if (q6.getText().equals("Q")) {
				q6.setText("✓");
			}
			else {
				q6.setText("Q");
			}
		}

		else if (e.getSource() == q7) {
			if (q7.getText().equals("Q")) {
				q7.setText("✓");
			}
			else {
				q7.setText("Q");
			}
		}

		else if (e.getSource() == q8) {
			if (q8.getText().equals("Q")) {
				q8.setText("✓");
			}
			else {
				q8.setText("Q");
			}
		}

		else if (e.getSource() == p1) {
			if (p1.getText().equals("P")) {
				p1.setText("✓");
			}
			else {
				p1.setText("P");
			}
		}

		else if (e.getSource() == p2) {
			if (p2.getText().equals("P")) {
				p2.setText("✓");
			}
			else {
				p2.setText("P");
			}
		}

		else if (e.getSource() == p3) {
			if (p3.getText().equals("P")) {
				p3.setText("✓");
			}
			else {
				p3.setText("P");
			}
		}

		else if (e.getSource() == p4) {
			if (p4.getText().equals("P")) {
				p4.setText("✓");
			}
			else {
				p4.setText("P");
			}
		}

		else if (e.getSource() == p5) {
			if (p5.getText().equals("P")) {
				p5.setText("✓");
			}
			else {
				p5.setText("P");
			}
		}

		else if (e.getSource() == p6) {
			if (p6.getText().equals("P")) {
				p6.setText("✓");
			}
			else {
				p6.setText("P");
			}
		}

		else if (e.getSource() == p7) {
			if (p7.getText().equals("P")) {
				p7.setText("✓");
			}
			else {
				p7.setText("P");
			}		}

		else if (e.getSource() == p8) {
			if (p8.getText().equals("P")) {
				p8.setText("✓");
			}
			else {
				p8.setText("P");
			}
		}
	}

	public void saveQP (MouseEvent e) {
		Date date = new Date();
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(date);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int month = calendar.get(Calendar.MONTH);

		ArrayList<Integer> checkedQuizzes = new ArrayList<>();
		ArrayList<Integer> checkedPresentations = new ArrayList<>();

		for (int i = 0; i < quizzes.size(); i++) {
			if (quizzes.get(i).getText().equals("✓"))
				checkedQuizzes.add(i);

			if (presentations.get(i).getText().equals("✓"))
				checkedPresentations.add(i);
		}

		qp.save(checkedQuizzes, checkedPresentations, month, day);

		try {
			new SerializationUtil();
			SerializationUtil.serialize(qp, qpDir);
		} catch (IOException e1) {
			System.out.println("Serialization Failed");
			e1.printStackTrace();
		}

		serializeLectureInfos(e);
		serializeActivities(e);
	}

	// activities 

	@FXML
	private VBox activities;

	@FXML
	private ScrollPane activityScroller;

	CustomEvent arrangedEvents = new CustomEvent();

	public void constructActivities() throws IOException {
		Date date = new Date();  
		Calendar calendar = GregorianCalendar.getInstance(); 
		calendar.setTime(date); 
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		int weekOfMonth = calendar.get(Calendar.WEEK_OF_MONTH);
		String day = "";

		if (dayOfWeek == 1)
			day = "Sun";
		else if (dayOfWeek == 2)
			day = "Mon";
		else if (dayOfWeek == 3)
			day = "Tue";
		else if (dayOfWeek == 4)
			day = "Wed";
		else if (dayOfWeek == 5)
			day = "Thu";
		else if (dayOfWeek == 6)
			day = "Fri";
		else if (dayOfWeek == 7)
			day = "Sat";

		arrangedEvents = new CustomEvent();
		int minIndex = 0;
		int startTime = 0;

		while (ce.getDays().size() != 0) {
			startTime = Integer.valueOf(""+ ce.getStartHours().get(0).intValue() + ce.getStartMins().get(0).intValue());
			minIndex = 0;

			for (int i = 0; i < ce.getDays().size(); i++)  {
				if ( Integer.valueOf(""+ ce.getStartHours().get(i).intValue() + ce.getStartMins().get(i).intValue()) < startTime) {
					minIndex = i;
					startTime = Integer.valueOf(""+ ce.getStartHours().get(i).intValue() + ce.getStartMins().get(i).intValue());
				}	
			}

			arrangedEvents.addEvent(ce.getStartHours().get(minIndex), ce.getStartMins().get(minIndex), ce.getHeights().get(minIndex), ce.getInfo().get(minIndex), ce.getDays().get(minIndex), ce.getLocations().get(minIndex), ce.getNotes().get(minIndex), ce.getSilenced().get(minIndex), weekOfMonth);

			ce.getDays().remove(minIndex);
			ce.getStartHours().remove(minIndex);
			ce.getStartMins().remove(minIndex);
			ce.getHeights().remove(minIndex);
			ce.getInfo().remove(minIndex);
			ce.getLocations().remove(minIndex);
			ce.getNotes().remove(minIndex);
			ce.getSilenced().remove(minIndex);
		}

		for (int i = 0; i < arrangedEvents.getDays().size(); i++) {
			if (day.equalsIgnoreCase(arrangedEvents.getDays().get(i))) {
				String eventTime = arrangedEvents.getStartHours().get(i).intValue() + ":" + arrangedEvents.getStartMins().get(i).intValue();
				int eventStart = Integer.valueOf(eventTime.substring(0, eventTime.indexOf(":"))) * 60 + Integer.valueOf(eventTime.substring(eventTime.indexOf(":") + 1));
				String eventName = arrangedEvents.getInfo().get(i);
				int eventDuration = (int)(arrangedEvents.getHeights().get(i) * 60.0 / (1250.0 / 24.0));

				int eventEnd = (eventStart + eventDuration) % 1440;
				String endHour = eventEnd / 60 + "";
				String endMin = eventEnd % 60 + "";

				if (endHour.length() < 2)
					endHour = "0" + endHour;
				if (endMin.length() < 2)
					endMin = "0" + endMin;

				String eventEndTime = endHour + ":" + endMin;

				try {       
					SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm");
					SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm a");
					Date _24HourDt = _24HourSDF.parse(eventTime);
					eventTime = _12HourSDF.format(_24HourDt);
				} catch (Exception e) {
					e.printStackTrace();
				}

				try {       
					SimpleDateFormat __24HourSDF = new SimpleDateFormat("HH:mm");
					SimpleDateFormat __12HourSDF = new SimpleDateFormat("hh:mm a");
					Date __24HourDt = __24HourSDF.parse(eventEndTime);
					eventEndTime = __12HourSDF.format(__24HourDt);
				} catch (Exception e) {
					e.printStackTrace();
				}

				Activity a = new Activity (eventTime, eventEndTime, eventName, arrangedEvents.getLocations().get(i));
				a.setSilentAndBell(arrangedEvents.getSilenced().get(i));
				activities.getChildren().add(a);
			}
		}

		try {
			new SerializationUtil();
			ce =  (CustomEvent) SerializationUtil.deserialize(eventsDir);			
		} catch (ClassNotFoundException | IOException e) {
			System.out.println("Serialization Failed");
		}
	}

	Timeline silentCheck;

	public void updateActivities() {
		silentCheck = new Timeline(new KeyFrame(Duration.seconds(0.1), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Date date = new Date();  
				Calendar calendar = GregorianCalendar.getInstance(); 
				calendar.setTime(date); 
				int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
				String day = "";

				if (dayOfWeek == 1)
					day = "Sun";
				else if (dayOfWeek == 2)
					day = "Mon";
				else if (dayOfWeek == 3)
					day = "Tue";
				else if (dayOfWeek == 4)
					day = "Wed";
				else if (dayOfWeek == 5)
					day = "Thu";
				else if (dayOfWeek == 6)
					day = "Fri";
				else if (dayOfWeek == 7)
					day = "Sat";

				for (int i = 0; i < activities.getChildren().size(); i++) {
					String startTime = ((Activity)activities.getChildren().get(i)).getStartingTime();
					String eventName = ((Activity)activities.getChildren().get(i)).getActivityName();
					String location = ((Activity)activities.getChildren().get(i)).getLocation();

					int startHour = Integer.valueOf(startTime.substring(0, startTime.indexOf(":")));
					int startMin = Integer.valueOf(startTime.substring(startTime.indexOf(":") + 1, startTime.indexOf(" ")));

					if (startTime.endsWith("PM") && startHour != 12)
						startHour = startHour + 12;

					for (int j = 0; j < ce.getDays().size(); j++) {
						if (ce.getStartHours().get(j) == startHour && ce.getStartMins().get(j) == startMin 
								&& ce.getLocations().get(j).equals(location) && ce.getInfo().get(j).equals(eventName)
								&& ce.getDays().get(j).equalsIgnoreCase(day)){
							ce.getSilenced().set(j, ((Activity)activities.getChildren().get(i)).getSilent());
						}
					}
				}

			}}));
		silentCheck.setCycleCount(Timeline.INDEFINITE);
		silentCheck.play();
	}

	public void serializeActivities (MouseEvent e) {
		try {
			new SerializationUtil();
			SerializationUtil.serialize(ce, eventsDir);
		} catch (IOException e1) {
			System.out.println("Serialization Failed");
			e1.printStackTrace();
		}
	}

	Timeline notifyCheck;

	public void notifyActivities() {
		notifyCheck = new Timeline(new KeyFrame(Duration.seconds(59), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				try {
					new SerializationUtil();
					ce =  (CustomEvent) SerializationUtil.deserialize(eventsDir);			
				} catch (ClassNotFoundException | IOException e) {
					System.out.println("Serialization Failed");
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

	public void notifyLectures() {
		notifyLectures = new Timeline(new KeyFrame(Duration.seconds(59), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				try {
					new SerializationUtil();
					qp =  (QuizzesAndPresentations) SerializationUtil.deserialize(qpDir);			
				} catch (ClassNotFoundException | IOException e) {
					System.out.println("Serialization Failed");
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

	// assignments

	@FXML
	private JFXButton addAss;	

	@FXML
	private VBox assBox;

	private int slotNo = 0;
	private boolean ass_buffer = false;
	private boolean assSer_buffer = false;
	private int indexOfAssDropTarget;

	private void addAssWithDragging(final VBox root, final Assignment cell) {

		cell.setOnDragDetected(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				indexOfAssDropTarget = root.getChildren().indexOf(cell);			
				addPreview(root,cell);
				cell.setOpacity(0.0);
				cell.toFront();
				cell.startFullDrag();
				ass_buffer = true;
			}
		});

		cell.setOnMouseDragEntered(new EventHandler<MouseDragEvent>() {
			@Override
			public void handle(MouseDragEvent event) {
				((Assignment) cell).hide();
			}
		});

		cell.setOnMouseDragExited(new EventHandler<MouseDragEvent>() {
			@Override
			public void handle(MouseDragEvent event) {
				((Assignment) cell).show();
			}
		});

		cell.setOnMouseDragReleased(new EventHandler<MouseDragEvent>() {
			@Override
			public void handle(MouseDragEvent event) {
				int indexOfDraggingNode = root.getChildren().indexOf(event.getGestureSource());
				int indexOfAssDropTarget = root.getChildren().indexOf(cell);
				rotateNodes(root, indexOfDraggingNode, indexOfAssDropTarget);
				removePreview(root);
				cell.setOpacity(1.0);
				event.consume();
				ass_buffer = false;
			}
		});

		cell.setOnMouseReleased(event -> {
			Point2D mouseLoc = new Point2D(event.getScreenX(), event.getScreenY());
			Bounds bounds = root.getBoundsInLocal();
			Bounds screenBounds = root.localToScreen(bounds);
			int x = (int) screenBounds.getMinX();
			int y = (int) screenBounds.getMinY();
			int width = (int) screenBounds.getWidth();
			int height = (int) screenBounds.getHeight();
			Rectangle2D windowBounds = new Rectangle2D(x, y, width, height);  
			if (! windowBounds.contains(mouseLoc)) {
				int indexOfDraggingNode = root.getChildren().indexOf(event.getSource());
				rotateNodes(root, indexOfDraggingNode, indexOfAssDropTarget);
				removePreview(root);
				cell.setOpacity(1.0);
				ass_buffer = false;
				event.consume();
			}
		});

		root.getChildren().add(cell);
	}

	// add new assignment
	public void newAss(ActionEvent e) throws IOException {

		if (slotNo < 6) {
			slotNo++;
			Assignment assignment = new Assignment();
			addAssWithDragging(assBox, assignment);
			StudentAssignment ass = new StudentAssignment();
			ass.load(((Assignment) assignment).save());
			assignments.add(ass);
		}

		if (slotNo >= 5) {
			addAss.setDisable(true);
			addAss.setText("   ");
		}

		try {
			new SerializationUtil();
			SerializationUtil.serialize(assignments, assDir);
		} catch (IOException e1) {
			System.out.println("Serialization Failed");
			e1.printStackTrace();
		}
	}


	// removes the selected assignments

	Timeline assCheck;

	public void assChecker() {
		assCheck = new Timeline(new KeyFrame(Duration.seconds(0.1), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (!ass_buffer) {
					int expandRate = 0;

					assignments = new Assignments();

					ArrayList<Assignment> toBeRemoved = new ArrayList<Assignment>();

					for (Node n : assBox.getChildren()) {
						if (((Assignment) n).willRemoved()) {
							toBeRemoved.add((Assignment) n);
							assSer_buffer = true;
						}
						else {
							StudentAssignment ass = new StudentAssignment();
							ass.load(((Assignment) n).save());
							assignments.add(ass);
						}
						if (((Assignment) n).isExpanded())
							expandRate++;
					} 

					slotNo -= toBeRemoved.size();

					assBox.getChildren().removeAll(toBeRemoved);

					if (assSer_buffer) {
						try {
							new SerializationUtil();
							SerializationUtil.serialize(assignments, assDir);
						} catch (IOException e1) {
							System.out.println("Serialization Failed");
							e1.printStackTrace();
						}
						assSer_buffer = false;
					}

					if (expandRate >= 2)
						assBox.setPrefHeight((200 * expandRate) + (assBox.getChildren().size() * 10));
					else
						assBox.setPrefHeight(553);

					if (slotNo <= 5) {
						addAss.setDisable(false);
						addAss.setText("+");
					}
				}}}));

		assCheck.setCycleCount(Timeline.INDEFINITE);
		assCheck.play();
	}

	// notes

	@FXML
	private JFXButton saveNote;

	@FXML
	private JFXTextArea note;

	@FXML
	private JFXButton importButton;

	@FXML
	private JFXTextField noteTitle;

	@FXML
	private ScrollPane noteScroller;

	@FXML
	private VBox notes;

	@FXML
	private ImageView move;

	@FXML
	private JFXButton editNote;

	private String userName = System.getProperty("user.name");
	private String strDirectory ="/Users/" + userName + "/Documents/Bias" + File.separator + "Notes";
	private String notePath = "";
	private boolean editable = true;
	private boolean note_buffer = false;
	private boolean ser_buffer = false;
	private int indexOfDropTarget;
	private int start_index;
	private int end_index;


	private void addNoteWithDragging(final VBox root, final NoteCell cell) {

		cell.setOnDragDetected(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				start_index = root.getChildren().indexOf(event.getSource());
				indexOfDropTarget = root.getChildren().indexOf(cell);	
				addPreview(root,cell);
				cell.setOpacity(0.0);
				cell.toFront();
				cell.startFullDrag();
				note_buffer = true;
			}
		});

		cell.setOnMouseDragEntered(new EventHandler<MouseDragEvent>() {
			@Override
			public void handle(MouseDragEvent event) {
				((NoteCell) cell).hide();
			}
		});

		cell.setOnMouseDragExited(new EventHandler<MouseDragEvent>() {
			@Override
			public void handle(MouseDragEvent event) {
				((NoteCell) cell).show();
			}
		});

		cell.setOnMouseDragReleased(new EventHandler<MouseDragEvent>() {
			@Override
			public void handle(MouseDragEvent event) {
				int indexOfDraggingNode = root.getChildren().indexOf(event.getGestureSource());
				int indexOfDropTarget = root.getChildren().indexOf(cell);
				rotateNodes(root, indexOfDraggingNode, indexOfDropTarget);
				removePreview(root);
				cell.setOpacity(1.0);
				event.consume();
				end_index = root.getChildren().indexOf(cell);
				noteList.swapNotes(start_index, end_index);
				note_buffer = false;
			}
		});

		cell.setOnMouseReleased(event -> {
			Point2D mouseLoc = new Point2D(event.getScreenX(), event.getScreenY());
			Bounds bounds = root.getBoundsInLocal();
			Bounds screenBounds = root.localToScreen(bounds);
			int x = (int) screenBounds.getMinX();
			int y = (int) screenBounds.getMinY();
			int width = (int) screenBounds.getWidth();
			int height = (int) screenBounds.getHeight();
			Rectangle2D windowBounds = new Rectangle2D(x, y, width, height);  
			if (! windowBounds.contains(mouseLoc)) {
				int indexOfDraggingNode = root.getChildren().indexOf(event.getSource());
				rotateNodes(root, indexOfDraggingNode, indexOfDropTarget);
				removePreview(root);
				cell.setOpacity(1.0);
				note_buffer = false;
				event.consume();
			}
		});

		root.getChildren().add(cell);
	}

	private void rotateNodes(final VBox root, final int indexOfDraggingNode, final int indexOfDropTarget) {
		if (indexOfDraggingNode >= 0 && indexOfDropTarget >= 0) {
			final Node node = root.getChildren().remove(indexOfDraggingNode);
			node.setOpacity(1.0);
			root.getChildren().add(indexOfDropTarget, node);
		}
	}

	private void addPreview(final VBox root, final Node cell) {
		ImageView imageView = new ImageView(cell.snapshot(null, null));
		imageView.setEffect(new DropShadow());
		imageView.setOpacity(0.8);
		imageView.setManaged(false);
		imageView.setMouseTransparent(true);
		root.getChildren().add(imageView);
		root.setUserData(imageView);
		root.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.getY() >= 0 && event.getY() < root.getHeight() - ((AnchorPane) cell).getHeight())
					imageView.relocate(0, event.getY());		
			}
		});
	}

	private void removePreview(final VBox root) {
		root.getChildren().remove(root.getUserData());
		root.setOnMouseDragged(null);
		root.setUserData(null);
	}

	// imports note to my notes
	public void importNote(ActionEvent event) throws IOException {
		FileChooser chooser = new FileChooser();
		chooser.setTitle("Choose File");
		File selectedFile = chooser.showOpenDialog(root.getScene().getWindow());

		if (selectedFile != null) {
			notePath = selectedFile.getAbsolutePath();
			noteList.add(new LectureNote(selectedFile.getName(), notePath));
			showNotes();
		}

		try {
			new SerializationUtil();
			SerializationUtil.serialize(noteList, noteDir);
		} catch (IOException e) {
			System.out.println("Serialization Failed");
			e.printStackTrace();
		}
	}

	// saves written note into a doc file
	public void saveNote(ActionEvent event) {
		ObservableList<CharSequence> paragraph = note.getParagraphs();

		try {
			if (noteTitle.getText().isEmpty()) {
				noteTitle.setText(note.getText().substring(0,37) + "...");
			}

			for (LectureNote note : noteList.getList()) {
				if (noteTitle.getText().equals(note.getName()))
					noteTitle.setText(noteTitle.getText() + " copy");
			}

			if (settings.getSaveNotesAsText()) {
				try(  PrintWriter out = new PrintWriter(strDirectory + "/" + noteTitle.getText() + ".txt")  ){
					out.println( paragraph );
					notePath = strDirectory + "/" + noteTitle.getText() + ".txt";
					noteList.add(new LectureNote(noteTitle.getText(), notePath));
					note.setUnFocusColor(Color.web("4d4d4d"));
					showNotes();

					try {
						new SerializationUtil();
						SerializationUtil.serialize(noteList, noteDir);
					} catch (IOException e) {
						System.out.println("Serialization Failed");
						e.printStackTrace();
					}
				}
			}

			else {
				DocFile newNote = new DocFile(noteTitle.getText(), paragraph);
				if (newNote.isSuccessful()) {
					notePath = strDirectory + "/" + noteTitle.getText() + ".docx";
					noteList.add(new LectureNote(noteTitle.getText(), notePath));
					note.setUnFocusColor(Color.web("4d4d4d"));
					showNotes();

					try {
						new SerializationUtil();
						SerializationUtil.serialize(noteList, noteDir);
					} catch (IOException e) {
						System.out.println("Serialization Failed");
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			note.setUnFocusColor(Color.web("ff3b30"));
		}
	}

	// drag function
	public void handleDragOver(DragEvent event) {
		if (event.getDragboard().hasFiles()) {
			event.acceptTransferModes(TransferMode.ANY);
		}
	}

	// drop function
	public void handleDrop(DragEvent event) throws IOException {
		List<File> files = event.getDragboard().getFiles();
		for (File f : files) {
			notePath = f.getAbsolutePath();
			noteList.add(new LectureNote(f.getName(), notePath));
			showNotes();

			try {
				new SerializationUtil();
				SerializationUtil.serialize(noteList, noteDir);
			} catch (IOException e) {
				System.out.println("Serialization Failed");
				e.printStackTrace();
			}
		}
	}

	// show notes list
	public void showNotes() throws IOException {
		noteList.checkFiles();
		noteList.cleanDuplicates();
		notes.getChildren().clear();

		if (noteList.getList().size() > 0)
			move.setVisible(false);
		else
			move.setVisible(true);

		for (LectureNote n : noteList.getList()) {
			NoteCell newNote = new NoteCell();
			newNote.setName(n.getName());
			addNoteWithDragging(notes, newNote);
		}
	}

	// open selected note
	public void openNote() throws IOException {

		ArrayList<String> fileList = new ArrayList<String>();

		String fileName = "";

		for (Node n : notes.getChildren()) {
			fileList.add(((NoteCell) n).getFilename());
		}

		for (String str : fileList) {
			if (!str.isEmpty())
				fileName = str;
		}

		File file = new File(noteList.getPathFromName(fileName));

		if (!Desktop.isDesktopSupported()) 
			return;
		Desktop desktop = Desktop.getDesktop();
		if (file.exists()) 
			desktop.open(file);	
	}

	// toggles edit mode
	public void editNotes(ActionEvent e) {

		for (Node cell : notes.getChildren()) {
			((NoteCell) cell).edit(editable);
		}

		if (notes.getChildren().isEmpty()) {
			move.setVisible(true);
			editNote.setText("Edit");
			editable = false;
		}

		if (editable) {
			editNote.setText("✓");
			editable = false;
		}

		else {
			editNote.setText("Edit");
			editable = true;
		}
	}

	// delete selected note

	Timeline noteCheck;

	public void noteChecker() {
		noteCheck = new Timeline(new KeyFrame(Duration.seconds(0.1), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (!note_buffer) {
					ArrayList<NoteCell> toBeRemoved = new ArrayList<NoteCell>();

					if (!editable) {
						for (Node n : notes.getChildren()) {
							if (((NoteCell) n).willRemoved()) {
								toBeRemoved.add((NoteCell) n);
								noteList.remove(notes.getChildren().indexOf(n));
								ser_buffer = true;
							}
						}

						notes.getChildren().removeAll(toBeRemoved);

						if (ser_buffer) {
							try {
								new SerializationUtil();
								SerializationUtil.serialize(noteList, noteDir);
							} catch (IOException e1) {
								System.out.println("Serialization Failed");
								e1.printStackTrace();
							}
							ser_buffer = false;
						}
					}

					if (editable) {
						try {
							openNote();
						} catch (IOException e) {
							//e.printStackTrace();
						}
					}
				}
			}}));

		noteCheck.setCycleCount(Timeline.INDEFINITE);
		noteCheck.play();
	}

	@Override
	@SuppressWarnings("unchecked")
	public void initialize(URL location, ResourceBundle resources) {

		try {
			new SerializationUtil();
			String os = System.getProperty("os.name").toUpperCase();

			if (os.contains("MAC")) {
				users = (Users) SerializationUtil.deserialize(usersDirMac);
				userProfile = users.getActiveUser();
				
				courseDir = System.getProperty("user.home") + "/Library/Application Support/Bias/" 
						+ userProfile.getDirectory()  +"/courses.ser";

				assDir = System.getProperty("user.home") + "/Library/Application Support/Bias/"
						+ userProfile.getDirectory()  +"/assignments.ser";

				noteDir = System.getProperty("user.home") + "/Library/Application Support/Bias/"
						+ userProfile.getDirectory()  + "/notes.ser";

				settingsDir = System.getProperty("user.home") + "/Library/Application Support/Bias/" 
						+ userProfile.getDirectory()  +"/settings.ser";

				eventsDir = System.getProperty("user.home") + "/Library/Application Support/Bias/"
						+ userProfile.getDirectory() + "/events.ser";

				qpDir = System.getProperty("user.home") + "/Library/Application Support/Bias/"
						+ userProfile.getDirectory() + "/qp.ser";

				eventInfoDir = System.getProperty("user.home") + "/Library/Application Support/Bias/"
						+ userProfile.getDirectory() + "/eventInfo.ser";

			}
			else if (os.contains("WIN")) {
				users = (Users) SerializationUtil.deserialize(usersDirWin);
				userProfile = users.getActiveUser();
				
				courseDir = System.getenv("APPDATA") + "\\Bias\\" + userProfile.getDirectory() + "\\courses.ser";

				assDir = System.getenv("APPDATA") + "\\Bias\\" + userProfile.getDirectory() + "\\assignments.ser";

				noteDir = System.getenv("APPDATA") + "\\Bias\\" + userProfile.getDirectory() + "\\notes.ser";

				settingsDir =System.getenv("APPDATA") + "\\Bias\\" + userProfile.getDirectory() + "\\settings.ser";

				eventsDir = System.getenv("APPDATA") + "\\Bias\\" + userProfile.getDirectory() + "\\events.ser";

				qpDir = System.getenv("APPDATA") + "\\Bias\\" + userProfile.getDirectory() + "\\qp.ser";

				eventInfoDir = System.getenv("APPDATA") + "\\Bias\\" + userProfile.getDirectory() + "\\eventInfo.ser";
			}

			if (!userProfile.getProfilePic().isEmpty()) {
				profileButton.setFill(new ImagePattern(new Image(userProfile.getProfilePic())));
			}
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

		// today & activities

		Date date = new Date();
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(date);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int month = calendar.get(Calendar.MONTH);
		int weekOfMonth = calendar.get(Calendar.WEEK_OF_MONTH);

		try {
			new SerializationUtil();
			ce =  (CustomEvent) SerializationUtil.deserialize(eventsDir);			
		} catch (ClassNotFoundException | IOException ex) {
		}
		
		if (ce.getWeekOfMonth() != weekOfMonth)
		{
			ce = new CustomEvent();
			
			try {
				new SerializationUtil();
				SerializationUtil.serialize(ce, eventsDir);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		try {
			new SerializationUtil();
			qp =  (QuizzesAndPresentations) SerializationUtil.deserialize(qpDir);			
		} catch (ClassNotFoundException | IOException ex) {
		}

		if (day != qp.loadDay() || month != qp.loadMonth()) {
			qp = new QuizzesAndPresentations();
		}

		try {
			new SerializationUtil();
			lectureEventInfo =  (LectureEventInfo) SerializationUtil.deserialize(eventInfoDir);			
		} catch (ClassNotFoundException | IOException ex) {
		}

		try {
			new SerializationUtil();
			ec =  (EnrolledCourses) SerializationUtil.deserialize(courseDir);
		} catch (ClassNotFoundException | IOException e) {
			//e.printStackTrace();
		}

		setToday();		
		arrowUpdate();		

		try {
			setLectureInfos();
		} catch (IOException e2) {
			e2.printStackTrace();
		}

		saveLectureInfos();

		try {
			constructActivities();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		updateActivities();
		
		if (settings.getAllowNotifications()) {
			notifyActivities();
			notifyLectures();
		}

		// assignments

		try {
			new SerializationUtil();
			assignments =  (Assignments) SerializationUtil.deserialize(assDir);
			slotNo += assignments.getList().size();

			for (int i = 0; i < assignments.getList().size(); i++) {
				Assignment ass = new Assignment();
				ass.load((ArrayList<Object>) assignments.getList().get(i).save());
				addAssWithDragging(assBox, ass);
			}

			assignments = new Assignments();

			ArrayList<Assignment> toBeRemoved = new ArrayList<Assignment>();

			for (Node n : assBox.getChildren()) {
				if (settings.getRemovePastAssignments() && ((Assignment) n).isExpired())
					toBeRemoved.add((Assignment) n);
				else {
					StudentAssignment ass = new StudentAssignment();
					ass.load(((Assignment) n).save());
					assignments.add(ass);
				}
			}

			assBox.getChildren().removeAll(toBeRemoved);
			SerializationUtil.serialize(assignments, assDir);
			assChecker();

		} catch (ClassNotFoundException | IOException e) {
			//e.printStackTrace();
		}

		// notes

		try {
			new SerializationUtil();
			noteList = (NoteList) SerializationUtil.deserialize(noteDir);
			saveNote.setDisable(!settings.getEnableNotes());
			showNotes();		
		} catch (ClassNotFoundException | IOException e) {
			//e.printStackTrace();
		}

		noteChecker();

		// in case user drops note in blank space in root:
		notes.setOnMouseDragReleased(new EventHandler<MouseDragEvent>() {
			@Override
			public void handle(MouseDragEvent event) {
				int indexOfDraggingNode = notes.getChildren().size() - 1 ;
				rotateNodes(notes, indexOfDraggingNode, indexOfDropTarget);
				removePreview(notes);

				for (Node n : notes.getChildren()) {
					n.setOpacity(1.0);
				}

				note_buffer = false;
				event.consume();
			}
		});

		// in case user drops assignment in blank space in root:
		assBox.setOnMouseDragReleased(new EventHandler<MouseDragEvent>() {
			@Override
			public void handle(MouseDragEvent event) {
				int indexOfDraggingNode = assBox.getChildren().size() - 1 ;
				rotateNodes(assBox, indexOfDraggingNode, indexOfDropTarget);
				removePreview(assBox);

				for (Node n : assBox.getChildren()) {
					n.setOpacity(1.0);
				}

				ass_buffer = false;
				event.consume();
			}
		});

		noteScroller.setOnScrollStarted(new EventHandler<ScrollEvent>() {
			@Override
			public void handle(ScrollEvent event) {
				noteScroller.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
				event.consume();
			}
		});

		noteScroller.setOnScrollFinished(new EventHandler<ScrollEvent>() {
			@Override
			public void handle(ScrollEvent event) {
				Timeline timeline = new Timeline(new KeyFrame(
						Duration.millis(1500),
						ae -> noteScroller.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER)));
				timeline.play();
				event.consume();
			}
		});

		activityScroller.setOnScrollStarted(new EventHandler<ScrollEvent>() {
			@Override
			public void handle(ScrollEvent event) {
				activityScroller.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
				event.consume();
			}
		});

		activityScroller.setOnScrollFinished(new EventHandler<ScrollEvent>() {
			@Override
			public void handle(ScrollEvent event) {
				Timeline timeline = new Timeline(new KeyFrame(
						Duration.millis(1500),
						ae -> activityScroller.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER)));
				timeline.play();
				event.consume();
			}
		});
	}
}