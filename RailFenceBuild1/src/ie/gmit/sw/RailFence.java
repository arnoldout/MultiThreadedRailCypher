package ie.gmit.sw;

import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/* Basic implementation of the Rail Fence Cypher using a 2D char array 
 * Note that there are more efficient ways to encrypt and decrypt, but the following implementation illustrates the steps
 * involved in each process and shows how the zig-zagging works. Feel free to change / adapt. 
 */

public class RailFence {
	
	//***** Encrypt a String called cypherText using an integer key ***** 
	public String encrypt(String cypherText, int key){
		//Declare a 2D array of key rows and text length columns
		char[][] matrix = new char[key][cypherText.length()]; //The array is filled with chars with initial values of zero, i.e. '0'.
		
		//Fill the array
		int row = 0; //Used to keep track of rows
		boolean down = true; //Used to zigzag
		for (int i = 0; i < cypherText.length(); i++){ //Loop over the plaintext
			matrix[row][i] = cypherText.charAt(i); //Add the next character in the plaintext to the array
			
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
		
		//printMatrix(matrix); //Output the matrix (debug)
		
		//Extract the cypher text
		StringBuffer sb = new StringBuffer(); //A string buffer allows a string to be built efficiently
		for (row = 0; row < matrix.length; row++){ //Loop over each row in the matrix
			for (int col = 0; col < matrix[row].length; col++){ //Loop over each column in the matrix
				if (matrix[row][col] > '0') sb.append(matrix[row][col]); //Extract the character at the row/col position if it's not 0.
			}
		}
		System.out.println("Encrypted Text: "+sb.toString());
		return sb.toString(); //Convert the StringBuffer into a String and return it
	}
		
	public static void main(String[] args){
		
		String gramFile = "src/4grams.txt";
		//String fileName = "src/ecryptor.txt";
		Map<String, Double> map = new ConcurrentHashMap<String, Double>();
		BlockingQueue<Resultable> queue;
		
		String cypherText = menu();
		if(cypherText.length()>0)
		{
			synchronized (map) {
				map = syncGParseFile(gramFile, map);
			}
			//String t = "STOPTHEMATTHECASTLEGATESSTOPTHEMATTHECASTLEGATESSTOPTHEMATTHECASTLEGATESSTOPTHEMATTHECASTLEGATESSTOPTHEMATTHECASTLEGATESSTOPTHEMATTHECASTLEGATESSTOPTHEMATTHECASTLEGATESSTOPTHEMATTHECASTLEGATESSTOPTHEMATTHECASTLEGATESSTOPTHEMATTHECASTLEGATESSTOPTHEMATTHECASTLEGATESSTOPTHEMATTHECASTLEGATESSTOPTHEMATTHECASTLEGATESSTOPTHEMATTHECASTLEGATESSTOPTHEMATTHECASTLEGATESSTOPTHEMATTHECASTLEGATESSTOPTHEMATTHECASTLEGATESSTOPTHEMATTHECASTLEGATESSTOPTHEMATTHECASTLEGATESSTOPTHEMATTHECASTLEGATESSTOPTHEMATTHECASTLEGATESSTOPTHEMATTHECASTLEGATESSTOPTHEMATTHECASTLEGATESSTOPTHEMATTHECASTLEGATESSTOPTHEMATTHECASTLEGATESSTOPTHEMATTHECASTLEGATESSTOPTHEMATTHECASTLEGATESSTOPTHEMATTHECASTLEGATESSTOPTHEMATTHECASTLEGATESSTOPTHEMATTHECASTLEGATESSTOPTHEMATTHECASTLEGATESSTOPTHEMATTHECASTLEGATESSTOPTHEMATTHECASTLEGATESSTOPTHEMATTHECASTLEGATESSTOPTHEMATTHECASTLEGATESSTOPTHEMATTHECASTLEGATESSTOPTHEMATTHECASTLEGATESSTOPTHEMATTHECASTLEGATESSTOPTHEMATTHECASTLEGATESSTOPTHEMATTHECASTLEGATESSTOPTHEMATTHECASTLEGATESSTOPTHEMATTHECASTLEGATESSTOPTHEMATTHECASTLEGATESSTOPTHEMATTHECASTLEGATESSTOPTHEMATTHECASTLEGATESSTOPTHEMATTHECASTLEGATESSTOPTHEMATTHECASTLEGATESSTOPTHEMATTHECASTLEGATESSTOPTHEMATTHECASTLEGATESSTOPTHEMATTHECASTLEGATESSTOPTHEMATTHECASTLEGATES";
			//String s = new RailFence().encrypt(t, 9);
			//s = s.toUpperCase();
			
			queue = new ArrayBlockingQueue<Resultable>(cypherText.length()/2);
			
			//Result object to be updated when a new highest 
			//result is found
			Resultable highResult = null;
			Integer keyThrdCnt = 1;
			boolean endLoop = false;
			AtomicInteger producerCounter = new AtomicInteger(2);
			
			while(endLoop == false)
			{
				keyThrdCnt++;
				
				Decryptor d = new Decryptor(queue, cypherText, keyThrdCnt, map, producerCounter);
				new Thread(d).start();	
				
				Resultable currResult = null;
				try {
					currResult = queue.take();		
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if(currResult instanceof PoisonResult)
				{
					endLoop = true;
				}
				
				highResult = currResult.chkNwRes(highResult);
				
			}
			System.out.println(highResult.toString());
		}
	}

/**
 * Returns a String containing encrypted text the user inputs 
 * after working through 2 options in a menu
 * @return The cyphertext input by the user
 */
	public static String menu() {
		System.out.print("Would you like to decrypt text from\n1. A text file\n2. The console: ");
		Scanner console = new Scanner(System.in);
		int input = console.nextInt();
		String cypherText = null;
		
			switch(input)
			{
				case 1:
				cypherText = encryptedReadFileText(console);
					break;
				case 2:
				cypherText = userInEncryptedText(console);
					break;
				default:
					cypherText = "";
					System.out.println("Invalid Option");
			}
			console.close();
		return cypherText;
	}


/**
 * User is asked to enter cypher text, this text is taken in by the Scanner object
 * and stored in a CypherText object.
 * The text is converted to a suitable format for use against the 4 grams map
 * 
 * @param console is the Scanner object for keyboard input
 * @return The converted encrypted text is returned as a String
 */
public static String userInEncryptedText(Scanner console) {
	String cypherText;
	System.out.print("Enter Cypher Text: ");
	CypherText ct = new CypherText();
	cypherText = console.next();
	ct.convertToCypher(cypherText);
	cypherText = ct.getCypherText();
	return cypherText;
}

/**
 * User is asked to enter the name of the text file containing decrypted text
 * A DecryptedFileParser object will attempt to parse the file
 * Once read in, the getCypherText method is called and the returning String is itself returned.
 * @param console is the Scanner object for keyboard input
 * @return The converted encrypted text is returned as a String
 */
public static String encryptedReadFileText(Scanner console) {
	System.out.print("Enter the file name: ");
	String fileName = console.next();
	DecryptionFileParser dfp = new DecryptionFileParser(fileName);
	dfp.parse();
	return dfp.getCypherText();
}

/**
 * This method allows the program to delegate the reading of the map to a worker thread
 * This allows for a quicker run through, as the 4 grams text file can be read in simultaneously
 * while the user works through the menu
 * @param fileName - The name of the 4grams text file used to populate the map
 * @param map - The map that contains the quadgrams
 * @return The same map that is passed down as a parameter is returned, now populated
 */
public static Map<String, Double> syncGParseFile(String fileName, Map<String, Double> map) {
	GramFileParser fp = new GramFileParser(map, fileName);
	new Thread(fp).start();
	map = fp.getMap();
	return map;
}
}
