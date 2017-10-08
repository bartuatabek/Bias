package view;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import org.controlsfx.control.PopOver;

import application.CalendarEvent;
import application.EventInfo;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import model.CourseCreator;
import model.CustomEvent;
import model.EnrolledCourses;
import model.LectureEventInfo;
import model.Profile;
import model.QuizzesAndPresentations;
import model.SerializationUtil;
import model.Settings;
import model.Users;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

public class CalendarController implements Initializable {

	private static final String usersDirMac = System.getProperty("user.home") 
			+ "/Library/Application Support/Bias/users.ser";

	private static final String usersDirWin = System.getenv("APPDATA") + "\\Bias\\users.ser";
	private Users users = new Users();

	private Profile userProfile = new Profile();

	private EnrolledCourses ec = new EnrolledCourses();

	private CustomEvent ce = new CustomEvent();

	private Settings settings = new Settings();

	private LectureEventInfo lectureEventInfo = new LectureEventInfo();

	private String settingsDir;

	private String courseDir;

	private String eventsDir;

	private String eventInfoDir;

	private String qpDir;

	private ArrayList<CalendarEvent> customEvents = new ArrayList<>();

	@FXML
	private ImageView homeButtonCW;

	@FXML
	private ImageView calendarButtonCW;

	@FXML
	private ImageView lectureButtonCW;

	@FXML
	private Circle profileButtonCW;

	@FXML
	private AnchorPane root;

	@FXML
	private ScrollPane calendarScroller;

	
	@FXML
	private void handleMenuAction(MouseEvent e) throws IOException {

		AnchorPane pane;

		if (e.getSource() == profileButtonCW) {
			pane = FXMLLoader.load(getClass().getResource("/view/Profile.fxml"));
		}
		else if (e.getSource() == homeButtonCW) {
			pane = FXMLLoader.load(getClass().getResource("/view/Dashboard.fxml"));
		}
		else {
			pane = FXMLLoader.load(getClass().getResource("/view/Courses.fxml"));
		}

		root.getChildren().setAll(pane);

		notifyCheck.stop();
		notifyLectures.stop();
		updatePanes.stop();
		updateLectureEventInfos.stop();
		updateTime.stop();
	}

	@FXML
	private Circle sunC;

	@FXML
	private Circle monC;

	@FXML
	private Circle tueC;

	@FXML
	private Circle wedC;

	@FXML
	private Circle thuC;

	@FXML
	private Circle friC;

	@FXML
	private Circle satC;

	@FXML
	private Label sun;

	@FXML
	private Label mon;

	@FXML
	private Label tue;

	@FXML
	private Label wed;

	@FXML
	private Label thu;

	@FXML
	private Label fri;

	@FXML
	private Label sat;

	@FXML
	private Circle sunCL;

	@FXML
	private Circle monCL;

	@FXML
	private Circle tueCL;

	@FXML
	private Circle wedCL;

	@FXML
	private Circle thuCL;

	@FXML
	private Circle friCL;

	@FXML
	private Circle satCL;

	@FXML
	private Label clock;

	@FXML
	private Group now;

	@FXML
	private Group now2;

	@FXML
	private Label t1;

	@FXML
	private Label t2;

	@FXML
	private Label t3;

	@FXML
	private Label t4;

	@FXML
	private Label t5;

	@FXML
	private Label t6;

	@FXML
	private Label t7;

	@FXML
	private Label t8;

	@FXML
	private Label t9;

	@FXML
	private Label t10;

	@FXML
	private Label t11;	

	@FXML
	private Label t12;

	@FXML
	private Label t13;

	@FXML
	private Label t14;

	@FXML
	private Label t15;

	@FXML
	private Label t16;

	@FXML
	private Label t17;

	@FXML
	private Label t18;

	@FXML
	private Label t19;

	@FXML
	private Label t20;

	@FXML
	private Label t21;

	@FXML
	private Label t22;

	@FXML
	private Label t23;

	@FXML
	private Label t24;

	@FXML
	private Label t25;

	public void setDate() {
		Date date = new Date();  
		Calendar calendar = GregorianCalendar.getInstance(); 
		calendar.setTime(date); 
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
		int monthOfYear = calendar.get(Calendar.MONTH);
		int diff = 0;

		int[] daysOfWeek = new int[7];

		sunCL.setVisible(false);
		monCL.setVisible(false);
		tueCL.setVisible(false);
		wedCL.setVisible(false);
		thuCL.setVisible(false);
		friCL.setVisible(false);
		satCL.setVisible(false);

		if (dayOfWeek == 1) {
			sunC.setFill(Color.web("#ff3b30"));
			sunCL.setVisible(true);			
			diff = 0;
		}
		else if (dayOfWeek == 2) {
			monC.setFill(Color.web("#ff3b30"));
			monCL.setVisible(true);			
			diff = 1;
		}
		else if (dayOfWeek == 3) {
			tueC.setFill(Color.web("#ff3b30"));
			tueCL.setVisible(true);
			diff = 2;
		}
		else if (dayOfWeek == 4) {
			wedC.setFill(Color.web("#ff3b30"));
			wedCL.setVisible(true);
			diff = 3;
		}
		else if (dayOfWeek == 5) {
			thuC.setFill(Color.web("#ff3b30"));
			thuCL.setVisible(true);
			diff = 4;
		}
		else if (dayOfWeek == 6) {
			friC.setFill(Color.web("#ff3b30"));
			friCL.setVisible(true);
			diff = 5;
		}
		else if (dayOfWeek == 7) {
			satC.setFill(Color.web("#ff3b30"));
			satCL.setVisible(true);
			diff = 6;
		}

		int add = 0;
		int subtract = 0;
		int newMonthMaker = 0;
		int monthMax = 28;
		int prevMonthMax = 28;

		if (monthOfYear == 1 && calendar.get(Calendar.YEAR) % 4 == 0) {
			monthMax = 29;
		}
		else if (monthOfYear == 0 || monthOfYear == 2 || monthOfYear == 4 || monthOfYear == 6 
				|| monthOfYear == 7 || monthOfYear == 9 || monthOfYear == 11) { 
			monthMax = 31;
		}
		else if (monthOfYear != 1) {
			monthMax = 30;
		}

		// finding previous month max
		if (monthOfYear - 1 == 1 && calendar.get(Calendar.YEAR) % 4 == 0) {
			prevMonthMax = 29;
		}
		else if (monthOfYear - 1 == 0 || monthOfYear - 1 == 2 || monthOfYear - 1 == 4 || monthOfYear - 1 == 6 
				|| monthOfYear - 1 == 7 || monthOfYear - 1 == 9 || monthOfYear - 1 == 11) { 
			prevMonthMax = 31;
		}
		else if (monthOfYear - 1 == -1) {
			prevMonthMax = 31;
		}
		else if (monthOfYear != 1) {
			prevMonthMax = 30;
		}

		// first half of the week
		for (int i = diff; i < 7; i++) {
			if (dayOfMonth + add <= monthMax) {
				daysOfWeek[i] = dayOfMonth + add;
				add++;
			}
			else {
				daysOfWeek[i] = 1 + newMonthMaker;
				newMonthMaker++;
			}
		}

		// second half of the week
		for (int i = 0; daysOfWeek[i] != dayOfMonth; i++) {
			if (dayOfMonth - diff > 0) {
				daysOfWeek[i] = dayOfMonth - diff;
				diff--;
			}
			else {
				daysOfWeek[i] = prevMonthMax - subtract;
				diff--;
				subtract++;
			}
		}

		sun.setText(daysOfWeek[0] + "");
		mon.setText(daysOfWeek[1] + "");
		tue.setText(daysOfWeek[2] + "");
		wed.setText(daysOfWeek[3] + "");
		thu.setText(daysOfWeek[4] + "");
		fri.setText(daysOfWeek[5] + "");
		sat.setText(daysOfWeek[6] + "");

		// setting time and line
		String time = "";

		if (calendar.get(Calendar.HOUR) == 0)
			time += 12 + ":";
		else
			time += calendar.get(Calendar.HOUR) + ":";

		if (calendar.get(Calendar.MINUTE) < 10)
			time += "0" + calendar.get(Calendar.MINUTE) + " ";
		else
			time += calendar.get(Calendar.MINUTE) + " ";

		if (calendar.get(Calendar.AM_PM) == 0)
			time += "AM";
		else
			time += "PM";

		t1.setVisible(true);
		t2.setVisible(true);
		t3.setVisible(true);
		t4.setVisible(true);
		t5.setVisible(true);
		t6.setVisible(true);
		t7.setVisible(true);
		t8.setVisible(true);
		t9.setVisible(true);
		t10.setVisible(true);
		t11.setVisible(true);
		t12.setVisible(true);
		t13.setVisible(true);
		t14.setVisible(true);
		t15.setVisible(true);
		t16.setVisible(true);
		t17.setVisible(true);
		t18.setVisible(true);
		t19.setVisible(true);
		t20.setVisible(true);
		t21.setVisible(true);
		t22.setVisible(true);
		t23.setVisible(true);
		t24.setVisible(true);
		t25.setVisible(true);	

		String hour = "" + calendar.get(Calendar.HOUR_OF_DAY);

		if (calendar.get(Calendar.MINUTE) < 10)
			hour += "0" + calendar.get(Calendar.MINUTE);
		else
			hour += calendar.get(Calendar.MINUTE);  

		if (Integer.parseInt(hour) >= 000 && Integer.parseInt(hour) < 015) {
			t1.setVisible(false);
		}
		else if (Integer.parseInt(hour) > 045 && Integer.parseInt(hour) < 115) {
			t2.setVisible(false);
		}
		else if (Integer.parseInt(hour) > 145 && Integer.parseInt(hour) < 215) {
			t3.setVisible(false);
		}
		else if (Integer.parseInt(hour) > 245 && Integer.parseInt(hour) < 315) {
			t4.setVisible(false);
		}
		else if (Integer.parseInt(hour) > 345 && Integer.parseInt(hour) < 415) {
			t5.setVisible(false);
		}
		else if (Integer.parseInt(hour) > 445 && Integer.parseInt(hour) < 515) {
			t6.setVisible(false);
		}
		else if (Integer.parseInt(hour) > 545 && Integer.parseInt(hour) < 615) {
			t7.setVisible(false);
		}
		else if (Integer.parseInt(hour) > 645 && Integer.parseInt(hour) < 715) {
			t8.setVisible(false);
		}
		else if (Integer.parseInt(hour) > 745 && Integer.parseInt(hour) < 815) {
			t9.setVisible(false);
		}
		else if (Integer.parseInt(hour) > 845 && Integer.parseInt(hour) < 915) {
			t10.setVisible(false);
		}
		else if (Integer.parseInt(hour) > 945 && Integer.parseInt(hour) < 1015) {
			t11.setVisible(false);
		}
		else if (Integer.parseInt(hour) > 1045 && Integer.parseInt(hour) < 1115) {
			t12.setVisible(false);
		}
		else if (Integer.parseInt(hour) > 1145 && Integer.parseInt(hour) < 1215) {
			t13.setVisible(false);
		}
		else if (Integer.parseInt(hour) > 1245 && Integer.parseInt(hour) < 1315) {
			t14.setVisible(false);
		}
		else if (Integer.parseInt(hour) > 1345 && Integer.parseInt(hour) < 1415) {
			t15.setVisible(false);
		}
		else if (Integer.parseInt(hour) > 1445 && Integer.parseInt(hour) < 1515) {
			t16.setVisible(false);
		}
		else if (Integer.parseInt(hour) > 1545 && Integer.parseInt(hour) < 1615) {
			t17.setVisible(false);
		}
		else if (Integer.parseInt(hour) > 1645 && Integer.parseInt(hour) < 1715) {
			t18.setVisible(false);
		}
		else if (Integer.parseInt(hour) > 1745 && Integer.parseInt(hour) < 1815) {
			t19.setVisible(false);
		}
		else if (Integer.parseInt(hour) > 1845 && Integer.parseInt(hour) < 1915) {
			t20.setVisible(false);
		}
		else if (Integer.parseInt(hour) > 1945 && Integer.parseInt(hour) < 2015) {
			t21.setVisible(false);
		}
		else if (Integer.parseInt(hour) > 2045 && Integer.parseInt(hour) < 2115) {
			t22.setVisible(false);
		}
		else if (Integer.parseInt(hour) > 2145 && Integer.parseInt(hour) < 2215) {
			t23.setVisible(false);
		}
		else if (Integer.parseInt(hour) > 2245 && Integer.parseInt(hour) < 2315) {
			t24.setVisible(false);
		}
		else if (Integer.parseInt(hour) > 2345 && Integer.parseInt(hour) <= 2359) {
			t25.setVisible(false);
		}

		clock.setText(time);

		double coord = (((calendar.get(Calendar.HOUR_OF_DAY) * 60 + calendar.get(Calendar.MINUTE)) * 52) / 60) + 10;
		//System.out.println(coord);
		now.setLayoutY(coord);
		now2.setLayoutY(coord);
	}

	@FXML
	private Pane sunday;

	@FXML
	private Pane monday;

	@FXML
	private Pane tuesday;

	@FXML
	private Pane wednesday;

	@FXML
	private Pane thursday;

	@FXML
	private Pane friday;

	@FXML
	private Pane saturday;

	public void addLectures() throws IOException {	
		ArrayList<Integer> enrolledCourses = ec.load();
		CourseCreator cc = new CourseCreator();

		for (int i = 0; i < enrolledCourses.size(); i++) {
			int index = enrolledCourses.get(i);

			for (int j = 0; j < cc.getAllLectureDates().get(index).size(); j++) {
				String current = cc.getAllLectureDates().get(index).get(j);
				int start1 = Integer.valueOf(current.substring(current.indexOf(' ') + 1, current.indexOf(':')));
				int start = Integer.valueOf(current.substring(current.indexOf(' ') + 1, current.indexOf(':')));
				int end = Integer.valueOf(current.substring(current.indexOf('-') + 1, current.indexOf('-') + 3));

				for (int k = 0; k < end - start1; k++) {
					CalendarEvent e = new CalendarEvent();
					start = Integer.valueOf(current.substring(current.indexOf(' ') + 1, current.indexOf(':')));
					start = start + k;
					String startAsString = "";

					if (start < 12)
						startAsString = start + ":40 " +"AM";
					else 
						startAsString = (start - 12) + ":40 " + "PM";

					if (i == 0)
						e.setColor(Color.web("#4CD964"));
					else if (i == 1)
						e.setColor(Color.web("#5AC8FA"));
					else if (i == 2)
						e.setColor(Color.web("#FF9500"));
					else if (i == 3)
						e.setColor(Color.web("#5856D6"));
					else if (i == 4)
						e.setColor(Color.web("#FFCC00"));
					else if (i == 5)
						e.setColor(Color.web("#FF2D55"));

					String info = cc.getAllFaculties().get(index) + "-" + cc.getAllCodes().get(index);

					if (current.contains("["))
						info = info + " " + current.substring(current.indexOf("["));

					e.generateEventAutomatically(Double.valueOf(start), Double.valueOf(start) + 1, startAsString, info);

					if (current.startsWith("Mon")) {
						monday.getChildren().add(e);
					}
					else if (current.startsWith("Tue")) {
						tuesday.getChildren().add(e);
					}
					if (current.startsWith("Wed")) {
						wednesday.getChildren().add(e);
					}
					else if (current.startsWith("Thu")) {
						thursday.getChildren().add(e);
					}
					else if (current.startsWith("Fri")) {
						friday.getChildren().add(e);
					}
				}
			}
		}
	}

	public void addEvents() throws IOException {
		for (int i = 0; i < ce.getStartHours().size(); i++) {
			CalendarEvent e = new CalendarEvent();
			e.generateCustomEvents(ce.getStartHours().get(i), ce.getStartMins().get(i), ce.getHeights().get(i), ce.getInfo().get(i));
			customEvents.add(e);

			if (ce.getDays().get(i).startsWith("Sun")) {
				sunday.getChildren().add(e);
			}
			else if (ce.getDays().get(i).startsWith("Mon")) {
				monday.getChildren().add(e);
			}
			else if (ce.getDays().get(i).startsWith("Tue")) {
				tuesday.getChildren().add(e);
			}
			if (ce.getDays().get(i).startsWith("Wed")) {
				wednesday.getChildren().add(e);
			}
			else if (ce.getDays().get(i).startsWith("Thu")) {
				thursday.getChildren().add(e);
			}
			else if (ce.getDays().get(i).startsWith("Fri")) {
				friday.getChildren().add(e);
			}
			else if (ce.getDays().get(i).startsWith("Sat")) {
				saturday.getChildren().add(e);
			}
		}

		for (int i = 0; i < customEvents.size(); i++) {
			customEvents.get(i).setInfo(ce.getInfo().get(i));
			customEvents.get(i).setLocation( ce.getLocations().get(i));
		}

	}

	public void removeAllFocus()
	{
		for (int j = 0; j < sunday.getChildren().size(); j++) {
			CalendarEvent ev = (CalendarEvent) sunday.getChildren().get(j);
			ev.removeFocus();
		}

		for (int j = 0; j < monday.getChildren().size(); j++) {
			CalendarEvent ev = (CalendarEvent) monday.getChildren().get(j);
			ev.removeFocus();
		}

		for (int j = 0; j < tuesday.getChildren().size(); j++) {
			CalendarEvent ev = (CalendarEvent) tuesday.getChildren().get(j);
			ev.removeFocus();
		}

		for (int j = 0; j < wednesday.getChildren().size(); j++) {
			CalendarEvent ev = (CalendarEvent) wednesday.getChildren().get(j);
			ev.removeFocus();
		}

		for (int j = 0; j < thursday.getChildren().size(); j++) {
			CalendarEvent ev = (CalendarEvent) thursday.getChildren().get(j);
			ev.removeFocus();
		}

		for (int j = 0; j < friday.getChildren().size(); j++) {
			CalendarEvent ev = (CalendarEvent) friday.getChildren().get(j);
			ev.removeFocus();
		}

		for (int j = 0; j < saturday.getChildren().size(); j++) {
			CalendarEvent ev = (CalendarEvent) saturday.getChildren().get(j);
			ev.removeFocus();
		}
	}

	// peek event info method allows to peek into event details 
	PopOver eventInfo = new PopOver(); 
	ArrayList<EventInfo> allInfos = new ArrayList<>();
	ArrayList<EventInfo> allLectureInfos = new ArrayList<>();

	public void peekCustomEventInfo (MouseEvent e) {
		eventInfo.hide();
		eventInfo2.hide();
		eventInfo = new PopOver();
		eventInfo.setAutoHide(true);
		eventInfo.setAnimated(true);
		eventInfo.setDetachable(false);
		eventInfo.setArrowLocation(PopOver.ArrowLocation.LEFT_TOP);
		eventInfo.setCornerRadius(10.0); 

		Pane source = (Pane) e.getSource();
		CalendarEvent temp = null;

		boolean found = false;

		if (source == friday || source == saturday )
		{
			eventInfo.setArrowLocation(PopOver.ArrowLocation.RIGHT_TOP);
		}

		for (int i = 0; i < source.getChildren().size(); i++) {
			temp = (CalendarEvent) source.getChildren().get(i);
			if (e.getY() > temp.getLayoutY() && temp.getMyHeight() + temp.getLayoutY() > e.getY()) {
				found = true;

				for (int j = 0; j < ce.getDays().size(); j++) {
					if (temp.getStartHour() == ce.getStartHours().get(j) && temp.getStartMin() == ce.getStartMins().get(j) && !temp.getFixed()) {
						EventInfo ei = allInfos.get(j);
						eventInfo.setContentNode(ei);
						
						if (temp.getFocus())
							eventInfo.show(temp);
						else
							eventInfo.hide();
					}
				}

				for (int j = 0; j < sunday.getChildren().size(); j++) {
					CalendarEvent ev = (CalendarEvent) sunday.getChildren().get(j);

					if (ev != temp)
						ev.removeFocus();
				}

				for (int j = 0; j < monday.getChildren().size(); j++) {
					CalendarEvent ev = (CalendarEvent) monday.getChildren().get(j);

					if (ev != temp)
						ev.removeFocus();
				}

				for (int j = 0; j < tuesday.getChildren().size(); j++) {
					CalendarEvent ev = (CalendarEvent) tuesday.getChildren().get(j);

					if (ev != temp)
						ev.removeFocus();
				}

				for (int j = 0; j < wednesday.getChildren().size(); j++) {
					CalendarEvent ev = (CalendarEvent) wednesday.getChildren().get(j);

					if (ev != temp)
						ev.removeFocus();
				}

				for (int j = 0; j < thursday.getChildren().size(); j++) {
					CalendarEvent ev = (CalendarEvent) thursday.getChildren().get(j);

					if (ev != temp)
						ev.removeFocus();
				}

				for (int j = 0; j < friday.getChildren().size(); j++) {
					CalendarEvent ev = (CalendarEvent) friday.getChildren().get(j);

					if (ev != temp)
						ev.removeFocus();
				}

				for (int j = 0; j < saturday.getChildren().size(); j++) {
					CalendarEvent ev = (CalendarEvent) saturday.getChildren().get(j);

					if (ev != temp)
						ev.removeFocus();
				}
			}
		}

		if (!found)
		{
			removeAllFocus();
		}
		peekLectureEventInfo(e);
	}

	PopOver eventInfo2 = new PopOver();

	public void peekLectureEventInfo (MouseEvent e) {
		eventInfo2.hide();
		eventInfo2 = new PopOver();
		eventInfo2.setAutoHide(true);
		eventInfo2.setAnimated(true);
		eventInfo2.setDetachable(false);
		eventInfo2.setArrowLocation(PopOver.ArrowLocation.LEFT_TOP);
		eventInfo2.setCornerRadius(10.0); 

		Pane source = (Pane) e.getSource();
		CalendarEvent temp = null;

		if (source == friday || source == saturday )
		{
			eventInfo2.setArrowLocation(PopOver.ArrowLocation.RIGHT_TOP);
		}

		for (int i = 0; i < source.getChildren().size(); i++) {
			temp = (CalendarEvent) source.getChildren().get(i);

			if (e.getY() > temp.getLayoutY() && temp.getMyHeight() + temp.getLayoutY() > e.getY() && source == monday) {
				for (int j = 0; j < lectureEventInfo.loadDays().size(); j++)
				{
					int startOfTemp = Integer.valueOf(temp.getTime().substring(0, temp.getTime().indexOf(":")));
					if (startOfTemp == (lectureEventInfo.loadHours().get(j) % 12) && temp.getFixed() && lectureEventInfo.loadDays().get(j).equalsIgnoreCase("Mon")) {
						EventInfo ei = allLectureInfos.get(j);
						ei.setEventName(temp.getInfo());
						ei.setEditableFalse();
						ei.setMailAddress(userProfile.getMail());
						eventInfo2.setContentNode(ei);
						
						if (temp.getFocus())
							eventInfo2.show(temp);
						else
							eventInfo2.hide();
//						eventInfo2.show(temp);
					}
				}

				for (int j = 0; j < sunday.getChildren().size(); j++) {
					CalendarEvent ev = (CalendarEvent) sunday.getChildren().get(j);

					if (ev != temp)
						ev.removeFocus();
				}

				for (int j = 0; j < monday.getChildren().size(); j++) {
					CalendarEvent ev = (CalendarEvent) monday.getChildren().get(j);

					if (ev != temp)
						ev.removeFocus();
				}

				for (int j = 0; j < tuesday.getChildren().size(); j++) {
					CalendarEvent ev = (CalendarEvent) tuesday.getChildren().get(j);

					if (ev != temp)
						ev.removeFocus();
				}

				for (int j = 0; j < wednesday.getChildren().size(); j++) {
					CalendarEvent ev = (CalendarEvent) wednesday.getChildren().get(j);

					if (ev != temp)
						ev.removeFocus();
				}

				for (int j = 0; j < thursday.getChildren().size(); j++) {
					CalendarEvent ev = (CalendarEvent) thursday.getChildren().get(j);

					if (ev != temp)
						ev.removeFocus();
				}

				for (int j = 0; j < friday.getChildren().size(); j++) {
					CalendarEvent ev = (CalendarEvent) friday.getChildren().get(j);

					if (ev != temp)
						ev.removeFocus();
				}

				for (int j = 0; j < saturday.getChildren().size(); j++) {
					CalendarEvent ev = (CalendarEvent) saturday.getChildren().get(j);

					if (ev != temp)
						ev.removeFocus();
				}
			}

			else if (e.getY() > temp.getLayoutY() && temp.getMyHeight() + temp.getLayoutY() > e.getY() && source == tuesday) {
				for (int j = 0; j < lectureEventInfo.loadDays().size(); j++) {
					int startOfTemp = Integer.valueOf(temp.getTime().substring(0, temp.getTime().indexOf(":")));
					if (startOfTemp == (lectureEventInfo.loadHours().get(j) % 12) && temp.getFixed() && lectureEventInfo.loadDays().get(j).equalsIgnoreCase("Tue")) {
						EventInfo ei = allLectureInfos.get(j);
						ei.setEventName(temp.getInfo());
						ei.setEditableFalse();
						ei.setMailAddress(userProfile.getMail());
						eventInfo2.setContentNode(ei);
						
						if (temp.getFocus())
							eventInfo2.show(temp);
						else
							eventInfo2.hide();
//						eventInfo2.show(temp);
					}
				}
			}

			else if (e.getY() > temp.getLayoutY() && temp.getMyHeight() + temp.getLayoutY() > e.getY() && source == wednesday) {		
				for (int j = 0; j < lectureEventInfo.loadDays().size(); j++) {
					int startOfTemp = Integer.valueOf(temp.getTime().substring(0, temp.getTime().indexOf(":")));
					if (startOfTemp == (lectureEventInfo.loadHours().get(j) % 12) && temp.getFixed() && lectureEventInfo.loadDays().get(j).equalsIgnoreCase("Wed")) {
						EventInfo ei = allLectureInfos.get(j);
						ei.setEventName(temp.getInfo());
						ei.setEditableFalse();
						ei.setMailAddress(userProfile.getMail());
						eventInfo2.setContentNode(ei);
						
						if (temp.getFocus())
							eventInfo2.show(temp);
						else
							eventInfo2.hide();
//						eventInfo2.show(temp);
					}
				}
			}

			else if (e.getY() > temp.getLayoutY() && temp.getMyHeight() + temp.getLayoutY() > e.getY() && source == thursday) {
				for (int j = 0; j < lectureEventInfo.loadDays().size(); j++) {
					int startOfTemp = Integer.valueOf(temp.getTime().substring(0, temp.getTime().indexOf(":")));
					if (startOfTemp == (lectureEventInfo.loadHours().get(j) % 12) && temp.getFixed() && lectureEventInfo.loadDays().get(j).equalsIgnoreCase("Thu")) {
						EventInfo ei = allLectureInfos.get(j);
						ei.setEventName(temp.getInfo());
						ei.setEditableFalse();
						ei.setMailAddress(userProfile.getMail());
						eventInfo2.setContentNode(ei);
						
						if (temp.getFocus())
							eventInfo2.show(temp);
						else
							eventInfo2.hide();
//						eventInfo2.show(temp);
					}
				}
			}

			else if (e.getY() > temp.getLayoutY() && temp.getMyHeight() + temp.getLayoutY() > e.getY() && source == friday) {
				for (int j = 0; j < lectureEventInfo.loadDays().size(); j++) {
					int startOfTemp = Integer.valueOf(temp.getTime().substring(0, temp.getTime().indexOf(":")));
					if (startOfTemp == (lectureEventInfo.loadHours().get(j) % 12) && temp.getFixed() && lectureEventInfo.loadDays().get(j).equalsIgnoreCase("Fri")) {
						EventInfo ei = allLectureInfos.get(j);
						ei.setEventName(temp.getInfo());
						ei.setEditableFalse();
						ei.setMailAddress(userProfile.getMail());
						eventInfo2.setContentNode(ei);
						
						if (temp.getFocus())
							eventInfo2.show(temp);
						else
							eventInfo2.hide();
//						eventInfo2.show(temp);
					}
				}
			}
		}
	}

	Timeline updatePanes;

	public void saveCustomEventInfo () {
		updatePanes = new Timeline(new KeyFrame(Duration.seconds(0.01), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				for (int i = 0; i < ce.getDays().size(); i++)  {
					ce.getInfo().set(i, allInfos.get(i).getEventName());
					ce.getLocations().set(i, allInfos.get(i).getLocation());
					ce.getNotes().set(i, allInfos.get(i).getNotes());
					ce.getSilenced().set(i, allInfos.get(i).getSilent());
				}

				for (int i = 0; i < customEvents.size(); i++) {
					customEvents.get(i).setInfo(ce.getInfo().get(i));
					customEvents.get(i).setLocation(ce.getLocations().get(i));
				}
			}
		}));
		updatePanes.setCycleCount(Timeline.INDEFINITE);
		updatePanes.play();	
	}

	Timeline updateLectureEventInfos;

	public void saveLectureEventInfo () {
		updateLectureEventInfos = new Timeline(new KeyFrame(Duration.seconds(0.01), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				for (int i = 0; i < lectureEventInfo.loadDays().size(); i++) {
					lectureEventInfo.loadNotes().set(i, allLectureInfos.get(i).getNotes());
					lectureEventInfo.loadSilents().set(i, allLectureInfos.get(i).getSilent());
				}
			}
		}));
		updateLectureEventInfos.setCycleCount(Timeline.INDEFINITE);
		updateLectureEventInfos.play();	
	}

	public void serializeLectureEventInfo (MouseEvent e) {
		try {
			new SerializationUtil();
			SerializationUtil.serialize(lectureEventInfo, eventInfoDir);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public void addEventInfos() throws IOException {

		EventInfo ei;
		String name = ce.getInfo().get(ce.getInfo().size() - 1);
		String loc = ce.getLocations().get(ce.getLocations().size() - 1);
		String day = ce.getDays().get(ce.getDays().size() - 1);

		String eventTime = ce.getStartHours().get(ce.getStartHours().size() - 1).intValue() + ":" + ce.getStartMins().get(ce.getStartMins().size() - 1).intValue();
		int eventStart = ce.getStartHours().get(ce.getStartHours().size() - 1).intValue() * 60 + ce.getStartMins().get(ce.getStartMins().size() - 1).intValue();
		int eventDuration = (int)(ce.getHeights().get(ce.getHeights().size() - 1) * 60.0 / (1250.0 / 24.0));

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

		ei = new EventInfo (name, loc, day, eventTime, eventEndTime);
		ei.setNotes(ce.getNotes().get(ce.getNotes().size() - 1));
		ei.setSilentAndBell(ce.getSilenced().get(ce.getSilenced().size() - 1));

		allInfos.add(ei);
		ei = null;
	}


	public void setEventInfos() throws IOException {
		allInfos = new ArrayList<>();

		for (int i = 0; i < ce.getDays().size(); i++) {
			EventInfo ei;

			String name = ce.getInfo().get(i);
			String loc = ce.getLocations().get(i);
			String day = ce.getDays().get(i);

			String eventTime = ce.getStartHours().get(i).intValue() + ":" + ce.getStartMins().get(i).intValue();
			int eventStart = ce.getStartHours().get(i).intValue() * 60 + ce.getStartMins().get(i).intValue();
			int eventDuration = (int)(ce.getHeights().get(i) * 60.0 / (1250.0 / 24.0));

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

			ei = new EventInfo (name, loc, day, eventTime, eventEndTime);
			ei.setNotes(ce.getNotes().get(i));
			ei.setSilentAndBell(ce.getSilenced().get(i));

			allInfos.add(ei);
			ei = null;
		}
	}

	public void setLectureEventInfos() throws IOException {
		allLectureInfos = new ArrayList<>();

		for (int i = 0; i < lectureEventInfo.loadDays().size(); i++) {
			EventInfo ei;
			String eventTime = lectureEventInfo.loadHours().get(i) + ":40";

			if (lectureEventInfo.loadHours().get(i) < 10)
				eventTime = "0" + eventTime;

			String eventEndTime = lectureEventInfo.loadHours().get(i) + 1 + ":30";

			if (lectureEventInfo.loadHours().get(i) + 1 < 10)
				eventEndTime = "0" + eventEndTime;

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

			ei = new EventInfo ("", lectureEventInfo.loadRooms().get(i), lectureEventInfo.loadDays().get(i), eventTime, eventEndTime);
			ei.setNotes(lectureEventInfo.loadNotes().get(i));
			ei.setSilentAndBell(lectureEventInfo.loadSilents().get(i));

			allLectureInfos.add(ei);
			ei = null;
		}
	}

	public String findTime(MouseEvent e) { //done

		double time = ((e.getY()) / 52.08);
		String hour = (int) time + "";
		String min = ((e.getY() * 1440) / 1250) % 60 + "";
		boolean isPM = false;

		min = min.substring(0, min.indexOf("."));

		if (min.length() == 1)
			min = "0" + min;

		if (Integer.parseInt(hour) >= 12) {
			if (Integer.parseInt(hour) != 12)
				hour = (Integer.parseInt(hour) - 12) + "";
			isPM = true;
		}		

		if (Integer.parseInt(hour) == 0) {
			hour = "12";
			isPM = false;
		}

		if (isPM)
			return hour + ":" + min + " PM";
		else
			return hour + ":" + min + " AM";				
	}

	public void addEvent(MouseEvent e) throws IOException {

		Date date = new Date();  
		Calendar calendar = GregorianCalendar.getInstance(); 
		calendar.setTime(date); 
		int weekOfMonth = calendar.get(Calendar.WEEK_OF_MONTH);

		if (e.getClickCount() == 2 && !e.isConsumed()) {
			e.consume();

			if (e.getSource() == sunday) {
				CalendarEvent event = new CalendarEvent();
				String startTime = findTime(e);			
				event.setTime(startTime);
				event.generateEventOnClick(e.getY());
				sunday.getChildren().add(event);
				customEvents.add(event);
				ce.addEvent(Double.valueOf(startTime.substring(0, startTime.indexOf(":"))), Double.valueOf(startTime.substring(startTime.indexOf(":") + 1, startTime.lastIndexOf(" "))), event.getPrefHeight(), event.getInfo(), "Sun", "", "", true, weekOfMonth);
			}
			else if (e.getSource() == monday) {
				CalendarEvent event = new CalendarEvent();
				String startTime = findTime(e);			
				event.setTime(startTime);
				event.generateEventOnClick(e.getY());
				monday.getChildren().add(event);
				customEvents.add(event);
				ce.addEvent(Double.valueOf(startTime.substring(0, startTime.indexOf(":"))), Double.valueOf(startTime.substring(startTime.indexOf(":") + 1, startTime.lastIndexOf(" "))), event.getPrefHeight(), event.getInfo(), "Mon", "", "", true, weekOfMonth);
			}
			else if (e.getSource() == tuesday) {
				CalendarEvent event = new CalendarEvent();
				String startTime = findTime(e);			
				event.setTime(startTime);
				event.generateEventOnClick(e.getY());
				tuesday.getChildren().add(event);
				customEvents.add(event);
				ce.addEvent(Double.valueOf(startTime.substring(0, startTime.indexOf(":"))), Double.valueOf(startTime.substring(startTime.indexOf(":") + 1, startTime.lastIndexOf(" "))), event.getPrefHeight(), event.getInfo(), "Tue", "", "", true, weekOfMonth);
			}
			else if (e.getSource() == wednesday) {
				CalendarEvent event = new CalendarEvent();
				String startTime = findTime(e);			
				event.setTime(startTime);
				event.generateEventOnClick(e.getY());
				wednesday.getChildren().add(event);
				customEvents.add(event);
				ce.addEvent(Double.valueOf(startTime.substring(0, startTime.indexOf(":"))), Double.valueOf(startTime.substring(startTime.indexOf(":") + 1, startTime.lastIndexOf(" "))), event.getPrefHeight(), event.getInfo(), "Wed", "", "", true, weekOfMonth);
			}
			else if (e.getSource() == thursday) {
				CalendarEvent event = new CalendarEvent();
				String startTime = findTime(e);			
				event.setTime(startTime);
				event.generateEventOnClick(e.getY());
				thursday.getChildren().add(event);
				customEvents.add(event);
				ce.addEvent(Double.valueOf(startTime.substring(0, startTime.indexOf(":"))), Double.valueOf(startTime.substring(startTime.indexOf(":") + 1, startTime.lastIndexOf(" "))), event.getPrefHeight(), event.getInfo(), "Thu", "", "", true, weekOfMonth);
			}
			else if (e.getSource() == friday) {
				CalendarEvent event = new CalendarEvent();
				String startTime = findTime(e);			
				event.setTime(startTime);
				event.generateEventOnClick(e.getY());
				friday.getChildren().add(event);
				customEvents.add(event);
				ce.addEvent(Double.valueOf(startTime.substring(0, startTime.indexOf(":"))), Double.valueOf(startTime.substring(startTime.indexOf(":") + 1, startTime.lastIndexOf(" "))), event.getPrefHeight(), event.getInfo(), "Fri", "", "", true, weekOfMonth);
			}
			else if (e.getSource() == saturday) {
				CalendarEvent event = new CalendarEvent();
				String startTime = findTime(e);			
				event.setTime(startTime);
				event.generateEventOnClick(e.getY());
				saturday.getChildren().add(event);
				customEvents.add(event);
				ce.addEvent(Double.valueOf(startTime.substring(0, startTime.indexOf(":"))), Double.valueOf(startTime.substring(startTime.indexOf(":") + 1, startTime.lastIndexOf(" "))), event.getPrefHeight(), event.getInfo(), "Sat", "", "", true, weekOfMonth);
			}

			updateEvents(e);
			addEventInfos();
		}

		else if (e.getClickCount() == 1) {
			setEventInfos();
			peekCustomEventInfo(e);
		}
	}

	public void findFocusedAndRemove (KeyEvent e) {
		CalendarEvent backup;

		for (int i = 0; i < customEvents.size(); i++) {
			if (customEvents.get(i).getFocus() && e.getCode().equals(KeyCode.BACK_SPACE))  {
				backup = customEvents.get(i);

				allInfos.remove(i);

				ce.getDays().remove(i);
				ce.getHeights().remove(i);
				ce.getInfo().remove(i);
				ce.getStartHours().remove(i);
				ce.getStartMins().remove(i);
				ce.getLocations().remove(i);
				ce.getNotes().remove(i);
				ce.getSilenced().remove(i);

				backup.deleteEvent();
				customEvents.remove(i);
				eventInfo.hide();
			}
		}

		for (int i = 0; i < sunday.getChildren().size(); i++) {
			if (((CalendarEvent) sunday.getChildren().get(i)).getDeleted())
				sunday.getChildren().remove(i);
		}

		for (int i = 0; i < monday.getChildren().size(); i++) {
			if (((CalendarEvent) monday.getChildren().get(i)).getDeleted())
				monday.getChildren().remove(i);
		}

		for (int i = 0; i < tuesday.getChildren().size(); i++) {
			if (((CalendarEvent) tuesday.getChildren().get(i)).getDeleted())
				tuesday.getChildren().remove(i);
		}

		for (int i = 0; i < wednesday.getChildren().size(); i++) {
			if (((CalendarEvent) wednesday.getChildren().get(i)).getDeleted())
				wednesday.getChildren().remove(i);
		}

		for (int i = 0; i < thursday.getChildren().size(); i++) {
			if (((CalendarEvent) thursday.getChildren().get(i)).getDeleted())
				thursday.getChildren().remove(i);
		}

		for (int i = 0; i < friday.getChildren().size(); i++) {
			if (((CalendarEvent) friday.getChildren().get(i)).getDeleted())
				friday.getChildren().remove(i);
		}

		for (int i = 0; i < saturday.getChildren().size(); i++) {
			if (((CalendarEvent) saturday.getChildren().get(i)).getDeleted())
				saturday.getChildren().remove(i);
		}	
	}

	public void updateEvents(MouseEvent e) {
		for (int i = 0; i < customEvents.size(); i++) {
			ce.getStartHours().set(i, customEvents.get(i).getStartHour());
			ce.getStartMins().set(i, customEvents.get(i).getStartMin());
			ce.getHeights().set(i, customEvents.get(i).getMyHeight());
		}

		try {
			new SerializationUtil();
			SerializationUtil.serialize(ce, eventsDir);
		} catch (IOException e1) {
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

	Timeline updateTime;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		try {
			String os = System.getProperty("os.name").toUpperCase();

			if (os.contains("MAC")) {
				new SerializationUtil();
				users = (Users) SerializationUtil.deserialize(usersDirMac);
				userProfile = users.getActiveUser();
			
				courseDir = System.getProperty("user.home") + "/Library/Application Support/Bias/" 
						+ userProfile.getDirectory()  +"/courses.ser";

				eventsDir = System.getProperty("user.home") + "/Library/Application Support/Bias/" 
						+ userProfile.getDirectory()  +"/events.ser";

				eventInfoDir = System.getProperty("user.home") + "/Library/Application Support/Bias/"
						+ userProfile.getDirectory() + "/eventInfo.ser";

				qpDir = System.getProperty("user.home") + "/Library/Application Support/Bias/"
						+ userProfile.getDirectory() + "/qp.ser";

				settingsDir = System.getProperty("user.home") + "/Library/Application Support/Bias/" 
						+ userProfile.getDirectory()  +"/settings.ser";
			}
			else if (os.contains("WIN")) {
				new SerializationUtil();
				users = (Users) SerializationUtil.deserialize(usersDirWin);
				userProfile = users.getActiveUser();
				
				courseDir = System.getenv("APPDATA") + "\\Bias\\" + userProfile.getDirectory() + "\\courses.ser";

				eventsDir = System.getenv("APPDATA") + "\\Bias\\" + userProfile.getDirectory() + "\\events.ser";

				eventInfoDir = System.getenv("APPDATA") + "\\Bias\\" + userProfile.getDirectory() + "\\eventInfo.ser";

				qpDir = System.getenv("APPDATA") + "\\Bias\\" + userProfile.getDirectory() + "\\qp.ser";


				settingsDir = System.getenv("APPDATA") + "\\Bias\\" + userProfile.getDirectory() + "\\settings.ser";
			}

			if (!userProfile.getProfilePic().isEmpty()) {
				profileButtonCW.setFill(new ImagePattern(new Image(userProfile.getProfilePic())));
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

		try {
			new SerializationUtil();
			ec =  (EnrolledCourses) SerializationUtil.deserialize(courseDir);		
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}	

		try {
			new SerializationUtil();
			ce =  (CustomEvent) SerializationUtil.deserialize(eventsDir);			
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}	

		try {
			new SerializationUtil();
			lectureEventInfo =  (LectureEventInfo) SerializationUtil.deserialize(eventInfoDir);			
		} catch (ClassNotFoundException | IOException ex) {
		}

		setDate();

		try {
			addLectures();
			addEvents();
			setEventInfos();
			setLectureEventInfos();
		} catch (IOException e) {
			e.printStackTrace();
		}

		saveCustomEventInfo();
		saveLectureEventInfo();

		if (settings.getAllowNotifications()) {
			notifyActivities();
			notifyLectures();
		}

		updateTime = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				setDate();
			}
		}));
		updateTime.setCycleCount(Timeline.INDEFINITE);
		updateTime.play();
	}
}