/*
 * Copyright (c) 2017 Infinity Group Inc. and/or its affiliates. All rights reserved.
 * INFINITY PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Stores the enrolled courses of the user
 * @author Bartu Atabek
 * @since 2.0
 */

public class EnrolledCourses implements Serializable {

	/**
	 * Serial Version UID of the class Enrolled Courses
	 */
	private static final long serialVersionUID = -3614885138131566013L;

	// properties
	private ArrayList<Integer> enrolledCourses;
	private ArrayList<String> courseNames;

	// constructors
	public EnrolledCourses() {
		enrolledCourses = new ArrayList<Integer>();
		courseNames = new ArrayList<String>();
	}

	// methods

	/**
	 * Saves the courses and adds them to the enrolled courses
	 * @param courses
	 */
	public void save(ArrayList<Integer> courses, String courseName) {
		for (int i = 0; i < courses.size(); i++)		
		{
			enrolledCourses.add(courses.get(i));
		}

		courseNames.add(courseName);
		cleanDuplicates();
	}

	/**
	 * Removes the course at the given index
	 * @param index
	 */
	public void remove(int index) {
		enrolledCourses.remove(index);
		courseNames.remove(index);
	}

	/**
	 * @return enrollerCourses
	 */
	public ArrayList<Integer> load() {
		return enrolledCourses;
	}

	/**
	 * @return courseNames
	 */
	public ArrayList<String> getCourseNames() {
		return courseNames;
	}

	/**
	 * Checks the list for duplicate notes
	 * and cleans them
	 */
	public void cleanDuplicates() {
		List<Integer> cleansedCourses = new ArrayList<Integer>();
		List<String> cleansedNames = new ArrayList<String>();

		for (int n : enrolledCourses) {
			if (!cleansedCourses.contains(n)) {
				cleansedCourses.add(n);
			}				
		}
		enrolledCourses = (ArrayList<Integer>) cleansedCourses;

		for (String str : courseNames) {
			if (!cleansedNames.contains(str)) {
				cleansedNames.add(str);
			}				
		}
		courseNames = (ArrayList<String>) cleansedNames;
	}	
}