package edu.toronto.bb.core;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Collection;
import java.util.LinkedHashMap;

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
    
    public BarcodeBooster(File fnaIn, File fnaOut, File qualIn, File qualOut) throws FileNotFoundException {
        this.fnaIn = fnaIn;
        this.fnaOut = fnaOut;
        this.qualIn = qualIn;
        this.qualOut = qualOut;
        
//        fnaInputStream = new FileInputStream(fnaIn);
        
        initIO();        
    }
    
    private void initIO() throws FileNotFoundException {
        fastaReader = new FastaReader<DNASequence, NucleotideCompound>(fnaInputStream, 
                new GenericFastaHeaderParser<DNASequence, NucleotideCompound>(), 
                (SequenceCreatorInterface<NucleotideCompound>) new FileProxyDNASequenceCreator(fnaIn, DNACompoundSet.getDNACompoundSet()));
        
        FileOutputStream fnaOS = new FileOutputStream(fnaOut);
        BufferedOutputStream fnaBO = new BufferedOutputStream(fnaOS);
        fastaWriter = new FastaWriter<DNASequence, NucleotideCompound>(fnaBO, 
                dnaSequences.values(),
                (FastaHeaderFormatInterface<DNASequence, NucleotideCompound>) new GenericFastaHeaderParser<DNASequence, NucleotideCompound>());
        
        phredReader = new PhredReader(qualIn);
        
//        FileOutputStream fnaOS = new FileOutputStream(fnaOut);
//        BufferedOutputStream fnaBO = new BufferedOutputStream(fnaOS);
//        phredWriter = new PhredWriter(os, sequences)
        
    }
    
    public void process() {
        
    }
}
