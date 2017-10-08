/*
 * Copyright (c) 2017 Infinity Group Inc. and/or its affiliates. All rights reserved.
 * INFINITY PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package model;

import java.io.Serializable;

/**
 * Stores the enrolled courses of the user
 * @author Bartu Atabek
 * @since 2.0
 */

public class Settings implements Serializable {

	/**
	 * Serial Version UID of the class Settings
	 */
	private static final long serialVersionUID = -1230691138169271397L;

	// properties
	private boolean rememberMe;
	private boolean keepMeLoggedIn;
	private boolean allowNotifications;
	private boolean removePastAssignments;
	private boolean enableNotes;
	private boolean saveNotesAsText;
	private boolean enableCourses;
	private boolean enableEditing;

	// constructors
	public Settings() {
		rememberMe = false;
		keepMeLoggedIn = false;
		allowNotifications = true;
		removePastAssignments = false;
		enableNotes = true;
		saveNotesAsText = false;
		enableCourses = true;
		enableEditing = false;
	}

	// methods

	/**
	 * Sets rememberMe
	 * @param bool
	 */
	public void setRememberMe(boolean bool) {
		rememberMe = bool;
	}

	/**
	 * @return rememberMe
	 */
	public boolean getRememberMe() {
		return rememberMe;
	}

	/**
	 * Sets keepMeLoggedIn
	 * @param bool
	 */
	public void setKeepMeLoggedIn(boolean bool) {
		keepMeLoggedIn = bool;
	}

	/**
	 * @return keepMeLoggedIn
	 */
	public boolean getKeepMeLoggedIn() {
		return keepMeLoggedIn;
	}

	/**
	 * Sets allowNotifications
	 * @param bool
	 */
	public void setAllowNotifications(boolean bool) {
		allowNotifications = bool;
	}

	/**
	 * @return allowNotifications
	 */
	public boolean getAllowNotifications() {
		return allowNotifications;
	}

	/**
	 * Sets removePastAssignments
	 * @param bool
	 */
	public void setRemovePastAssignments(boolean bool) {
		removePastAssignments = bool;
	}

	/**
	 * @return removePastAssignments
	 */
	public boolean getRemovePastAssignments() {
		return removePastAssignments;
	}

	/**
	 * Sets enableNotes
	 * @param bool
	 */
	public void setEnableNotes(boolean bool) {
		enableNotes = bool;
	}

	/**
	 * @return enableNotes
	 */
	public boolean getEnableNotes() {
		return enableNotes;
	}

	/**
	 * Sets saveNotesAsText
	 * @param bool
	 */
	public void setSaveNotesAsText(boolean bool) {
		saveNotesAsText = bool;
	}

	/**
	 * @return saveNotesAsText
	 */
	public boolean getSaveNotesAsText() {
		return saveNotesAsText;
	}

	/**
	 * Sets enableCourses
	 * @param bool
	 */
	public void setEnableCourses(boolean bool) {
		enableCourses = bool;
	}

	/**
	 * @return enableCourses
	 */
	public boolean getEnableCourses() {
		return enableCourses;
	}

	/**
	 * Sets enableEditing
	 * @param bool
	 */
	public void setEnableEditing(boolean bool) {
		enableEditing = bool;
	}

	/**
	 * @return enableEditing
	 */
	public boolean getEnableEditing() {
		return enableEditing;
	}	
}