package ie.gmit.sw;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class FilesParser {

	public Map<String, Double> parse (String fileName)
	{
		Map<String, Double> temp = new ConcurrentHashMap<String, Double>();
		BufferedReader br = null;
		String line;

		//reading in from file safely
		try {
			br = new BufferedReader(new FileReader(fileName));
			while ((line = br.readLine()) != null) {
				String tmpS = line.substring(0, 4);
				double tmpD = Double.valueOf(line.substring(5));
				temp.put(tmpS, tmpD);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//closing the file safely
		try {
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return temp;
	}
}
