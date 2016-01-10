package ie.gmit.sw;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class DecryptionFileParser implements Fileable {

	private String fileName;
	private CypherText cypherText;

	/**
	 * A Constructor that takes in a file name and initialises the cyphertext
	 * @param fileName - File name for the decrypted file name
	 */
	public DecryptionFileParser(String fileName) {
		super();
		this.fileName = fileName;
		this.cypherText = new CypherText();
	}

	/**
	 * This method tries to parse the current object's file
	 */
	@Override
	public void parse() {
		BufferedReader br = null;
		String line;

		// reading in from file safely
		try {
			//try to setup the reader, check if file is there
			br = new BufferedReader(new FileReader(this.fileName));
		}catch(FileNotFoundException e)
		{
			//output to user a message
			System.out.println("File Not Found");
		}
		//if the file has been found, the parsing can begin
		if(br!=null)
		{
			try {
				while ((line = br.readLine()) != null) {
					cypherText.convertToCypher(line);
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
	}

	/**
	 * Overriding the Runnable run method
	 * it simply calls the parse method
	 */
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
		return cypherText.getCypherText();
	}

	public void setCypherText(StringBuilder cypherText) {
		this.cypherText.setCypherText(cypherText);
	}

}
