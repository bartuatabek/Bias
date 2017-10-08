/* Copyright (c) 2017 Infinity Group Inc. and/or its affiliates. All rights reserved.
 * INFINITY PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Stores the required user data and information
 * @author Bartu Atabek
 * @since 2.0
 */

public class Users implements Serializable {

	/**
	 * Serial Version UID of the class Profile
	 */
	private static final long serialVersionUID = -6777887675960638788L;

	// properties
	private ArrayList<Profile> users;
	
	// constructors
	public Users() {
		users = new ArrayList<Profile>();
	}
	
	// methods
	
	/**
	 * Adds a new user to the stack
	 * @param newUser
	 */
	public void newUser(Profile newUser) {
		if (users.size() <= 5)
			users.add(newUser);
	}
	
	/**
	 * @return users
	 */
	public ArrayList<Profile> getUsers() {
		return users;
	}
			
	/**
	 * Sets the selected user as active
	 * @param i
	 */
	public Profile setActiveUser(int i) {
		users.get(i).setOnline(true);
		return getActiveUser();
	}
	
	/**
	 * @return the active user
	 */
	public Profile getActiveUser() {
		for (Profile user : users) {
			if (user.isOnline())
				return user;
		}
		return null;
	}
	
	/**
	 * @return true if a user is active
	 */
	public boolean hasActiveUser() {
		for (Profile user : users) {
			if (user.isOnline())
				return true;
		}
		return false;
	}
	
	/**
	 * Sets all online users to offline
	 */
	public void setOffline() {
		for (Profile user : users) {
			if (user.isOnline())
				user.setOnline(false);
		}
	}
}