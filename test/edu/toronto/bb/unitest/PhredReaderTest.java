package edu.toronto.bb.unitest;

import static org.junit.Assert.*;
import edu.toronto.bb.core.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import org.junit.Before;

import org.junit.Test;


public class PhredReaderTest {
    
    File qual;

    @Before
    public void setUp() {       
        //      fna = new File("C:\\temp\\java_projects\\BarcodeBooster\\test\\sample\\fna"); // at work
        qual = new File("/home/ryan/java_projects/BarcodeBooster/test/sample/qual"); // at work
    }
    
    @Test
    public void shouldReadPhredFile() {
        try {
            PhredReader reader = new PhredReader(qual);
            LinkedHashMap<String, PhredScoreSequence> sequences  = reader.process();            
            
            assertTrue(sequences.size() == 5);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void shouldReadFirstSequence() {
        try {
            PhredReader reader = new PhredReader(qual);
            LinkedHashMap<String, PhredScoreSequence> sequences  = reader.process();
            String key = "HY3YUE404H86YW rank=0000003 x=3268.0 y=246.5 length=197";
            PhredScoreSequence ps = sequences.get(key);
            assertTrue(ps.getAccession().getID().equalsIgnoreCase(key));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void shouldReadLastSequence() {
        try {
            PhredReader reader = new PhredReader(qual);
            LinkedHashMap<String, PhredScoreSequence> sequences  = reader.process();
            String key = "HY3YUE404H2ITV rank=0000008 x=3192.0 y=321.0 length=462";
            PhredScoreSequence ps = sequences.get(key);
            assertTrue(ps.getAccession().getID().equalsIgnoreCase(key));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    


}
