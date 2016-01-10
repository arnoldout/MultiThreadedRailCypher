package ie.gmit.sw;

public class Result implements Resultable {
	private double score;
	private String plainText;
	private int keyUsed;
	
	
	/**
	 * A Constructor to initialise the variables for the poison result
	 * @param score - The Cumulative score for the decrypted text
	 * @param plainText - The Decrypted text 
	 * @param keyUsed - The key used to decrypt the text
	 */
	public Result(double score, String plainText, int keyUsed) {
		super();
		this.score = score;
		this.plainText = plainText;
		this.keyUsed = keyUsed;
	}
	/**
	 * This function returns the variables of the result objects in string form
	 */
	public String toString()
	{		
		return "Plain Text: "+getPlainText()
			+"\nKey: "+getKeyUsed()
			+"\nScore: "+getScore();		
	}
	
	/**
	 * This method compares the two resultables and returns the larger of the two objects
	 * @param a - A Resultable object for comparison
	 */
	@Override
	public Resultable chkNwRes(Resultable a) {
		if(a==null)
		{
			a = this;
		}
		else if(a.getScore()<this.getScore())
		{
			a = this;
		}
		return a;
	}
	public double getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public String getPlainText() {
		return plainText;
	}
	public void setPlainText(String plainText) {
		this.plainText = plainText;
	}

	public int getKeyUsed() {
		return keyUsed;
	}

	public void setKeyUsed(int keyUsed) {
		this.keyUsed = keyUsed;
	}
}
