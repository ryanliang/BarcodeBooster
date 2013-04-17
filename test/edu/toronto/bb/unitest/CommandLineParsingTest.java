package edu.toronto.bb.unitest;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.ParseException;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import edu.toronto.bb.core.Driver;

public class CommandLineParsingTest {

    List<String> argv;
    String fnaIn = "C:\\temp\\java_projects\\BarcodeBooster\\test\\sample\\fna";
    String fnaOut = "C:\\temp\\java_projects\\BarcodeBooster\\test\\sample\\o_fna";
    String qualIn = "C:\\temp\\java_projects\\BarcodeBooster\\test\\sample\\qual";
    String qualOut = "C:\\temp\\java_projects\\BarcodeBooster\\test\\sample\\o_qual";
    String fi ="fi";
    String fo ="fo";
    String qi ="qi";
    String qo ="qo";
    
    @Before
    public void setUp() {             
        argv = new ArrayList<String>();
        argv.add("-fi");
        argv.add(fnaIn);
        argv.add("-fo");
        argv.add(fnaOut);
        argv.add("-qi");
        argv.add(qualIn);
        argv.add("-qo");
        argv.add(qualOut);        
    }

    @Test
    public void shouldParseOptionFlags() throws ParseException {
        CommandLineParser parser = new BasicParser();                
        CommandLine cmd = parser.parse(Driver.buildOptions(), argv.toArray(new String[argv.size()]));
        
        assertTrue(cmd.hasOption(fi));
        assertTrue(cmd.hasOption(fo));
        assertTrue(cmd.hasOption(qi));
        assertTrue(cmd.hasOption(qo));
    }

    @Test
    public void shouldParseArguements() throws ParseException {
        CommandLineParser parser = new BasicParser();                
        CommandLine cmd = parser.parse(Driver.buildOptions(), argv.toArray(new String[argv.size()]));
        
        if (cmd.hasOption(fi)) {            
            assertTrue(fnaIn.equals(cmd.getOptionValue(fi)));
        }
        
        if (cmd.hasOption(fo)) {
            assertTrue(fnaOut.equals(cmd.getOptionValue(fo)));
        }
        
        if (cmd.hasOption(qi)) {
            assertTrue(qualIn.equals(cmd.getOptionValue(qi)));
        }
        
        if (cmd.hasOption(qo)) {
            assertTrue(qualOut.equals(cmd.getOptionValue(qo)));
        }
    }
}
