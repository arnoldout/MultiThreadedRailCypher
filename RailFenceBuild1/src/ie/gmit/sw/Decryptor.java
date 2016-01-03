package ie.gmit.sw;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Decryptor implements Runnable {//producer

	private BlockingQueue<Resultable> queue;
	private String cypherText;
	private int key;
	private Map<String, Double> quadGramMap;
	private AtomicInteger counter;
	
	public Decryptor(BlockingQueue<Resultable> queue, String cypherText, int key, Map<String, Double> quadGramMap, AtomicInteger counter) {
		super();
		this.queue = queue;
		this.cypherText = cypherText;
		this.key = key;
		this.quadGramMap = quadGramMap;
		this.counter = counter;
	}
	public void run() {		
		RailFence rf = new RailFence();
		String plainText = rf.decrypt(cypherText, key);
		TextScorer ts = new TextScorer(quadGramMap);
		
		Resultable r = createResult(plainText, ts);
		addResultableToQueue(r);
		counterIncrement(1);
		//System.out.println(getIntCounter());
	}
	public void addResultableToQueue(Resultable r) {
		try {
			this.queue.put(r);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public Resultable createResult(String plainText, TextScorer ts) {
		Resultable r;
		if(getIntCounter()<plainText.length()/2)
		{
			r = new Result(ts.getScore(plainText), plainText, key);
		}
		else
		{
			r = new PoisonResult(ts.getScore(plainText), plainText, key);
		}
		return r;
	}
	
	public AtomicInteger getAtomCounter() {
		return counter;
	}
	public void counterIncrement(int number) {
		counter.addAndGet(number);
	}
	public int getIntCounter() {
		return counter.get();
	}
	public void setCounter(AtomicInteger counter) {
		this.counter = counter;
	}
	

}
