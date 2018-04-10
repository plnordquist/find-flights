package fflights.util;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

// NOTE: https://commons.apache.org/proper/commons-cli/usage.html
// NOTE: https://commons.apache.org/proper/commons-cli/javadocs/api-release/index.html

public class ProgramOptions {
	private Options options = new Options();
	private Option help = new Option("help", "print this message");
	private Option flightFile = Option.builder()
            .required(true)
            .longOpt("flight-file")
            .desc("read in flight data from file")
            .hasArg(true)
            .build();
	private HelpFormatter formatter = new HelpFormatter();
    private CommandLineParser parser = new DefaultParser();
	
	public ProgramOptions() {				
		options.addOption(help);
		options.addOption(flightFile);
	}
	
	public boolean parseOptions(String... args) {
	    try {
	        // parse the command line arguments
	        CommandLine line = parser.parse(options, args );
	        if (line.hasOption("help")) {
	        	formatter.printHelp("FindFlights", options);
	        }
	        if (line.hasOption("flight-file") ) {
	            System.out.println("FlightFile:" + line.getOptionValue("flight-file") + ":");
	        }
	    }
	    catch(ParseException exp) {
	        // oops, something went wrong
	    	formatter.printHelp("FindFlights", options);
	        System.err.println("Parsing failed.  Reason: " + exp.getMessage());
	        return false;
	    }
	    return true;
	}
}