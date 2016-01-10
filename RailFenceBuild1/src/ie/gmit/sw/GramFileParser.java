package ie.gmit.sw;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class GramFileParser implements Runnable, Fileable {
	
	private Map<String, Double> map;
	private String fileName;
	
	/**
	 * Constructor to initilize the GramFileParser variables
	 * @param map - The map that will contain the key value pairs for the quadgrams and their values
	 * @param fileName - The String for the name of the File containing the quadgrams and their values
	 * 
	 */
	public GramFileParser(Map<String, Double> map, String fileName) {
		super();
		this.map = map;
		this.fileName = fileName;
	}


	/**
	 * This method tries to parse the current object's file
	 */
	public void parse() {
		BufferedReader br = null;
		String line;

		// reading in from file safely
		try {
			
			 br = new BufferedReader(new FileReader(this.fileName));

			while ((line = br.readLine()) != null) {
				processString(line);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// closing the file safely
		try {
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * This method takes in a String, adds the first 4 characters as the key and the rest of the string as the value
	 * The key and value are added to the objects's map
	 * @param line - Should contain 4 characters followed by some numbers
	 */
	public void processString(String line) {
	
			String tmpS = line.substring(0, 4);
			double tmpD = Double.valueOf(line.substring(5));
			map.put(tmpS, tmpD);		
	}

	/**
	 * Run is Overridden from Runnable, simply calls the parse method
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		parse();
	}
	/**
	 * Get method for the object's map
	 * @return the map of the current object
	 */
	public Map<String, Double> getMap() {
		return map;
	}
	/**
	 * Set method that takes in a map and sets it to the value of the object's map
	 * @param map - The map object to be set
	 */
	public void setMap(Map<String, Double> map) {
		this.map = map;
	}

	/**
	 * Get method to get the fileName
	 * @return the value of the fileName String
	 */
	public String getFileName() {
		return fileName;
	}
	
	/**
	 *  Set Method for the fileName String 
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
