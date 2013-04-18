package edu.toronto.bb.core;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import org.biojava3.core.sequence.AccessionID;
import org.biojava3.core.sequence.DNASequence;
import org.biojava3.core.sequence.compound.DNACompoundSet;
import org.biojava3.core.sequence.compound.NucleotideCompound;
import org.biojava3.core.sequence.io.FastaReader;
import org.biojava3.core.sequence.io.FastaWriter;
import org.biojava3.core.sequence.io.FileProxyDNASequenceCreator;
import org.biojava3.core.sequence.io.GenericFastaHeaderParser;
import org.biojava3.core.sequence.io.template.FastaHeaderFormatInterface;
import org.biojava3.core.sequence.io.template.SequenceCreatorInterface;

import edu.toronto.bb.core.phred.PhredReader;
import edu.toronto.bb.core.phred.PhredScoreSequence;
import edu.toronto.bb.core.phred.PhredWriter;

public class BarcodeBooster {

    private File fnaIn;
    private File fnaOut;
    private File qualIn;
    private File qualOut;
    
    private InputStream fnaInputStream;    
    private InputStream qualInputStream;    
    private FileOutputStream fnaOutputStream;
    private FileOutputStream qualOutputStream;
    
    private FastaReader<DNASequence, NucleotideCompound> fastaReader;
    private FastaWriter<DNASequence, NucleotideCompound> fastaWriter;
    private PhredReader phredReader;
    private PhredWriter phredWriter;
    
    private LinkedHashMap<String,DNASequence> dnaSequences;
    private LinkedHashMap<String,PhredScoreSequence> qualSequences;
    
    private String barcodes; // contains the CSV formats of the barcode
    private final String pathToBarcodes = "config/barcodes";
    private final int barcodeLength = 10;  // length of one barcode
    
    private String key = "";  // key to be inserted

    public BarcodeBooster(File fnaIn, File fnaOut, File qualIn, File qualOut) throws FileNotFoundException {
        this.fnaIn = fnaIn;
        this.fnaOut = fnaOut;
        this.qualIn = qualIn;
        this.qualOut = qualOut;       
                
        initIO();        
    }
    
    public void process() throws Throwable {
        barcodes = loadBarcodes();
        
        dnaSequences = fastaReader.process();
        qualSequences = phredReader.process();
        
        searchForBarcodeAndInsertKeyToSequence();
        
        fastaWriter.process();
        phredWriter.process();
        
        closeIO();
    }    
    
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
    
    private void searchForBarcodeAndInsertKeyToSequence() {
        DNASequence newDNASequence;
        DNASequence currDNASequence;
        String currKey;
        for(Entry<String, DNASequence> entry : dnaSequences.entrySet() ) {
            currDNASequence = entry.getValue();
            currKey = entry.getKey();
            if (containsBarcode(currDNASequence.toString(), barcodes)) {
                 newDNASequence = new DNASequence(key + currDNASequence.toString());
                 newDNASequence.setAccession(currDNASequence.getAccession());
                 dnaSequences.put(entry.getKey(), newDNASequence);
                 
                 // handle phredscore here
            }
        }
        
    }
    
    private boolean containsBarcode(String sequenceData, String barcodes) {
        return barcodes.contains(sequenceData.substring(0, barcodeLength));
    }
        
    private void initIO() throws FileNotFoundException {        
        fnaInputStream = new FileInputStream(fnaIn);
        fnaOutputStream = new FileOutputStream(fnaOut);
        qualInputStream = new FileInputStream(qualIn);
        qualOutputStream = new FileOutputStream(qualOut);
        
        fastaReader = new FastaReader<DNASequence, NucleotideCompound>(fnaIn, 
                new GenericFastaHeaderParser<DNASequence, NucleotideCompound>(), 
                (SequenceCreatorInterface<NucleotideCompound>) new FileProxyDNASequenceCreator(fnaIn, DNACompoundSet.getDNACompoundSet()));
                
        BufferedOutputStream fnaBO = new BufferedOutputStream(fnaOutputStream);
        fastaWriter = new FastaWriter<DNASequence, NucleotideCompound>(fnaBO, 
                dnaSequences.values(),
                (FastaHeaderFormatInterface<DNASequence, NucleotideCompound>) new GenericFastaHeaderParser<DNASequence, NucleotideCompound>());
        
        phredReader = new PhredReader(qualInputStream);
        
        BufferedOutputStream qualBO = new BufferedOutputStream(qualOutputStream);
        phredWriter = new PhredWriter(qualBO, qualSequences.values());        
    }
    
    private void closeIO() throws IOException {
        fnaInputStream.close();
        fnaOutputStream.close();
        qualInputStream.close();
        qualOutputStream.close();
    }
    
    private String loadBarcodes() throws IOException {
        FileInputStream barcodeFile = new FileInputStream(pathToBarcodes);
        InputStreamReader reader = new InputStreamReader(barcodeFile);
        BufferedReader br = new BufferedReader(reader);
        
        StringBuilder sb = new StringBuilder();         
        String delimiter =",";
        String line = br.readLine();
        
        while (line != null ) {
            sb.append(line);
            sb.append(delimiter);
            line = br.readLine();
        }
        
        return sb.toString();
    }
}
