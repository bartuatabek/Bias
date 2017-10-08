package application;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import de.codecentric.centerdevice.MenuToolkit;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {
	static final String appName = "Bias";

	@Override
	public void start(Stage primaryStage) {

		try {			
			String FileFolder = "";

			System.out.println("Searching for system");

			String os = System.getProperty("os.name").toUpperCase();
			if (os.contains("WIN")) {
				FileFolder = System.getenv("APPDATA") + "\\" + "Bias";
				System.out.println("Found windows");
			}

			else if (os.contains("MAC")) {
				FileFolder = System.getProperty("user.home") + "/Library/Application Support" + "/Bias";
				System.out.println("Found mac");
			}

			System.out.println("Searching for resource folder");
			File directory = new File(FileFolder);

			if (directory.exists()) {
				System.out.println("Found folder");
			}

			if (directory.exists() == false) {
				directory.mkdir();
				System.out.println("Could not find folder so created it");
			}
						
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
			Scene scene = new Scene(root,1400,700);
			scene.getStylesheets().add(getClass().getResource("/view/application.css").toExternalForm());
			//primaryStage.setResizable(false);
			primaryStage.setScene(scene);
			primaryStage.show();

			MenuToolkit tk = MenuToolkit.toolkit();

			MenuBar bar = new MenuBar();

			// Application Menu
			Menu appMenu = new Menu(appName);

			// Create a new menu item
			MenuItem aboutItem = new MenuItem("About Bias");
			aboutItem.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					try {
						Stage stage = new Stage();
						Parent root;
						root = FXMLLoader.load(getClass().getResource("/view/About.fxml"));
						Scene scene = new Scene(root);
						stage.setResizable(false);
						stage.setScene(scene);
						stage.show();
					} catch (IOException e) {
						e.printStackTrace();
					}}
			});

			MenuItem prefsItem = new MenuItem("Preferences...");
			prefsItem.setAccelerator(new KeyCodeCombination(KeyCode.COMMA, KeyCombination.SHORTCUT_DOWN));
			prefsItem.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					try {
						AnchorPane pane = FXMLLoader.load(getClass().getResource("/view/Profile.fxml"));
						root.getChildren().setAll(pane);
					} catch (IOException e) {
						e.printStackTrace();
					}}
			});

			MenuItem signOutItem = new MenuItem("Sign Out");
			signOutItem.setAccelerator(new KeyCodeCombination(KeyCode.W, KeyCombination.SHORTCUT_DOWN, KeyCombination.SHIFT_DOWN));
			signOutItem.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					try {
						AnchorPane pane;
						pane = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
						root.getChildren().setAll(pane);
					} catch (IOException e) {
						e.printStackTrace();
					}}
			});

			appMenu.getItems().addAll(new SeparatorMenuItem(), aboutItem, new SeparatorMenuItem(), prefsItem, new SeparatorMenuItem(),
					signOutItem, new SeparatorMenuItem(), tk.createHideMenuItem(appName), tk.createHideOthersMenuItem(), tk.createUnhideAllMenuItem(),
					new SeparatorMenuItem(), tk.createQuitMenuItem(appName));

			// File Menu 
			Menu fileMenu = new Menu("File");
			MenuItem newAssItem = new MenuItem("New Assignment");
			newAssItem.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.SHORTCUT_DOWN));
			newAssItem.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					try {
						AnchorPane pane;
						pane = FXMLLoader.load(getClass().getResource("/view/Dashboard.fxml"));
						root.getChildren().setAll(pane);
					} catch (IOException e) {
						e.printStackTrace();
					}}
			});

			MenuItem newEventItem = new MenuItem("New Event");
			newEventItem.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.SHORTCUT_DOWN, KeyCombination.SHIFT_DOWN));
			newEventItem.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					try {
						AnchorPane pane;
						pane = FXMLLoader.load(getClass().getResource("/view/Calendar.fxml"));
						root.getChildren().setAll(pane);
					} catch (IOException e) {
						e.printStackTrace();
					}}
			});

			Menu importMenu = new Menu("Import Notes");
			MenuItem ChooseNoteItem = new MenuItem("Notes");
			ChooseNoteItem.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					try {
						AnchorPane pane;
						pane = FXMLLoader.load(getClass().getResource("/view/Dashboard.fxml"));
						root.getChildren().setAll(pane);
					} catch (IOException e) {
						e.printStackTrace();
					}}
			});

			importMenu.getItems().addAll(ChooseNoteItem);

			fileMenu.getItems().addAll(newAssItem, newEventItem, new SeparatorMenuItem(), importMenu);

			// Edit 
			Menu editMenu = new Menu("Edit");
			MenuItem undoItem = new MenuItem("Undo");
			undoItem.setAccelerator(new KeyCodeCombination(KeyCode.Z, KeyCombination.SHORTCUT_DOWN));

			MenuItem redoItem = new MenuItem("Undo");
			redoItem.setAccelerator(new KeyCodeCombination(KeyCode.Z, KeyCombination.SHORTCUT_DOWN, KeyCombination.SHIFT_DOWN));

			MenuItem cutItem = new MenuItem("Cut");
			cutItem.setAccelerator(new KeyCodeCombination(KeyCode.X, KeyCombination.SHORTCUT_DOWN));

			MenuItem copyItem = new MenuItem("Copy");
			copyItem.setAccelerator(new KeyCodeCombination(KeyCode.C, KeyCombination.SHORTCUT_DOWN));

			MenuItem pasteItem = new MenuItem("Paste");
			pasteItem.setAccelerator(new KeyCodeCombination(KeyCode.V, KeyCombination.SHORTCUT_DOWN));

			MenuItem deleteItem = new MenuItem("Delete");

			MenuItem selectAllItem = new MenuItem("Select All");
			selectAllItem.setAccelerator(new KeyCodeCombination(KeyCode.A, KeyCombination.SHORTCUT_DOWN));

			editMenu.getItems().addAll(undoItem, redoItem, new SeparatorMenuItem(), cutItem, copyItem, pasteItem,
					deleteItem, selectAllItem);

			// View Menu
			Menu viewMenu = new Menu("View");

			MenuItem sidebarItem = new MenuItem("Left Sidebar");

			MenuItem actualSizeItem = new MenuItem("Actual Size");
			actualSizeItem.setDisable(true);
			actualSizeItem.setAccelerator(new KeyCodeCombination(KeyCode.DIGIT0, KeyCombination.SHORTCUT_DOWN));

			MenuItem zoomInItem = new MenuItem("Zoom In");
			zoomInItem.setAccelerator(new KeyCodeCombination(KeyCode.PLUS, KeyCombination.SHORTCUT_DOWN));

			MenuItem zoomOutItem = new MenuItem("Zoom Out");
			zoomOutItem.setAccelerator(new KeyCodeCombination(KeyCode.MINUS, KeyCombination.SHORTCUT_DOWN));

			MenuItem fullscreenItem = new MenuItem("Enter Fullscreen");
			zoomOutItem.setAccelerator(new KeyCodeCombination(KeyCode.F, KeyCombination.SHORTCUT_DOWN, KeyCombination.SHIFT_DOWN));

			viewMenu.getItems().addAll(sidebarItem, new SeparatorMenuItem(), actualSizeItem, zoomInItem, zoomOutItem,
					new SeparatorMenuItem(), fullscreenItem);

			// Window Menu
			Menu windowMenu = new Menu("Window");

			windowMenu.getItems().addAll(tk.createMinimizeMenuItem(), tk.createZoomMenuItem(), new SeparatorMenuItem(),
					tk.createBringAllToFrontItem());

			// Help Menu 
			Menu helpMenu = new Menu("Help");
		
			MenuItem helpItem = new MenuItem("Bias Help");
			helpItem.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					if(Desktop.isDesktopSupported()) {
					  try {
						Desktop.getDesktop().browse(new URI("http://www.infinitus.cf/bias"));
					} catch (IOException | URISyntaxException e) {
						e.printStackTrace();
					}
					}}
			});
			
			helpMenu.getItems().addAll(helpItem);

			bar.getMenus().addAll(appMenu, fileMenu, editMenu, viewMenu, windowMenu, helpMenu);

			tk.setGlobalMenuBar(bar);

		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}