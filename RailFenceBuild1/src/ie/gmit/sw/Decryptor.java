package ie.gmit.sw;

import java.util.Map;
import java.util.concurrent.BlockingQueue;

public class Decryptor implements Runnable {//producer

	private BlockingQueue<Resultable> queue;
	private String cypherText;
	private int key;
	private Map<String, Double> quadGramMap;
	
	public Decryptor(BlockingQueue<Resultable> queue, String cypherText, int key, Map<String, Double> quadGramMap) {
		super();
		this.queue = queue;
		this.cypherText = cypherText;
		this.key = key;
		this.quadGramMap = quadGramMap;
	}
	public void run() {
		RailFence rf = new RailFence();
		String plainText = rf.decrypt(cypherText, key);
		TextScorer ts = new TextScorer(quadGramMap);
		Resultable r;
		if(key<plainText.length()/2)
		{
			r = new Result(ts.getScore(plainText), plainText, key);
		}
		else
		{
			System.out.println(key);
			r = new PoisonResult(ts.getScore(plainText), plainText, key);
		}
		try {
			this.queue.put(r);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
	

}
