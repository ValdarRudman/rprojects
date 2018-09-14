package track;

public class Score {

	private int score = 0;
	private int minScore = 4;
	
	/*
	 * create score object with a defined min score.
	 */
	public Score() {
		
	}
	
	/*
	 * add to score
	 */
	public void addToScore(int value) {
		
		score += value;
		
	}
	
	public int getMinScore() {
		
		return this.minScore;
		
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public void setMinScore(int minScore) {
		this.minScore = minScore;
	}
	
}
