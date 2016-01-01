package ie.gmit.sw;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class GramFileParser implements Runnable, Fileable {
	
	private Map<String, Double> map;
	private String fileName;

	public GramFileParser(Map<String, Double> map, String fileName) {
		super();
		this.map = map;
		this.fileName = fileName;
	}

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

	public void processString(String line) {
	
			String tmpS = line.substring(0, 4);
			double tmpD = Double.valueOf(line.substring(5));
			map.put(tmpS, tmpD);		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		parse();
	}
	public Map<String, Double> getMap() {
		return map;
	}

	public void setMap(Map<String, Double> map) {
		this.map = map;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
