package edu.toronto.bb.core.phred;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.biojava3.core.sequence.AccessionID;
import org.biojava3.core.sequence.template.Accessioned;

public class PhredScoreSequence implements Iterable<PhredScore>, Accessioned {

    private LinkedList<PhredScore> phredScores;
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

    public void pushScore(PhredScore score) {
        phredScores.push(score);
    }

    public int length() {
        return phredScores.size();
    }

    private LinkedList<PhredScore> parsePhredScoreList(String sequence) {				
        LinkedList<PhredScore> listOfScores = new LinkedList<PhredScore>();

        Pattern pattern= Pattern.compile("[0-9]+");

        if (sequence != null) {
            Matcher matcher = pattern.matcher(sequence);
            while(matcher.find()) {
                listOfScores.add(new PhredScore(Integer.parseInt(matcher.group().intern())));;
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
