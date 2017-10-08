/*
 * Copyright (c) 2017 Infinity Group Inc. and/or its affiliates. All rights reserved.
 * INFINITY PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package model;

/**
 * Stores the registered activities
 * @author Musab Gelisgen
 * @since 2.0
 */

import java.io.Serializable;
import java.util.ArrayList;

public class AttendedActivities implements Serializable {

	/**
	 * Serial Version UID of the class AttendedActivities
	 */
	private static final long serialVersionUID = -748285138131566013L;

	// properties
	private ArrayList<Integer> attendedActivities;

	// constructors
	public AttendedActivities () {
		attendedActivities = new ArrayList<>();
	}

	// methods
	
	/**
	 * Saves a attended activity
	 * @param attended
	 */
	public void save (ArrayList<Integer> attended) {
		attendedActivities = attended;
	}

	/**
	 * @return attendedActivities
	 */
	public ArrayList<Integer> load () {
		return attendedActivities;
	}
}