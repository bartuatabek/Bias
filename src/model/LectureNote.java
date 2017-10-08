/* Copyright (c) 2017 Infinity Group Inc. and/or its affiliates. All rights reserved.
 * INFINITY PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package model;

import java.io.Serializable;

/**
 * Holds the data of a stored lecture note
 * @author Bartu Atabek
 * @since 2.0
 */

public class LectureNote implements Serializable {

	/**
	 * Serial Version UID of the class LectureNote
	 */
	private static final long serialVersionUID = 2337255945976933558L;

	// properties
	private String name;
	private String path;

	// constructors
	public LectureNote(String name, String path) {
		this.name = name;
		this.path = path;
	}

	// methods

	/**
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return path
	 */
	public String getPath() {
		return path;
	}

	@Override
	public boolean equals(Object n) {
		if (((LectureNote) n).getPath().equals(path)) {	
			return true;
		}	
		return false;
	}

	@Override
	public String toString() {
		return path;
	}
}