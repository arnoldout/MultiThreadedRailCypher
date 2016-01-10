package ie.gmit.sw;

public class CypherText {
	private StringBuilder cypherText;
	
	/**
	 * Empty Constructor that initialises the cypherTest variable 
	 */
	public CypherText() {
		super();
		this.cypherText = new StringBuilder();
	}
	
	/**
	 * This method removes any non alphabetic characters from the String
	 * It converts them to uppercase and appends them to the cyphertext StringBuilder
	 * This format ensures the text is in a format compatible with the cyphertext
	 * @param line - A string that needs to be converted
	 */
	public void convertToCypher(String line)
	{
		//[^\\p{IsAlphabetic}] is a regular expression (Regex) 
		//command to search for only alphabetical characters
		//in this case, it takes in a string, and removes any non alphabetical characters
		//the remaining characters are then converted to upper case and added to the cyperText
		line = line.replaceAll("[^\\p{IsAlphabetic}]+", "");
		line = line.toUpperCase();
		cypherText.append(line);
	}

	/**
	 * Calls the toString method of StringBuilder
	 * @return the StringBuilder's toString value
	 */
	public String getCypherText() {
		return cypherText.toString();
	}

	public void setCypherText(StringBuilder cypherText) {
		this.cypherText = cypherText;
	}
}
