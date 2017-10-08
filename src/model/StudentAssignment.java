/* Copyright (c) 2017 Infinity Group Inc. and/or its affiliates. All rights reserved.
 * INFINITY PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Holds the data for a assignment
 * @author Bartu Atabek
 * @since 2.0
 */
public class StudentAssignment implements Serializable {

	/**
	 * Serial Version UID of the class Assignment
	 */
	private static final long serialVersionUID = -3698454637900519868L;

	//properties
	private String name;
	private String info;
	private String due;
	private int progress;
	private LocalDate remaining;

	// constructors
	public StudentAssignment() {
		name = "";
		info = "";
		due = "";	
		progress = 0;
		remaining =  LocalDate.now();
	}

	// methods

	/**
	 * Loads the stored data when needed
	 * @param data
	 */
	public void load(ArrayList<Object> data) {
		name = (String) data.get(0);
		info = (String) data.get(1);
		due = (String) data.get(2);
		progress = (int) data.get(3);
		remaining =  (LocalDate) data.get(4);
	}

	/**
	 * @return data
	 */
	public Object save() {
		ArrayList<Object> data = new ArrayList<Object>();

		data.add(name);
		data.add(info);
		data.add(due);
		data.add(progress);
		data.add(remaining);

		return data;
	}

	public String toString() {
		return name;
	}	
}