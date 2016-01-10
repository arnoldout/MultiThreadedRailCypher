package ie.gmit.sw;

public class PoisonResult implements Resultable {
	private double score;
	private String plainText;
	private int keyUsed;
	
	/**
	 * A Constructor to initialise the variables for the poison result
	 * @param score - The Cumulative score for the decrypted text
	 * @param plainText - The Decrypted text 
	 * @param keyUsed - The key used to decrypt the text
	 */
	public PoisonResult(double score, String plainText, int keyUsed) {
		super();
		this.score = score;
		this.plainText = plainText;
		this.keyUsed = keyUsed;
	}
	@Override
	public String toString()
	{
		return "Plain Text: "+getPlainText()
				+"\nKey: "+getKeyUsed()
				+"\nScore: "+getScore();
	}
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
	@Override
	public double getScore() {
		// TODO Auto-generated method stub
		return score;
	}

	@Override
	public void setScore(int score) {
		// TODO Auto-generated method stub
		this.score = score;
	}

	@Override
	public String getPlainText() {
		// TODO Auto-generated method stub
		return plainText;
	}

	@Override
	public void setPlainText(String plainText) {
		// TODO Auto-generated method stub
		this.plainText = plainText;
	}

	@Override
	public int getKeyUsed() {
		// TODO Auto-generated method stub
		return keyUsed;
	}

	@Override
	public void setKeyUsed(int keyUsed) {
		// TODO Auto-generated method stub
		this.keyUsed = keyUsed;
	}

}
