package edu.toronto.bb.core;

import java.io.File;
import java.io.FileNotFoundException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;


public class Driver {

    private static final String bb = "bb"; // main cmd
    
    // Command line options
    private static final String fi = "fi";  // FNA input
    private static final String fo = "fo";  // FNA output
    private static final String qi = "qi";  // QUAL input
    private static final String qo = "qo";  // QUAL output
    private static final String key ="k";  // QUAL output
    
    private static CommandLine cmd;
    private static Options options;

    public static void main(String[] argv) throws ParseException {
        CommandLineParser parser = new BasicParser();
        cmd = parser.parse(buildOptions(), argv);
        
        File fnaIn, fnaOut, qualIn, qualOut;
        
        if (isUserOptionValid()) {
            try {
                fnaIn   = new File(cmd.getOptionValue(fi));
                fnaOut  = new File(cmd.getOptionValue(fo));
                qualIn  = new File(cmd.getOptionValue(qi));
                qualOut = new File(cmd.getOptionValue(qo));
                BarcodeBooster bb = new BarcodeBooster(fnaIn, fnaOut, qualIn, qualOut);
                bb.setMarker(cmd.getOptionValue(key));
                bb.process();
            } catch (FileNotFoundException e) {
                System.err.println("File not found: " + e.getMessage());                
            } catch (Throwable e) {
                e.printStackTrace();
            }  
        } else {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("bb", options);
        }        
    }
    
    private static boolean isUserOptionValid() {
        return  cmd.hasOption(fi)  &&
                cmd.hasOption(fo)  &&
                cmd.hasOption(qi)  &&
                cmd.hasOption(qo)  &&
                cmd.hasOption(key) &&
                key.length() == 1;
    }
    
    public static Options buildOptions() {        
         options = new Options();
         
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
