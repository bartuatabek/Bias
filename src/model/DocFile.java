/*
 * Copyright (c) 2017 Infinity Group Inc. and/or its affiliates. All rights reserved.
 * INFINITY PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package model;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Iterator;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;   
import org.apache.poi.xwpf.usermodel.XWPFParagraph; 
import org.apache.poi.xwpf.usermodel.XWPFRun;

import javafx.collections.ObservableList;   

/**
 * Generates and writes a word document with the given information to the specified directory
 * @author Bartu Atabek
 * @since 2.0
 */

public class DocFile {   

	//properties
	private boolean success;

	// constructors
	public DocFile(String filename, ObservableList<CharSequence> paragraph) {
		success = false;
		try {
			newWordDoc(filename, paragraph);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// methods
	
	/**
	 * creates and writes the document into the given directory
	 * @param filename
	 * @param paragraph
	 * @throws Exception
	 */
	public void newWordDoc(String filename, ObservableList<CharSequence> paragraph) throws Exception {   
		XWPFDocument document = new XWPFDocument();   

		Iterator<CharSequence> iter = paragraph.iterator();
		
		while (iter.hasNext()) {
			CharSequence seq = iter.next();
			createParagraph(document, seq.toString());
		}
		
		String userName = System.getProperty("user.name");
		String strDirectory ="/Users/" + userName + "/Documents/Bias/Notes";

		File file = new File(strDirectory);

		if (!file.exists() && !file.isDirectory()) {
			success = (new File(strDirectory)).mkdirs();
		}
		
		FileOutputStream fos = new FileOutputStream(new File(strDirectory + "/" + filename + ".docx"));   
		document.write(fos);   
		fos.close(); 
		document.close();
		success = true;
	} 

	/**
	 * adds a new paragraph to the document
	 * @param document
	 * @param paragraph
	 */
	public void createParagraph(XWPFDocument document, String paragraph) {
		XWPFParagraph tmpParagraph = document.createParagraph();
		tmpParagraph.setAlignment(ParagraphAlignment.BOTH);
		XWPFRun tmpRun = tmpParagraph.createRun(); 
		tmpRun.setText(paragraph);
		tmpRun.setFontSize(12);
	}
	
	/**
	 * @return success
	 */
	public boolean isSuccessful() {
		return success;
	}
} 