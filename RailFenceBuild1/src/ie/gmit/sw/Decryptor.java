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

	/**
	 * Constructor for the Decryptor class
	 * @param queue - The Queue is made of Resultables and used to process the results in an order
	 * @param cypherText - The CypherText the user input to the program
	 * @param key - The key used for this run at decrypting
	 * @param quadGramMap - The map that holds the key value pairs of the quadgrams
	 * @param counter - An AtomicInteger counter that is shared amongst many Decryptor objects, 
	 * @ 	The AtomicInteger prevents concurrency issues that may be caused by incrementing the number simultaneously 
	 */
	public Decryptor(BlockingQueue<Resultable> queue, String cypherText, int key, Map<String, Double> quadGramMap, AtomicInteger counter) {
		super();
		this.queue = queue;
		this.cypherText = cypherText;
		this.key = key;
		this.quadGramMap = quadGramMap;
		this.counter = counter;
	}
	//***** Decrypt a String cypherText using an integer key ***** 
		public String decrypt(String cypherText, int key){
			//Declare a 2D array of key rows and text length columns
			char[][] matrix = new char[key][cypherText.length()]; //The array is filled with chars with initial values of zero, i.e. '0'.
			
			//Fill the array
			int targetRow = 0;
			int index = 0;
			do{
				int row = 0; //Used to keep track of rows		
				boolean down = true; //Used to zigzag
				for (int i = 0; i < cypherText.length(); i++){ //Loop over the plaintext
					if (row == targetRow){
						matrix[row][i] = cypherText.charAt(index); //Add the next character in the plaintext to the array
						index++;
					}
					
					if (down){ //If we are moving down the array
						row++;
						if (row == matrix.length){ //Reached the bottom
							row = matrix.length - 2; //Move to the row above
							down = false; //Switch to moving up
						} 
					}else{ //We are moving up the array
						row--;
						
						if (row == -1){ //Reached the top
							row = 1; //Move to the first row
							down = true; //Switch to moving down
						}
					}
				}

				targetRow++;
			}while (targetRow < matrix.length);
			
			//printMatrix(matrix); //Output the matrix (debug)
			
			//Extract the cypher text
			StringBuffer sb = new StringBuffer(); //A string buffer allows a string to be built efficiently
			int row = 0;
			boolean down = true; //Used to zigzag
			for (int col = 0; col < matrix[row].length; col++){ //Loop over each column in the matrix
				sb.append(matrix[row][col]); //Extract the character at the row/col position if it's not 0.
				
				if (down){ //If we are moving down the array
					row++;
					if (row == matrix.length){ //Reached the bottom
						row = matrix.length - 2; //Move to the row above
						down = false; //Switch to moving up
					} 
				}else{ //We are moving up the array
					row--;
					
					if (row == -1){ //Reached the top
						row = 1; //Move to the first row
						down = true; //Switch to moving down
					}
				}

			}
			
			return sb.toString(); //Convert the StringBuffer into a String and return it
		}
		
	/**
	 * The run method Overrides the method in Runnable
	 * it decrypts the cyphertext using the associated key
	 * This plaintext is used to create a Resultable object
	 * This Resultable is added to the queue and counter is incremented
	 */
	@Override
	public void run() {		
		String plainText = decrypt(cypherText, key);
		TextScorer ts = new TextScorer(quadGramMap);
		
		Resultable r = createResult(plainText, ts);
		addResultableToQueue(r);
		counterIncrement(1);
		//System.out.println(getIntCounter());
	}
	/**
	 * A Resultable is added to the process queue
	 * @param r - A Resultable object to be added to the queue
	 */
	public void addResultableToQueue(Resultable r) {
		try {
			this.queue.put(r);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	/**
	 * This method checks the state of the counter variable
	 * if the counter is at its max value, the type is a poisonResult, Otherwise its just a Result Object
	 * @param plainText - The decrypted text
	 * @param ts - A Text Scorer object that is used to score the text, needed to create the Resultables
	 * @return r - The newly created resultable object
	 */
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
