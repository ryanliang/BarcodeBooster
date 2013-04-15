package edu.toronto.bb.core;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.List;

public class PhredWriter {

    OutputStream os;
    Collection<PhredScoreSequence> sequences;
    private int scoresPerLine = 60;
    byte[] lineSep = System.getProperty("line.separator").getBytes();
    String delimiter = " "; // single space to separate the socres

    public PhredWriter(OutputStream os, Collection<PhredScoreSequence> sequences) {
        this.os = os;
        this.sequences = sequences;
    }

    public void setScorePerLine(int scorePerLine) {
        this.scoresPerLine = scorePerLine; 
    }

    /**
     * Allow to overwrite the system's line separator
     * @param newLineSeparator
     */
    public void setLineSeparator(byte[] newLineSeparator) {
        this.lineSep = newLineSeparator;
    }


    /**
     * Write the sequence to the OutputStream os
     * @throws IOException 
     */
    public void process() throws IOException {				
        StringBuilder sb = new StringBuilder();		

        for (PhredScoreSequence sequence : sequences) {			
            buildHeader(sb, sequence);
            buildSequenceContents(sb, sequence);
            os.write(sb.toString().getBytes());
        }

    }

    private void buildHeader(StringBuilder sb, PhredScoreSequence sequence) {
        sb.append(">");
        sb.append(sequence.getAccession());
        sb.append(lineSep);			
    }

    private void buildSequenceContents(StringBuilder sb, PhredScoreSequence sequence) {
        List<PhredScore> scores = sequence.getScores();
        int seqSize = scores.size();

        for (int i = 0; i < seqSize; i++) {
            if (i > 0 && i % scoresPerLine == 0) {
                sb.append(lineSep);
            } else {
                sb.append(scores.get(i).toString());
                sb.append(delimiter);
            }

        }

    }





}
