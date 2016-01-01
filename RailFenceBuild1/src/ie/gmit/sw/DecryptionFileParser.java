package ie.gmit.sw;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class DecryptionFileParser implements Fileable{

	private String fileName;
	private StringBuilder cypherText;
	

	public DecryptionFileParser(String fileName) {
		super();
		this.fileName = fileName;
		this.cypherText = new StringBuilder();
	}

	
	@Override
	public void parse() {
		BufferedReader br = null;
		String line;

		// reading in from file safely
		try {
			
			 br = new BufferedReader(new FileReader(this.fileName));

			while ((line = br.readLine()) != null) {
				line = line.replaceAll("[^\\p{IsAlphabetic}]+", "");
				line = line.toUpperCase();
				cypherText.append(line);
				
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
		parse();
		
	}

	@Override
	public String getFileName() {
		// TODO Auto-generated method stub
		return fileName;
	}

	@Override
	public void setFileName(String fileName) {
		// TODO Auto-generated method stub
		this.fileName = fileName;
	}

	public String getCypherText() {
		return cypherText.toString();
	}

	public void setCypherText(StringBuilder cypherText) {
		this.cypherText = cypherText;
	}

}
