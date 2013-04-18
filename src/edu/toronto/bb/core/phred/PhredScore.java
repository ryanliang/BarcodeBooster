package edu.toronto.bb.core.phred;

public class PhredScore {

    public static final int MAX_SCORE = 40;
    
    int score;

    public PhredScore(int score) {		
        if (score > MAX_SCORE) {
            throw new IllegalArgumentException("Maximum score allowed is " + MAX_SCORE);
        }

        this.score = score;
    }

    public int getScoreValue() {
        return score;
    }	

    public String toString() {
        String result = "";
        if (score < 10) {
            result = Integer.toString(score) + " "; // append a space for single digit score 
        } else {
            result = Integer.toString(score);
        }

        return result;
    }
}
