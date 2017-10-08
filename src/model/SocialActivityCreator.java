/*
 * Copyright (c) 2017 Infinity Group Inc. and/or its affiliates. All rights reserved.
 * INFINITY PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package model;

import java.io.*;
import java.util.ArrayList;

/**
 * Creates a list of activities from the source file
 * @author Musab Gelisgen
 * @since 2.0
 */

public class SocialActivityCreator {

	// properties
	BufferedReader br;
	LineNumberReader lnr;
	ArrayList<String> activities;
	ArrayList<String> times;
	ArrayList<String> locations;

	// constructors
	public SocialActivityCreator() { 
		activities = new ArrayList<>();
		times = new ArrayList<>();
		locations = new ArrayList<>();

		createActivities();
	}

	// methods

	/**
	 * Creates a list of activities from the source file
	 */
	public void createActivities() {
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(
					new File(getClass().getResource("/resources/Events.txt").getFile()))));
			lnr = new LineNumberReader( br );

			String line;
			int firstEqual;
			int secondEqual;
			int thirdEqual;

			while( (line = lnr.readLine()) != null) {
				firstEqual = line.indexOf('=');
				secondEqual = line.indexOf('=', firstEqual + 1);
				thirdEqual = line.indexOf('=', secondEqual + 1);

				activities.add(line.substring(0, firstEqual));
				times.add(line.substring(firstEqual + 1, secondEqual));
				locations.add(line.substring(secondEqual + 1, thirdEqual));
			}
		}
		catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * @return activities
	 */
	public ArrayList<String> getActivities() {
		return activities;
	}

	/**
	 * @return times
	 */
	public ArrayList<String> getTimes() {
		return times;
	}

	/**
	 * @return locations
	 */
	public ArrayList<String> getLocations() {
		return locations;
	}
}