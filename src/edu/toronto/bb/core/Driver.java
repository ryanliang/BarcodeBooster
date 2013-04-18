package edu.toronto.bb.core;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;


public class Driver {

    // Command line options
    private static final String fi ="fi";  // FNA input
    private static final String fo ="fo";  // FNA output
    private static final String qi ="qi";  // QUAL input
    private static final String qo ="qo";  // QUAL output
    private static final String key ="k";  // QUAL output

    public static void main(String[] argv) throws ParseException {
        CommandLineParser parser = new BasicParser();
        CommandLine cmd = parser.parse(buildOptions(), argv);
        
        // Create a Barcode Booster Class to do the actual work
//        BarcodeBooster bb = new BarcodeBooster(fnaIn, fnaOut, qualIn, qualOut);
    }
    
    public static Options buildOptions() {        
         Options options = new Options();
         
         Option fnaInputFile = OptionBuilder.withArgName("file")
                                 .hasArg()
                                 .withDescription("FNA Input File")
                                 .create(fi);
         
                  
         Option fnaOutputFile = OptionBuilder.withArgName("file")
                                 .hasArg()
                                 .withDescription("FNA Output File")
                                 .create(fo);
         
         
         Option qualInputFile = OptionBuilder.withArgName("file")
                                 .hasArg()
                                 .withDescription("QUAL Input File")
                                 .create(qi);
                
         
         Option qualOutputFile = OptionBuilder.withArgName("file")
                                 .hasArg()
                                 .withDescription("QUAL Output File")
                                 .create(qo);
         
         Option markerKey = OptionBuilder.withArgName("key")
                                 .hasArg()
                                 .withDescription("Key to insert")
                                 .create(key);
         
         
         options.addOption(fnaInputFile);
         options.addOption(fnaOutputFile);
         options.addOption(qualInputFile);         
         options.addOption(qualOutputFile);         
         options.addOption(markerKey);         
         
         return options;
    }

}
