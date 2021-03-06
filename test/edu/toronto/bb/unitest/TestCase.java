package edu.toronto.bb.unitest;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;

import static org.junit.Assert.*;
import org.junit.Test;


import org.biojava3.core.sequence.AccessionID;
import org.biojava3.core.sequence.DNASequence;
import org.biojava3.core.sequence.io.FastaReaderHelper;
import org.biojava3.core.sequence.io.FastaWriterHelper;

public class TestCase {

    File fna;
    File fnaO;
    File qual;

    @Before
    public void setUp() {		
        fna = new File("C:\\temp\\java_projects\\BarcodeBooster\\test\\sample\\fna"); // at work
     	fnaO = new File("C:\\temp\\fnaO"); // at work
//        fna = new File("/home/ryan/java_projects/BarcodeBooster/test/sample/fna"); // at work
//        qual = new File("/home/ryan/java_projects/BarcodeBooster/test/sample/qual"); // at work
    }

    @Test    
    public void shouldReadFastaFile() {
        FileOutputStream fo = null;
        BufferedOutputStream bo = null;
        try {
            LinkedHashMap<String, DNASequence> dnaSeqMap = FastaReaderHelper.readFastaDNASequence(fna);
            Iterator<Entry<String, DNASequence>> it = dnaSeqMap.entrySet().iterator();
            
            fo = new FileOutputStream(fnaO);            
            bo = new BufferedOutputStream(fo); 
            
            while (it.hasNext()) {
                Entry<String, DNASequence> entry = (Entry<String, DNASequence>) it.next();
//                System.out.println("ID: " + entry.getKey());
//                System.out.println("AD: " + entry.getValue().getAccession().getID());
                DNASequence seq = new DNASequence(entry.getValue().toString());
                seq.setAccession(new AccessionID(entry.getKey().toString()));
                System.out.println("Sequence:");
                System.out.println(entry.getValue());  
                
                FastaWriterHelper.writeSequence(bo, seq);
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                bo.close();
                fo.close();
            } catch (IOException e) { 
                e.printStackTrace();
            }
            
        }
    }
    
    @Test 
    public void shoudLoadConfig() {
        FileInputStream f;
        try {
            f = new FileInputStream("config/barcodes");
            assertNotNull(f.read());            
        } catch (FileNotFoundException e) {
            assertFalse(true);
            e.printStackTrace();
        } catch (IOException e) {
            assertFalse(true);
            e.printStackTrace();
        }
        
        
        
    }
//    
//    @Test
//    public void shouldWriteProperties() {
//        Properties prop = new Properties();
//        
//        try {
//            //set the properties value
//            prop.setProperty("database", "localhost");
//            prop.setProperty("dbuser", "mkyong");
//            prop.setProperty("dbpassword", "password");
// 
//            //save properties to project root folder
//            prop.store(new FileOutputStream("config.properties"), null);
// 
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
//    }

}
