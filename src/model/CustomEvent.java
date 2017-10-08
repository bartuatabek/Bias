/*
 * Copyright (c) 2017 Infinity Group Inc. and/or its affiliates. All rights reserved.
 * INFINITY PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Custom events are user generated events that can hold event information and
 * detail in order to rebuild at application start or when required
 * @author Musab Gelisgen
 * @since 2.0
 */

public class CustomEvent implements Serializable {

	/**
	 * Serial Version UID of the class CustomEvent
	 */
	private static final long serialVersionUID = -6029482710483724958L;

	// properties
	private ArrayList<Double> startHours;
	private ArrayList<Double> startMins;
	private ArrayList<Double> heights;
	private ArrayList<String> info;
	private ArrayList<String> days;
	private ArrayList<String> locations;
	private ArrayList<String> notes;
	private ArrayList<Boolean> silenced;
	private int weekOfMonth;

	// constructors
	public CustomEvent() {
		startHours = new ArrayList<>();
		startMins = new ArrayList<>();
		heights = new ArrayList<>();
		info = new ArrayList<>();
		days = new ArrayList<>();
		locations = new ArrayList<>();
		notes = new ArrayList<>();
		silenced = new ArrayList<>();
	}

	// methods

	/**
	 * Generates a custom event 
	 * @param startHour
	 * @param startMin
	 * @param height
	 * @param info
	 * @param day
	 */
	public void addEvent (double startHour, double startMin, double height, String info, String day, String location, String note, boolean silenced, int weekOfMonth) {
		startHours.add(startHour);
		startMins.add(startMin);
		heights.add(height);
		this.info.add(info);
		days.add(day);
		locations.add(location);
		notes.add(note);
		this.silenced.add(silenced);
		this.weekOfMonth = weekOfMonth;
	}

	/**
	 * @return startHours
	 */
	public ArrayList<Double> getStartHours() {
		return startHours;
	}

	/**
	 * @return startMins
	 */
	public ArrayList<Double> getStartMins() {
		return startMins;
	}

	/**
	 * @return heights
	 */
	public ArrayList<Double> getHeights() {
		return heights;
	}

	/**
	 * @return info
	 */
	public ArrayList<String> getInfo() {
		return info;
	}

	/**
	 * @return days
	 */
	public ArrayList<String> getDays() {
		return days;
	}
	
	/**
	 * @return locations
	 */
	public ArrayList<String> getLocations() {
		return locations;
	}
	
	/**
	 * @return notes
	 */
	public ArrayList<String> getNotes() {
		return notes;
	}
	
	/**
	 * @return silenced
	 */
	public ArrayList<Boolean> getSilenced() {
		return silenced;
	}
	
	/**
	 * @return weekOfMonth
	 */
	public int getWeekOfMonth() {
		return weekOfMonth;
	}
}