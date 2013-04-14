package edu.toronto.bb.unitest;

import java.util.Iterator;

import static org.junit.Assert.*;
import org.junit.Before;

import org.junit.Test;

import edu.toronto.bb.core.PhredScore;
import edu.toronto.bb.core.PhredScoreSequence;

public class TestPhred {


    String phredStr;

    @Before
    public void setUp() {		
        phredStr = "37 37 37 37 37 37 37 37 37 37 37 40 39 39 39 40 40 40 40 40 40 40 40 40 40 40 40 40 40 39 39 39 39 39 39 40 40 40 40 40 40 40 40 40 40 40 40 40 40 40 40 40 37 37 37 37 37 37 37 37" +
                "\r\n" +
                "37 37 37 37 37 37 37 37 37 37 37 37 37 37 37 37 37 37 37 37 37 37 37 37 37 37 37 37 37 37 37 37 37 37 37 35 35 35 37 37 37 37 37 37 37 37 37 37 37 37 37 37 37 37 37 37 37 37 37 37" +
                "\r\n" +
                "37 37 37 37 37 37 37 37 37 37 37 37 37 37 37 35 33 33 32 30 26 27 12 11 11 11 11 0  11 11 11 11 11 18 11 11 11 12 12 13 16 16 16 18 13 18 18 20 22 16 16 16 16 16 16 18 16 16 18 11" +
                "\r\n" +
                "12 13 22 17 18 19 19 23 27 30 28 30 28 21 21 21 19 " +
                "\r\n" ;  // 197 scores
    }

    @Test
    public void shouldReadPhredSequence() {
        PhredScoreSequence pss = new PhredScoreSequence(phredStr);		
        Iterator<PhredScore> itr = pss.iterator();
        StringBuffer sb = new StringBuffer();
        while (itr.hasNext()) {
            sb.append(itr.next());
        }

        assertTrue(sb.length() > 0);

    }

    @Test
    public void shouldGetAllScores() {
        PhredScoreSequence pss = new PhredScoreSequence(phredStr);		
        assertEquals(197, pss.getScores().size());
    }

    @Test
    public void shouldGetScoreAtPosition() {
        PhredScoreSequence pss = new PhredScoreSequence(phredStr);		
        assertEquals(37, pss.scoreAt(0).getScoreValue());
        assertEquals(19, pss.scoreAt(pss.getScores().size() - 1).getScoreValue());
        assertEquals(40, pss.scoreAt(11).getScoreValue());
    }

    @Test
    public void shouldSetScoreAtPosition() {
        PhredScoreSequence pss = new PhredScoreSequence(phredStr);

        pss.setScoreAt(new PhredScore(7), 0);
        assertEquals(7, pss.scoreAt(0).getScoreValue());

        pss.setScoreAt(new PhredScore(7), 196);
        assertEquals(7, pss.scoreAt(pss.getScores().size() - 1).getScoreValue());

        pss.setScoreAt(new PhredScore(7), 12);
        assertEquals(7, pss.scoreAt(12).getScoreValue());
    }

}
