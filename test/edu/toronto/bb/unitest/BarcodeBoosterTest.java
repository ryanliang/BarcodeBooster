package edu.toronto.bb.unitest;

import java.io.File;
import java.io.FileNotFoundException;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import edu.toronto.bb.core.BarcodeBooster;

public class BarcodeBoosterTest {
    
    private File fnaIn;
    private File fnaOut;
    private File qualIn;
    private File qualOut;
    private String currPath = "C:\\temp\\"; 
    @Before
    public void setUp() {
        fnaIn = new File(currPath + "fna");
        fnaOut = new File(currPath + "o_fna");
        qualIn = new File(currPath + "qual");
        qualOut = new File(currPath + "o_qual");
    }
    
    @Test
    public void shouldProcess() {
       try {
        BarcodeBooster bb = new BarcodeBooster(fnaIn, fnaOut, qualIn, qualOut);        
        bb.setMarker("T");
        bb.process();
    } catch (FileNotFoundException e) {
        fail();
        e.printStackTrace();
    } catch (Throwable e) {
        fail();
        e.printStackTrace();
    } 
    }
    
    

}
