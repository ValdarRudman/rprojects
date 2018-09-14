package games;

import java.util.Random;

public class LottoCure {
	
	// The max amount of numbers that are random and that the user can guess
	private final int MAX_NUMBERS = 6;
	// Max number you can guess
	private final int MAX = 50;
	// min number you can guess
	private final int MIN = 1;
	// Mim score need to win prize
	private int minScore;
	//users score
	private int score = 0;
	
	private int[] ranNumbers = new int[MAX_NUMBERS];
	private int[] numberGuesses = new int[MAX_NUMBERS];
	private int currentIndex = 0; 
	
	/*
	 *Pass a min value which is the min value needed to get the score. Create an array with random numbers
	 */
	public LottoCure(int minScore) {
		
		this.minScore = minScore;
		
		// loops through the ranNumbers array and adds a random number.
		for(int i = 0; i < ranNumbers.length; i++) {
			
			int no = ranNumber();

			// if random number is already in array, will generate a new one till not in array
			while(checkNumber(no)) {
				
				no = ranNumber();
				
			}
			
			ranNumbers[i] = no;
			
		}
        
		/*
		 * Prints out the random numbers generated
		for(int i = 0; i < ranNumbers.length; i++) {
			
			System.out.println(ranNumbers[i]);
			
		}*/
		
	}
	
	/*
	 * Stores user guess into the numberGusses array
	 */
	public void numberGuess(int number) {
		
		if(currentIndex == 6) {
			
		}else {
			
			numberGuesses[currentIndex] = number;
			currentIndex++;
		}
		
	}
	
	/*
	 * checks to see if user has used all his guesses
	 */
	public boolean allGuesses() {
		
		if(currentIndex == MAX_NUMBERS) {
			
			return true;
			
		}
		
		return false;
		
	}
	
	/*
	 * Checks the users guesses and increments score if a number guessed in the ranNumbers array
	 */
	public boolean checkNumbers() {
		
		for(int i = 0; i < ranNumbers.length; i++) {
			
			innerloop:
			for(int j = 0; j < ranNumbers.length; j++) {
				
				
				if(numberGuesses[i] == ranNumbers[j]) {
					
					score++;
					break innerloop;
					
				}
				
			}
			
		}
		
		// if the users score is below the min score, user gets his score reset
		if(score < minScore) {
			
			score = 0;
			return false;
			
		}
		
		return true;
		
	}
	
	public int getMinScore() {
		return minScore;
	}

	public void setMinScore(int minScore) {
		this.minScore = minScore;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
	/*
	 * Checks to make sure there are no duplicates in the ranNumbers array
	 */
	private boolean checkNumber(int number) {
		
		for(int i = 0; i < this.ranNumbers.length; i ++) {
			
			if(number == this.ranNumbers[i]) {
				
				return true;
				
			}
			
		}
		
			return false;
		
	}
	
	public void resetCurrentIndex() {
		
		currentIndex = 0;
		
	}
	
	/*
	 * gets a random number between the games values
	 */
	private int ranNumber() {
		
		Random ran = new Random();
		
		return ran.nextInt(MAX) + MIN;
		
	}
	
	public int[] getRanNumbers() {
		return ranNumbers;
	}

	public void setRanNumbers(int[] ranNumbers) {
		this.ranNumbers = ranNumbers;
	}

	public int[] getNumberGuesses() {
		return numberGuesses;
	}

	public void setNumberGuesses(int[] numberGuesses) {
		this.numberGuesses = numberGuesses;
	}

	public int getCurrentIndex() {
		return currentIndex;
	}

	public void setCurrentIndex(int currentIndex) {
		this.currentIndex = currentIndex;
	}

	public int getMAX_NUMBERS() {
		return MAX_NUMBERS;
	}

	public int getMAX() {
		return MAX;
	}

	public int getMIN() {
		return MIN;
	}
	
}
