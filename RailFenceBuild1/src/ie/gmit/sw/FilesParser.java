package ie.gmit.sw;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class FilesParser implements Runnable {
	
	private Map<String, Double> map;
	private String fileName;

	public FilesParser(Map<String, Double> map, String fileName) {
		super();
		this.map = map;
		this.fileName = fileName;
	}

	public void parse() {
		BufferedReader br = null;
		String line;

		// reading in from file safely
		try {
			/*
			 * File f = new File(fileName); FileInputStream fileInputStream =
			 * null; byte[] bFile = new byte[(int) f.length()]; fileInputStream
			 * = new FileInputStream(f); fileInputStream.read(bFile);
			 * 
			 * for (int i = 0; i < bFile.length; i++) { //
			 * System.out.print((char) bFile[i]); } System.out.print("File:"
			 * +(char) bFile[0]+"\n"); fileInputStream.close();
			 */
			 br = new BufferedReader(new FileReader(this.fileName));

			while ((line = br.readLine()) != null) {
				String tmpS = line.substring(0, 4);
				double tmpD = Double.valueOf(line.substring(5));
				map.put(tmpS, tmpD);
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
