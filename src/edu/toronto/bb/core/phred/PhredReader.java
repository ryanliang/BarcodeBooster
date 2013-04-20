package edu.toronto.bb.core.phred;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;

import org.biojava3.core.sequence.AccessionID;
import org.biojava3.core.sequence.io.BufferedReaderBytesRead;

import edu.toronto.bb.common.Constants;

public class PhredReader {
    
    BufferedReaderBytesRead  br;
    InputStreamReader isr;
    FileInputStream fis = null;

    public PhredReader(File file) throws FileNotFoundException {
        fis = new FileInputStream(file);
        isr = new InputStreamReader(fis);
        br = new BufferedReaderBytesRead(isr);
    }
    
    public PhredReader(InputStream is) {
        isr = new InputStreamReader(is);
        br = new BufferedReaderBytesRead(isr);
    }
    
    public LinkedHashMap<String, PhredScoreSequence> process() throws IOException {
        LinkedHashMap<String, PhredScoreSequence> sequences = new LinkedHashMap<String, PhredScoreSequence>();
        
        String line = "";
        String header = "";                
        StringBuilder sb = new StringBuilder();
        int maxSequenceLength = -1;
        boolean keepGoing = true;
        
        while (keepGoing) {
            line = line.trim();
            if (line.length() > 0) {
                if (line.startsWith(">")) {
                    if (sb.length() > 0) {
                        // reach header of next sequence.  process the current sequence now
                        processOneSequence(header, sb, sequences);
                        if (maxSequenceLength < sb.length()) {
                            maxSequenceLength = sb.length();
                        }
                        sb = new StringBuilder(maxSequenceLength);
                    }
                    header = line.substring(1);
                } else if (line.startsWith(";")) { // ignore comment line                    
                } else {
                    sb.append(line);
                    sb.append(Constants.SCORE_DELIMITER);
                }                    
            }
            
            line = br.readLine();
            
            if (line == null) { // last sequence in the file
                processOneSequence(header, sb, sequences);
                keepGoing = false;
            }
        }
        
        return sequences;
    }
    
    private void processOneSequence(String header, StringBuilder sequenceContents,  
            LinkedHashMap<String, PhredScoreSequence> sequences) {        
        AccessionID accessionId;
        PhredScoreSequence currSequence;
        
        accessionId = new AccessionID(header);
        currSequence = new PhredScoreSequence(sequenceContents.toString());
        currSequence.setAccessionID(accessionId);
        sequences.put(accessionId.getID(),currSequence);           
    }
}
