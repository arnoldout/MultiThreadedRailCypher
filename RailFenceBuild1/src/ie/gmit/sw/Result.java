package ie.gmit.sw;

public class Result {
	private double score;
	private String plainText;
	private int keyUsed;
	public Result(double score, String plainText, int keyUsed) {
		super();
		this.score = score;
		this.plainText = plainText;
		this.keyUsed = keyUsed;
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
