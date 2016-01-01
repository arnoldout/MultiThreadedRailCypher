package ie.gmit.sw;

public class PoisonResult implements Resultable {
	private double score;
	private String plainText;
	private int keyUsed;
	
	
	public PoisonResult(double score, String plainText, int keyUsed) {
		super();
		this.score = score;
		this.plainText = plainText;
		this.keyUsed = keyUsed;
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
