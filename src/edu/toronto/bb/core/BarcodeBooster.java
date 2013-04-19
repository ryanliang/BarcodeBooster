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
import org.biojava3.core.sequence.io.FastaReaderHelper;
import org.biojava3.core.sequence.io.FastaWriter;
import org.biojava3.core.sequence.io.FastaWriterHelper;
import org.biojava3.core.sequence.io.FileProxyDNASequenceCreator;
import org.biojava3.core.sequence.io.GenericFastaHeaderFormat;
import org.biojava3.core.sequence.io.GenericFastaHeaderParser;
import org.biojava3.core.sequence.io.template.FastaHeaderFormatInterface;
import org.biojava3.core.sequence.io.template.SequenceCreatorInterface;

import edu.toronto.bb.core.phred.PhredReader;
import edu.toronto.bb.core.phred.PhredScore;
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
    private BufferedOutputStream fnaBO;
    private BufferedOutputStream qualBO;

    
    private FastaWriter<DNASequence, NucleotideCompound> fastaWriter;
    private PhredReader phredReader;
    private PhredWriter phredWriter;

    private LinkedHashMap<String,DNASequence> dnaSequences;
    private LinkedHashMap<String,PhredScoreSequence> qualSequences;

    private String barcodes; // contains the CSV formats of all the barcodes
    private final String pathToBarcodes = "config/barcodes";
    private final int barcodeLength = 10;  

    private String marker = "";  // key to be inserted

    public BarcodeBooster(File fnaIn, File fnaOut, File qualIn, File qualOut) {
        this.fnaIn = fnaIn;
        this.fnaOut = fnaOut;
        this.qualIn = qualIn;
        this.qualOut = qualOut;
    }

    public void process() throws Throwable {
        if (marker.length() != 1) {
            throw new IllegalArgumentException("marker has to be exactly one character");
        }

        barcodes = loadBarcodes();

        initInputIO();
        dnaSequences =  FastaReaderHelper.readFastaDNASequence(fnaIn);        
        qualSequences = phredReader.process();

        searchForBarcodeAndInsertMarkerToSequence();

        initOutputIO();
        FastaWriterHelper.writeNucleotideSequence(fnaBO, dnaSequences.values());
        phredWriter.process();

        closeIO();
    }    

    public String getMarker() {
        return marker;
    }

    public void setMarker(String key) {
        this.marker = key;
    }

    private void searchForBarcodeAndInsertMarkerToSequence() {
        DNASequence newDNASequence;
        DNASequence currDNASequence;
        PhredScoreSequence currPhredScoreSequence;
        String currKey;

        for(Entry<String, DNASequence> entry : dnaSequences.entrySet() ) {
            currDNASequence = entry.getValue();
            currKey = entry.getKey();

            if (containsBarcode(currDNASequence.toString(), barcodes)) {
                newDNASequence = new DNASequence(marker + currDNASequence.toString());
                newDNASequence.setAccession(currDNASequence.getAccession());
                dnaSequences.put(entry.getKey(), newDNASequence);

                currPhredScoreSequence = qualSequences.get(currKey);
                if (currPhredScoreSequence == null) {
                    throw new IllegalArgumentException("No matching PhredScore Sequence with header " + entry.getKey());
                } else {
                    currPhredScoreSequence.pushScore(new PhredScore(PhredScore.MAX_SCORE));
                }
            }
        }

    }

    private boolean containsBarcode(String sequenceData, String barcodes) {
        return barcodes.indexOf(sequenceData.substring(0, barcodeLength)) > -1;
    }

    private void initInputIO() throws FileNotFoundException {        
        fnaInputStream = new FileInputStream(fnaIn);        
        qualInputStream = new FileInputStream(qualIn);        
        
        phredReader = new PhredReader(qualInputStream);
    }

    private void initOutputIO() throws FileNotFoundException {                
        fnaOutputStream = new FileOutputStream(fnaOut);
        qualOutputStream = new FileOutputStream(qualOut);

        fnaBO = new BufferedOutputStream(fnaOutputStream);
        qualBO = new BufferedOutputStream(qualOutputStream);
        phredWriter = new PhredWriter(qualBO, qualSequences.values());        
    }

    private void closeIO() throws IOException {
        fnaInputStream.close();
        qualInputStream.close();
        fnaBO.close();
        fnaOutputStream.close();        
        qualBO.close();
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
        
        br.close();
        reader.close();
        barcodeFile.close();

        return sb.toString();
    }
}
