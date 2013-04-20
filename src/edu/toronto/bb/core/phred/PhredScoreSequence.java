package edu.toronto.bb.core.phred;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.biojava3.core.sequence.AccessionID;
import org.biojava3.core.sequence.template.Accessioned;

import edu.toronto.bb.common.Constants;

public class PhredScoreSequence implements Iterable<String>, Accessioned {

    private String phredScores;
    private AccessionID accesionID;

    public PhredScoreSequence(String phredScores) {		
        this.phredScores = phredScores;
    }
    
    public String getScores() {
        return phredScores;
    }
   
    public void pushScore(String score) {
        String maskedScore = "";
        if (score.length() == 1) {
            maskedScore = score + " "; // append a single space for single digit score
        } else {
            maskedScore = score;
        }
        phredScores = maskedScore + Constants.SCORE_DELIMITER + phredScores;
    }

    public int length() {
        return phredScores.length();
    }
    
    public void setAccessionID(AccessionID id) {
        this.accesionID = id;
    }

    @Override
    public Iterator<String> iterator() {
        String[] rawScores = phredScores.split("\\s+");
        List<String> scoreList = new ArrayList<String>();
        scoreList.toArray(rawScores);
        
        return scoreList.iterator();
    }

    @Override
    public AccessionID getAccession() {
        return this.accesionID;
    }

}
