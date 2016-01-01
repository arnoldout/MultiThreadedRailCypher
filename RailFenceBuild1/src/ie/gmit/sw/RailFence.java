package ie.gmit.sw;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

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
	
	//***** Output the 2D array in CSV format ***** 
	/*private void printMatrix(char[][] matrix){
		for (int row = 0; row < matrix.length; row++){ //Loop over each row in the matrix
			for (int col = 0; col < matrix[row].length; col++){ //Loop over each column in the matrix
				System.out.print(matrix[row][col]); //Output the value at row/column index
				if (col < matrix[row].length - 1) System.out.print(",");
			}
			System.out.println();
		}
	}*/
		
	public static void main(String[] args){
		String fileName = "src/4grams.txt";
		Map<String, Double> map = new HashMap<String, Double>();
		BlockingQueue<Resultable> queue;
		
		map = syncParseFile(fileName, map);
		userInput("Enter your name: ");
		
		String s = new RailFence().encrypt("STOPTHEMATTHECASTLEGATES", 12);
		s = s.toUpperCase();
		
		queue = new ArrayBlockingQueue<Resultable>(s.length()/2);
		
		//Result object to be updated when a new highest 
		//result is found
		Resultable highResult = null;
		Integer keyThrdCnt = 1;
		boolean endLoop = false;
		while(endLoop == false)
		{
			synchronized (keyThrdCnt) {
				keyThrdCnt++;
				System.out.println(keyThrdCnt.toString());
			}
			
			Decryptor d = new Decryptor(queue, s, keyThrdCnt, map);
			new Thread(d).start();	
			
			Resultable currResult = null;
			try {
				currResult = queue.take();		
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(currResult instanceof PoisonResult)
			{
				endLoop = true;
			}
			
			highResult = chkNwRes(highResult, currResult);
			//System.out.println(keyThrdCnt);
			
		}
		System.out.println(highResult.getPlainText()+"\n  >"+queue.isEmpty());
	}


	public static Resultable chkNwRes(Resultable a, Resultable r) {
		if(a==null)
		{
			a = r;
		}
		else if(a.getScore()<r.getScore())
		{
			a = r;
		}
		return a;
	}


	public static void userInput(String message) {
		System.out.println(message);
		Scanner in = new Scanner(System.in);
		in.nextLine();
		in.close();
	}


	public static Map<String, Double> syncParseFile(String fileName, Map<String, Double> map) {
		FilesParser fp = new FilesParser(map, fileName);
		//fp.parse(fileName);
		synchronized (fp) {
			new Thread(fp).start();
		}
		map = fp.getMap();
		return map;
	}
}
