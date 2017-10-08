/** Copyright (c) 2017 Infinity Group Inc. and/or its affiliates. All rights reserved.
 * INFINITY PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package model;

import java.io.*;
import java.net.URL;
import java.util.*;

/**
 * Generates a course from the stored source and extracts all the required
 * information for rebuilding the selected course
 * @author Musab Gelisgen
 * @since 2.0
 */

public class CourseCreator {

	// properties
	ArrayList<String> faculties;
	ArrayList<Integer> lineNumbersOfFaculties;
	ArrayList<String> codesOfCourses;
	ArrayList<String> sections;
	ArrayList<String> courseNames;
	ArrayList<String> teacherNames;
	ArrayList<ArrayList<String>> lectureDates;
	ArrayList<String> singleLectureDates;

	ArrayList<String> nonRepeatFaculties; /* For Auto-Complete */
	ArrayList<String> nonRepeatCodes; /* For Auto-Complete */
	ArrayList<String> nonRepeatSections; /* For Auto-Complete */

	ArrayList<Integer> startIndex; /* Gives the starting line number of the course in Lectures.txt when course is enrolled */
	ArrayList<Integer> endIndex; /* Gives the ending line number of the course in Lectures.txt when course is enrolled */
	ArrayList<Integer> coursePlaceOnArrays; /* Gives the position of the course in the arrays that will be used for rebuilding */


	LineNumberReader lnr;
	BufferedReader br;

	int currentLine;

	// constructors
	public CourseCreator() {
		startIndex = new ArrayList<>();
		endIndex = new ArrayList<>();
		coursePlaceOnArrays = new ArrayList<>();

		createFacultyLists();
	}

	// methods

	/**
	 * @return startIndex
	 */
	public ArrayList<Integer> getStartIndex (){
		return startIndex;
	}

	/**
	 * @return endIndex
	 */
	public ArrayList<Integer> getEndIndex ()	{
		return endIndex;
	}

	/**
	 * @return coursePlaceOnArrays
	 */
	public ArrayList<Integer> getCoursePlaceOnArrays () {
		return coursePlaceOnArrays;
	}

	/**
	 * Has size 2331 
	 * @return faculties
	 */
	public ArrayList<String> getAllFaculties() { 
		return faculties;
	}

	/**
	 * Has size 2331 
	 * @return codesOfCourses
	 */
	public ArrayList<String> getAllCodes() {
		return codesOfCourses;
	}

	/**
	 * Has size 2331 
	 * @return sections
	 */
	public ArrayList<String> getAllSections() {
		return sections;
	}

	/**
	 * Has size 2331 
	 * @return lineNumbersOfFaculties
	 */
	public ArrayList<Integer> getAllLineNumbersOfFaculties() { 
		return lineNumbersOfFaculties;
	}

	/**
	 * Has size 2331 
	 * @return courseNames
	 */
	public ArrayList<String> getAllCourseNames() {
		return courseNames;
	}

	/**
	 * Has size 2331 
	 * @return teacherNames
	 */
	public ArrayList<String> getAllTeacherNames() {
		return teacherNames;
	}

	/**
	 * Has size 2331 
	 * @return lectureDates
	 */
	public ArrayList<ArrayList<String>> getAllLectureDates() {
		return lectureDates;
	}

	/**
	 * @return nonRepeatFaculties
	 */
	public ArrayList<String> getFaculties () {
		return nonRepeatFaculties;
	}

	/**
	 * @return nonRepeatCodes
	 */
	public ArrayList<String> getCodes () {
		return nonRepeatCodes;
	}

	/**
	 * @return nonRepeatSections
	 */
	public ArrayList<String> getSections ()
	{
		return nonRepeatSections;
	}

	/**
	 * reads the Lectures.txt file
	 * @param url
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static Object readFile(URL url) throws IOException, ClassNotFoundException {
		try (ObjectInputStream is = new ObjectInputStream(url.openStream())) {
			Object o = is.readObject();
			return o;
		}
	}


	/**
	 * Generates the faculty list from the Lectures.txt
	 */
	private void createFacultyLists() {
		faculties = new ArrayList<String>();
		codesOfCourses = new ArrayList<String>();
		lineNumbersOfFaculties = new ArrayList<Integer>();
		sections = new ArrayList<String>();
		nonRepeatFaculties = new ArrayList<>();
		courseNames = new ArrayList<>();
		teacherNames = new ArrayList<>();
		lectureDates = new ArrayList<>();

		String courseCode = "";
		String faculty = "";
		String section = "";

		boolean isAfterEmptyLine = true;
		boolean isAfterFirstLine = false;
		boolean isAfterSecondLine = false;
		boolean isAfterThirdLine = false;

		try {
			br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/resources/Lectures.txt")));		
			lnr = new LineNumberReader(br);
			String line;
			while ( (line = lnr.readLine()) != null) {

				/*1 fac + code + sec */
				if (isAfterEmptyLine) { 
					int facultyEnd = line.indexOf(' ');
					faculty = line.substring(0,facultyEnd);
					faculties.add(faculty);

					int codeStart = line.indexOf(' ') + 1;
					int codeEnd = line.indexOf('-');
					courseCode = line.substring(codeStart,codeEnd);
					codesOfCourses.add( courseCode);

					int sectionStart = line.indexOf('-') + 1;
					section = line.substring(sectionStart);
					sections.add( section);	

					currentLine = lnr.getLineNumber();
					lineNumbersOfFaculties.add(currentLine);

					isAfterEmptyLine = false;
					isAfterFirstLine = true;
				}

				/* 2 courseName */
				else if (isAfterFirstLine) {
					courseNames.add(line);
					isAfterFirstLine = false;
					isAfterSecondLine = true;

				}

				/* 3 teacher */
				else if (isAfterSecondLine) {
					teacherNames.add(line);
					isAfterSecondLine = false;

					if (line.equals("")) {
						isAfterEmptyLine = true;
						singleLectureDates = new ArrayList<>();
						lectureDates.add(singleLectureDates);	
					}

					else
						isAfterThirdLine = true;
				}

				/* 4 5 6 ... dates */
				else if (isAfterThirdLine) {
					singleLectureDates = new ArrayList<>();

					while (!line.equals("")) {
						singleLectureDates.add(line);
						line = lnr.readLine();
					}

					lectureDates.add(singleLectureDates);

					isAfterThirdLine = false;
					isAfterEmptyLine = true;
				}
			}      
			lnr.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}

		for (int i = 0; i < faculties.size(); i++) {
			String facultyToAdd = faculties.get(i);

			if (nonRepeatFaculties.size() == 0 || !nonRepeatFaculties.get(nonRepeatFaculties.size() - 1).equals(facultyToAdd))
				nonRepeatFaculties.add(facultyToAdd);	
		}	
	}

	/**
	 * Cleanses the duplicate lines inside faculties 
	 * @param faculty
	 */
	public void setNonRepeatCodes ( String faculty) {
		nonRepeatCodes = new ArrayList<>();

		for (int i = 0; i < faculties.size(); i++) {
			if (faculties.get(i).equals(faculty)) {
				String codeToAdd = codesOfCourses.get(i);

				if (nonRepeatCodes.size() == 0 || !nonRepeatCodes.get(nonRepeatCodes.size() - 1).equals(codeToAdd))
					nonRepeatCodes.add(codeToAdd);
			}
		}
	}

	/**
	 * Cleanses the duplicate lines inside sections 
	 * @param faculty
	 * @param code
	 */
	public void setNonRepeatSections ( String faculty, String code) {
		nonRepeatSections = new ArrayList<>();

		for (int i = 0; i < codesOfCourses.size(); i++) {
			if ( codesOfCourses.get(i).equals(code) && faculties.get(i).equals(faculty)) {
				String sectionToAdd = sections.get(i);

				if (nonRepeatSections.size() == 0 || !nonRepeatSections.get(nonRepeatSections.size() - 1).equals(sectionToAdd))
					nonRepeatSections.add(sectionToAdd);
			}
		}
	}

	/**
	 * Adds the indexes of the course that are required to rebuild the course when loaded
	 * or at application start
	 * @param faculty
	 * @param code
	 * @param section
	 */
	public void addIndexes (String faculty, String code, String section) {
		for (int i = 0; i < faculties.size(); i++) {
			if (faculties.get(i).equalsIgnoreCase(faculty) && codesOfCourses.get(i).equals(code) && sections.get(i).equals(section)) {
				boolean alreadyExist = false;
				for (int j = 0; j < coursePlaceOnArrays.size(); j++) {
					if (coursePlaceOnArrays.get(j) == i)
					{
						alreadyExist = true;
					}
				}

				if (!alreadyExist) {
					startIndex.add(lineNumbersOfFaculties.get(i));
					endIndex.add(lineNumbersOfFaculties.get(i + 1) - 2);
					coursePlaceOnArrays.add(i);
				}
			}
		}
	}
}