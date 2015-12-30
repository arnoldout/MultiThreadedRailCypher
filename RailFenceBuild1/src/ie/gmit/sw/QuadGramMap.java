package ie.gmit.sw;

public class QuadGramMap {
	final public static int GRAM_SIZE = 4;
	private int score;
	private String gram;
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public String getGram() {
		return gram;
	}
	public void setGram(String gram) {
		this.gram = gram;
	}
}
