/* Copyright (c) 2017 Infinity Group Inc. and/or its affiliates. All rights reserved.
 * INFINITY PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Holds the data of the courses
 * @author Bartu Atabek
 * @since 2.0
 */

public class CourseData implements Serializable {

	/**
	 * Serial Version UID of the class CourseData
	 */
	private static final long serialVersionUID = -6585935898564234110L;

	// properties
	private ArrayList<ArrayList<ArrayList<ArrayList<String>>>> courseData;
	
	// constructors
	public CourseData() {
		courseData = new ArrayList<ArrayList<ArrayList<ArrayList<String>>>>();
		courseData.add(new ArrayList<ArrayList<ArrayList<String>>>());
		courseData.add(new ArrayList<ArrayList<ArrayList<String>>>());
		courseData.add(new ArrayList<ArrayList<ArrayList<String>>>());
		courseData.add(new ArrayList<ArrayList<ArrayList<String>>>());
		courseData.add(new ArrayList<ArrayList<ArrayList<String>>>());
		courseData.add(new ArrayList<ArrayList<ArrayList<String>>>());
	}
	
	// methods
		
	/**
	 * @param gradeList
	 * @param index
	 * Updates the gradeList of current course
	 */
	public void updateData(ArrayList<ArrayList<ArrayList<String>>> gradeList, int index) {
		courseData.set(index, gradeList);
	}
	
	/**
	 * @param index
	 * @return gradeList
	 */
	public ArrayList<ArrayList<ArrayList<String>>> getGradeList(int index) {
		return courseData.get(index);
	}
	
	/**
	 * Removes all course data
	 */
	public void clear() {
		courseData.clear();
	}
	
	/**
	 * Removes all course data
	 */
	public void remove(int index) {
		courseData.add(index, new ArrayList<ArrayList<ArrayList<String>>>());
		courseData.remove(index++);
	}
}