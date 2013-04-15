package edu.toronto.bb.unitest;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedHashMap;

import org.junit.Before;
import org.junit.Test;

import edu.toronto.bb.core.PhredReader;
import edu.toronto.bb.core.PhredScoreSequence;
import edu.toronto.bb.core.PhredWriter;

public class PhredWriterTest {

    File qual;
    File outputQual;

    @Before
    public void setUp() {       
        qual = new File("C:\\temp\\java_projects\\BarcodeBooster\\test\\sample\\qual"); // at work
        //        qual = new File("/home/ryan/java_projects/BarcodeBooster/test/sample/qual"); // at work
        outputQual = new File("C:\\temp\\out.qual"); // at work
    }

    @Test
    public void shouldWriteToOutputFile() {
        try {
            // load qual file
            PhredReader reader = new PhredReader(qual);
            LinkedHashMap<String, PhredScoreSequence> sequences  = reader.process();                        
            assertTrue(sequences.size() == 5);                        

            // write to new output file
            FileOutputStream fop = new FileOutputStream(outputQual);            
            PhredWriter pw = new PhredWriter(fop, sequences.values());
            pw.process();            
            fop.close();

            // read from the output file again to make sure it's written correctly
            reader = new PhredReader(outputQual);
            sequences  = reader.process();                        
            assertTrue(sequences.size() == 5);            

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
