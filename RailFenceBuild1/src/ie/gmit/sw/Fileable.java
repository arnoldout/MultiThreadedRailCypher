package ie.gmit.sw;

public interface Fileable extends Runnable{

	public abstract void parse();

	public abstract void run();

	public abstract String getFileName();

	public abstract void setFileName(String fileName);

}