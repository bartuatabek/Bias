/* Copyright (c) 2017 Infinity Group Inc. and/or its affiliates. All rights reserved.
 * INFINITY PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package model;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Holds the data of the stored notes
 * @author Bartu Atabek
 * @since 2.0
 */

public class NoteList implements Serializable {

	/**
	 * Serial Version UID of the class NoteList
	 */
	private static final long serialVersionUID = 2337255945976933558L;

	// properties
	private ArrayList<LectureNote> note_list;

	// constructors
	public NoteList() {
		note_list = new ArrayList<LectureNote>();
	}

	// methods

	/**
	 * Adds a note to the list
	 * @param note
	 */
	public void add(LectureNote note) {
		File file = new File(note.getPath());

		if (file.exists())
			note_list.add(note);				
	}

	/**
	 * @return note_list
	 */
	public ArrayList<LectureNote> getList() {
		return note_list;
	}

	/**
	 * Removes the note at the given index
	 * @param index
	 */
	public void remove(int index) {
		note_list.remove(index);
	}

	/**
	 * Checks the list for duplicate notes
	 * and cleans them
	 */
	public void cleanDuplicates() {
		List<LectureNote> cleansed = new ArrayList<LectureNote>();

		for (LectureNote n : note_list) {
			if (!cleansed.contains(n)) {
				cleansed.add(n);
			}				
		}
		note_list = (ArrayList<LectureNote>) cleansed;
	}

	/**
	 * Reconstructs the path from the given filename
	 * @param name
	 * @return
	 */
	public String getPathFromName(String name) {
		for (LectureNote n : note_list) {
			if (n.getName().equals(name)) {
				return n.getPath();
			}
		}
		return "FileNotFound";
	}

	/**
	 * Checks files for removal and duplicate cleansing
	 */
	public void checkFiles() {
		ArrayList<LectureNote> toBeRemoved = new ArrayList<LectureNote>();

		for (LectureNote n : note_list) {
			File file = new File(n.getPath());

			if (!file.exists())
				toBeRemoved.add(n);
		} 
		note_list.removeAll(toBeRemoved);
	}
	
	/**
	 * @param indexOfDraggingNode
	 * @param indexOfDropTarget
	 * Swaps two notes
	 */
	public void swapNotes(int indexOfDraggingNode, int indexOfDropTarget ) {
		Collections.swap((List<?>) note_list, indexOfDraggingNode, indexOfDropTarget);
	}
}