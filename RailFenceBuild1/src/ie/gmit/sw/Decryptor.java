package ie.gmit.sw;

public class Decryptor implements Runnable {//producer

	//private BlockingQueue<Resultable> queue;
	private String cypherText;
	private int key;
	
	public Decryptor(/*BlockingQueue<Resultable> queue, */String cypherText, int key) {
		super();
		//this.queue = queue;
		this.cypherText = cypherText;
		this.key = key;
	}
	public void run() {
		RailFence rf = new RailFence();
		String plainText = rf.decrypt(cypherText, key);
		System.out.println(plainText);
		/*Resultable r = null;
		try {
			queue.put(r);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
	}
	

}
