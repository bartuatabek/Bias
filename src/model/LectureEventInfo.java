/*
 * Copyright (c) 2017 Infinity Group Inc. and/or its affiliates. All rights reserved.
 * INFINITY PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Stores the info for the lecture events
 * @author Musab Gelisgen
 * @since 2.0
 */

public class LectureEventInfo implements Serializable{
	
	/**
	 * Serial Version UID of the class LectureEventInfo
	 */
	private static final long serialVersionUID = -7539085138131566013L;
	
	// properties
	private ArrayList<Integer> hours;
	private ArrayList<String> days;
	private ArrayList<String> notes;
	private ArrayList<Boolean> silents;
	private ArrayList<String> rooms;
	
	// constructors
	public LectureEventInfo() {
		notes = new ArrayList<>();
		silents = new ArrayList<>();
		days = new ArrayList<>();
		hours = new ArrayList<>();
		rooms = new ArrayList<>();
	}
	
	// methods
	
	/**
	 * Saves the event info
	 * @param notes
	 * @param silent
	 * @param day
	 * @param hour
	 */
	public void save (String notes, boolean silent, String day, int hour) {
		this.notes.add(notes);
		this.silents.add(silent);
		this.hours.add(hour);
		this.days.add(day);
	}
	
	/**
	 * Saves hours and days for the event info
	 * @param hours
	 * @param days
	 * @param rooms
	 */
	public void saveHoursAndDays (ArrayList<Integer> hours, ArrayList<String> days,  ArrayList<String> rooms) {
		this.hours = hours;
		this.days = days;
		this.rooms = rooms;
	}
	
	/**
	 * Initialize notes and alarms
	 * @param size
	 */
	public void initializeNotesAndSilents (int size) {
		System.out.println("init");
		silents = new ArrayList<>();
		notes = new ArrayList<>();
		
		for (int i = 0; i < size; i++) {
			silents.add(true);
			notes.add("");
			
		}
	}

	/**
	 * @return notes
	 */
	public ArrayList<String> loadNotes() {
		return notes;
	}
	
	/**
	 * @return silents
	 */
	public ArrayList<Boolean> loadSilents() {
		return silents;
	}
	
	/**
	 * @return days
	 */
	public ArrayList<String> loadDays() {
		return days;
	}
	
	/**
	 * @return hours
	 */
	public ArrayList<Integer> loadHours() {
		return hours;
	}
	
	/**
	 * @return rooms
	 */
	public ArrayList<String> loadRooms() {
		return rooms;
	}
}