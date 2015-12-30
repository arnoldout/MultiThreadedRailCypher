package ie.gmit.sw;

public class Result {
	private int score;
	private String plainText;
	private int keyUsed;
	public Result(int score, String plainText, int keyUsed) {
		super();
		this.score = score;
		this.plainText = plainText;
		this.keyUsed = keyUsed;
	}
	
	public int getScore() {
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
