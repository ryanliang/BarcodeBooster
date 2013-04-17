package edu.toronto.bb.core.phred;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.biojava3.core.sequence.AccessionID;
import org.biojava3.core.sequence.template.Accessioned;

public class PhredScoreSequence implements Iterable<PhredScore>, Accessioned {

    private List<PhredScore> phredScores;
    private AccessionID accesionID;
    

    public PhredScoreSequence(String sequence) {		
        this.phredScores = parsePhredScoreList(sequence);
    }

    public PhredScore scoreAt(int index) {
        return phredScores.get(index);
    }	

    public List<PhredScore> getScores() {
        return phredScores;
    }

    public void setScoreAt(PhredScore score, int index) {
        phredScores.set(index, score);
    }

    public int length() {
        return phredScores.size();
    }

    private ArrayList<PhredScore> parsePhredScoreList(String sequence) {				
        ArrayList<PhredScore> listOfScores = new ArrayList<PhredScore>();

        if (sequence != null) {
            String[] rawScores= sequence.split("\\s+");
            for (String score : rawScores) {
                listOfScores.add(new PhredScore(Integer.parseInt(score)));
            }
        }

        return listOfScores;
    }
    
    public void setAccessionID(AccessionID id) {
        this.accesionID = id;
    }

    @Override
    public Iterator<PhredScore> iterator() {		
        return phredScores.iterator();
    }

    @Override
    public AccessionID getAccession() {
        return this.accesionID;
    }

}