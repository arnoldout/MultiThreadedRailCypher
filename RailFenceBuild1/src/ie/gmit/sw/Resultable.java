package ie.gmit.sw;

public interface Resultable {

	/*
	 * abstract methods for objects of type Resultable
	 */
	public abstract double getScore();

	public abstract void setScore(int score);

	public abstract String getPlainText();

	public abstract void setPlainText(String plainText);

	public abstract int getKeyUsed();

	public abstract void setKeyUsed(int keyUsed);
	
	public abstract String toString();

	Resultable chkNwRes(Resultable a);
}