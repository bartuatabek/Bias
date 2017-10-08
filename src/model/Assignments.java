/*
 * Copyright (c) 2017 Infinity Group Inc. and/or its affiliates. All rights reserved.
 * INFINITY PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Custom assignments list that uses the ArrayList sub-structure
 * @author Bartu Atabek
 * @since 2.0
 */

public class Assignments implements Serializable {

	/**
	 * Serial Version UID of the class Assignments
	 */
	private static final long serialVersionUID = -5550081904226034726L;

	// properties
	private ArrayList<StudentAssignment> assignment_list;

	// constructors
	public Assignments() {
		assignment_list = new ArrayList<StudentAssignment>();
	}

	// methods

	/**
	 * Adds new  assignment to the list
	 * @param assignment
	 */
	public void add(StudentAssignment assignment) {
		assignment_list.add(assignment);				
	}


	/**
	 * Returns the assignments list
	 * @return the assignment list
	 */
	public ArrayList<StudentAssignment> getList() {
		return assignment_list;
	}
}