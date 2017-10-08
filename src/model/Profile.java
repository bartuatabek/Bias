/* Copyright (c) 2017 Infinity Group Inc. and/or its affiliates. All rights reserved.
 * INFINITY PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package model;

import java.io.Serializable;

/**
 * Stores the required user data and information
 * @author Bartu Atabek
 * @since 2.0
 */

public class Profile implements Serializable {

	/**
	 * Serial Version UID of the class Profile
	 */
	private static final long serialVersionUID = -7010557039785604098L;

	// properties	
	private String profilePic;
	private String name;
	private String mail;
	private String username;
	private String id;
	private String password;
	private String directory;
	private boolean online;
	private boolean directory_created;
	private boolean keep_logged;
	private boolean splash_active;

	// constructors
	public Profile() {
		profilePic = "";
		name = "Name";
		mail = "";
		username = "";
		id = "";
		password = "";
		directory = "";
		online = false;
		directory_created = false;
		keep_logged = false;
		splash_active = true;
	}

	// methods

	/**
	 * Changes the profile picture of the user
	 * @param img
	 */
	public void setProfilePic(String img) {
		profilePic = img;
	}

	/**
	 * @return profilePic
	 */
	public String getProfilePic() {
		return profilePic;
	}

	/**
	 * Sets the name of the user
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the mail address of the user
	 * @param mail
	 */
	public void setMail(String mail) {
		this.mail = mail;
	}

	/**
	 * @return mail
	 */
	public String getMail() {
		return mail;
	}

	/**
	 * Sets the user name of the user
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;

		if (!directory_created) {
			directory = username;
			directory_created = true;
		}
	}

	/**
	 * @return user name
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Sets the ID of the user
	 * @param id
	 */
	public void setID(String id) {
		this.id = id;
	}

	/**
	 * @return id
	 */
	public String getID() {
		return id;
	}

	/**
	 * Sets the password of the user
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @return directory
	 */
	public String getDirectory() {
		return directory;
	}

	/**
	 * Sets the users status to online or offline
	 * @param bool
	 */
	public void setOnline(boolean bool) {
		online = bool;
	}

	/**
	 * @return online
	 */
	public boolean isOnline() {
		return online;
	}
	
	/**
	 * Sets the user to be keep logged or not
	 * @param bool
	 */
	public void setKeepLogged(boolean bool) {
		keep_logged = bool;
	}

	/**
	 * @return keep_logged
	 */
	public boolean getKeepLogged() {
		return keep_logged;
	}
	
	/**
	 * Sets splash_active
	 * @param bool
	 */
	public void setSplashActive(boolean bool) {
		splash_active = bool;
	}

	/**
	 * @return splash_active
	 */
	public boolean isSplashActive() {
		return splash_active;
	}
	
	@Override
	public String toString() {
		return "Name: " + name + ", Username: " + username;
	}
}