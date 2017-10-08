/*
 * Copyright (c) 2017 Infinity Group Inc. and/or its affiliates. All rights reserved.
 * INFINITY PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package model;

/**
 * Stores the registered quizzes and presentations
 * @author Musab Gelisgen
 * @since 2.0
 */

import java.io.Serializable;
import java.util.ArrayList;

public class QuizzesAndPresentations implements Serializable {

	/**
	 * Serial Version UID of the class QuizzesAndPresentations
	 */
	private static final long serialVersionUID = -748285138131566013L;

	// properties
	private int month;
	private int day;
	private ArrayList<Integer> attendedQuizzes;
	private ArrayList<Integer> attendedPresentations;

	// constructors
	public QuizzesAndPresentations () {
		attendedQuizzes = new ArrayList<>();
		attendedPresentations = new ArrayList<>();
	}

	// methods
	
	/**
	 * Saves attended quizzes and presentations
	 * @param attendedQuizzes
	 * @param attendedPresentations
	 */
	public void save (ArrayList<Integer> attendedQuizzes, ArrayList<Integer> attendedPresentations, int month, int day) {
		this.attendedQuizzes = attendedQuizzes;
		this.attendedPresentations = attendedPresentations;
		this.month = month;
		this.day = day;
	}

	/**
	 * @return attendedActivities
	 */
	public ArrayList<Integer> loadQuizzes () {
		return attendedQuizzes;
	}
	
	/**
	 * @return attendedPresentations
	 */
	public ArrayList<Integer> loadPresentations () {
		return attendedPresentations;
	}
	
	/**
	 * @return month that the qp's saved
	 */
	public int loadMonth () {
		return month;
	}
	
	/**
	 * @return day that the qp's saved
	 */
	public int loadDay () {
		return day;
	}
}