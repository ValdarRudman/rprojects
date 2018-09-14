package games;

import java.util.Random;

public class GuessNumber {
	
	private int ranNumber;
	// Amount of attempts the user has
	private final int ATTEMPTS = 6;
	// Current number of attempts
	private int noOfAttempts = 0;
	// Users score. Will only ever be 2 values. 0 or min score
	private int score = 0;
	private int minScore;
	
	// Min and Max values for the game
	private int minValue = 1;
	private int maxValue = 100;
	
	/*
	 * Pass a min score that will be that max score the user can get
	 */
	public GuessNumber(int minScore) {
		
		this.minScore = minScore;
		
		// genetrate a random number between min and max value
		Random ran = new Random();
        ranNumber = ran.nextInt(this.maxValue) + this.minValue;
        
        /*
         * Prints out the random number
        System.out.println("Random number is " + this.ranNumber);*/
		
	}
	
	/*
	 * Increments number of attempts
	 */
	public void incrementAttempt() {
		
		this.noOfAttempts++;
		
	}
	
	/*
	 * Checks user guess to see if it is equal to the random number. Returns true if so
	 * @param number
	 * @return
	 */
	public boolean checkGuess(int number) {
		
		this.noOfAttempts++;
		
		if(number == this.ranNumber) {
			
			score = this.minScore;
			
			return true;
			
		}
		
		return false;
	}
	
	// Checks to see if user has used all their attempts
	public boolean checkAttempts() {
	
		if(this.noOfAttempts == this.ATTEMPTS) {
			
			return true;
			
		}
		
		return false;
		
	}
	
	/*
	 * Checks if number is higher or lower than random number. Returns String. "Lower" , "Higher"
	 */
	public String checkHighOrLow(int number) {
		
		if(number > this.ranNumber) {
			
			return "Lower";
			
		}
		
		return "Higher";
		
	}
	
	public int getRanNumber() {
		return ranNumber;
	}

	public void setRanNumber(int ranNumber) {
		this.ranNumber = ranNumber;
	}

	public int getNoOfAttempts() {
		return noOfAttempts;
	}

	public void setNoOfAttempts(int noOfAttempts) {
		this.noOfAttempts = noOfAttempts;
	}

	public int getMinScore() {
		return minScore;
	}

	public void setMinScore(int minScore) {
		this.minScore = minScore;
	}

	public int getMinValue() {
		return minValue;
	}

	public void setMinValue(int minValue) {
		this.minValue = minValue;
	}

	public int getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(int maxValue) {
		this.maxValue = maxValue;
	}

	public int getATTEMPTS() {
		return ATTEMPTS;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getScore() {
		
		return score;
		
	}
	
}
