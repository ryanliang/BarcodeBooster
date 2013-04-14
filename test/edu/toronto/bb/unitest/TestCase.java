package edu.toronto.bb.unitest;

import java.io.File;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import org.junit.Before;

import org.junit.Test;


import org.biojava3.core.sequence.DNASequence;
import org.biojava3.core.sequence.io.FastaReaderHelper;

public class TestCase {
	
	File fna;
	File qual;
	
	@Before
	public void setUp() {		
//		fna = new File("C:\\temp\\java_projects\\BarcodeBooster\\test\\sample\\fna"); // at work
		fna = new File("/home/ryan/java_projects/BarcodeBooster/test/sample/fna"); // at work
		qual = new File("/home/ryan/java_projects/BarcodeBooster/test/sample/qual"); // at work
	}
	
	@Test
	public void shouldReadFastaFile() {
		try {
			LinkedHashMap<String, DNASequence> dnaSeqMap = FastaReaderHelper.readFastaDNASequence(fna);
			Iterator<Entry<String, DNASequence>> it = dnaSeqMap.entrySet().iterator();
			
			while (it.hasNext()) {
				Entry<String, DNASequence> entry = (Entry<String, DNASequence>) it.next();
				System.out.println("ID: " + entry.getKey());
				System.out.println("Sequence:");
				System.out.println(entry.getValue());				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

}
