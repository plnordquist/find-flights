package fflights.util;

/*
 * CRC Responsibilities
 *   - Process command line arguments
 *   - Check to make sure values to arguments are valid
 *   - Answer questions about
 *     - Command line option values
 */

import java.io.File;
import java.util.Arrays;

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
	
	private String  flights_file_val;
	private String  photos_file_val;
	private String  hotels_file_val;
	private String  reactions_val;      // TODO: This should be an ENUM type
	private boolean reactions_type_val;
	private int     min_reactions_val;
	private int     min_miles_val;
	private int     max_miles_val;
	private int     max_meters_val;
	private int     min_reviews_val;
	private int     min_cost_val;
	private int     max_cost_val;
	private double  min_rating_val;
	private boolean verbose_val;

	private Options options      = new Options();
	private Option  help         = new Option("help",    "print this message");
	private Option  verbose      = new Option("verbose", "print out extra messages");
	private Option  flightsFile  = Option.builder()
			.required(true).hasArg(true)
            .longOpt("flights-file").type(String.class)
            .desc("read in flight data from file").build();
	private Option  photosFile   = Option.builder()
			.required(true).hasArg(true)
            .longOpt("photos-file").type(String.class)
            .desc("read in photo data from file").build();	
	private Option  hotelsFile   = Option.builder()
			.required(true).hasArg(true)
            .longOpt("hotels-file").type(String.class)
            .desc("read in hotel data from file").build();
	private Option  minReactions = Option.builder()
			.required(true).hasArg(true)
            .longOpt("min-reactions").type(Number.class)
            .desc("the minimum number of reactions for a file").build();
	private Option  reactions    = Option.builder()
			.required(true).hasArg(true)
            .longOpt("reactions").type(String.class)
            .desc("the type of reaction for a photo (value positive or negative").build();
	private Option  minMiles     = Option.builder()
			.required(true).hasArg(true)
            .longOpt("min-miles").type(Number.class)
            .desc("the minimum number of miles from default airport").build();
	private Option  maxMiles     = Option.builder()
			.required(true).hasArg(true)
            .longOpt("max-miles").type(Number.class)
            .desc("the maximum number of miles from default airport").build();
	private Option  maxMeters    = Option.builder()
			.required(true).hasArg(true)
            .longOpt("max-meters").type(Number.class)
            .desc("the maximum number of meters from the hotel to the airport").build();
	private Option  minRating    = Option.builder()
			.required(true).hasArg(true)
            .longOpt("min-rating").type(Number.class)
            .desc("the minimum rating for a hotel (double 0.0-5.0)").build();
	private Option  minReviews   = Option.builder()
			.required(true).hasArg(true)
            .longOpt("min-reviews").type(Number.class)
            .desc("the minimum number of reviews for a hotel").build();
	private Option  minCost      = Option.builder()
			.required(true).hasArg(true)
            .longOpt("min-cost").type(Number.class)
            .desc("the minimum cost for a hotel (integer 0-4)").build();
	private Option  maxCost      = Option.builder()
			.required(true).hasArg(true)
            .longOpt("max-cost").type(Number.class)
            .desc("the maximum cost for a hotel (integer 0-4)").build();

	private HelpFormatter     formatter = new HelpFormatter();
    private CommandLineParser parser    = new DefaultParser();
	
	public String  getFlightsFile()  { return flights_file_val;   }
	public String  getPhotosFile()   { return photos_file_val;    }
	public String  getHotelsFile()   { return hotels_file_val;    }
	public String  getReactions()    { return reactions_val;      }
	public boolean getReactionType() { return reactions_type_val; }
	public int     getMinReactions() { return min_reactions_val;  }
	public int     getMinMiles()     { return min_miles_val;      }
	public int     getMaxMiles()     { return max_miles_val;      }
	public int     getMaxMeters()    { return max_meters_val;     }
	public int     getMinReviews()   { return min_reviews_val;    }
	public int     getMinCost()      { return min_cost_val;       }
	public int     getMaxCost()      { return max_cost_val;       }
	public double  getMinRating()    { return min_rating_val;     }
	
	private boolean valueIntRangeCheck(String option, int value, int min, int max) {
		if (value >= min && value <= max) {
			return true;
		}
		if (value < min) {
	    	System.err.println("ERROR: " + option + ", " + value + ", is less than minimum value, " + min + ".");
		}
		if (value > max) {
	    	System.err.println("ERROR: " + option + ", " + value + ", is greater than maximum value, " + max + ".");
		}
        System.exit(1);
    	return false;
	}
	
	private boolean valueDoubleRangeCheck(String option, double value, double min, double max) {
		if (value >= min && value <= max) {
			return true;
		}
		if (value < min) {
	    	System.err.println("ERROR: " + option + ", " + value + ", is less than minimum value, " + min + ".");
		}
		if (value > max) {
	    	System.err.println("ERROR: " + option + ", " + value + ", is greater than maximum value, " + max + ".");
		}
        System.exit(1);
    	return false;
	}
	
    private boolean valueStringCheck(String option, String value, String[] choices) {
    	if (Arrays.stream(choices).anyMatch(value::equals)) {
    		return true;
    	}
    	System.err.println("ERROR: " + option + ", " + value + ", is not equal to any of the choices, " + Arrays.toString(choices) + ".");
        System.exit(1);
    	return false;
	}
	
	private boolean fileMustExist(String option, String path) {
		File file = new File(path);
		if (file.exists()) {
		  return true;
		} 
    	System.err.println("ERROR: " + option + ", " + path + ", does not exist.");
        System.exit(1);
    	return false;
	}
	
	private void verboseOut(String message) {
		if (verbose_val) {
			System.out.println(message);
		}
	}
	
	public ProgramOptions() {
		verbose_val = false;
		options.addOption(help);
		options.addOption(verbose);
		options.addOption(flightsFile);
		options.addOption(photosFile);
		options.addOption(hotelsFile);
		options.addOption(minReactions);
		options.addOption(reactions);
		options.addOption(minMiles);
		options.addOption(maxMiles);
		options.addOption(maxMeters);
		options.addOption(minRating);
		options.addOption(minReviews);
		options.addOption(minCost);
		options.addOption(maxCost);
	}
	
	public boolean parseOptions(String... args) {
		
	    try {
	        // parse the command line arguments
	        CommandLine cmdline = parser.parse(options, args );
	        if (cmdline.hasOption("help")) {
	        	formatter.printHelp("FindFlights", options);
	        }
	        if (cmdline.hasOption("verbose")) {
	        	verbose_val = true;
        		verboseOut("Arg Verbose:" + verbose_val + ":");
	        }
	        if (cmdline.hasOption("flights-file") ) {
	        	if (cmdline.getOptionValue("flights-file") != null) {
	        		flights_file_val = cmdline.getOptionValue("flights-file");
	            	verboseOut("Arg FlightFile:" + flights_file_val + ":");
	            	fileMustExist("flights-file", flights_file_val);
	        	}
	        }
	        if (cmdline.hasOption("photos-file") ) {
	        	if (cmdline.getOptionValue("photos-file") != null) {
	        		photos_file_val = cmdline.getOptionValue("photos-file");
	        		verboseOut("Arg PhotosFile:" + photos_file_val + ":");
	            	fileMustExist("photos-file", photos_file_val);
	        	}
	        }
	        if (cmdline.hasOption("hotels-file") ) {
	        	if (cmdline.getOptionValue("photos-file") != null) {
	        		hotels_file_val = cmdline.getOptionValue("hotels-file");
	        		verboseOut("Arg HotelsFile:" + hotels_file_val + ":");
	            	fileMustExist("hotels-file", hotels_file_val);
	        	}
	        }
	        if (cmdline.hasOption("min-reactions") ) {
	        	if (cmdline.getOptionValue("min-reactions") != null) {
	        		min_reactions_val = Integer.parseInt(cmdline.getOptionValue("min-reactions"));
	        		verboseOut("Arg MinReactions:" + min_reactions_val + ":");
	        		valueIntRangeCheck("min-reactions", min_reactions_val, 0, 10000);
	        	}
	        }
	        if (cmdline.hasOption("reactions") ) {
	        	if (cmdline.getOptionValue("reactions") != null) {
	        		reactions_val = cmdline.getOptionValue("reactions");
	        		verboseOut("Arg Reactions:" + reactions_val + ":");
	        		valueStringCheck("reactions", reactions_val, new String []{ "positive", "negative" });
	        		//reactions_type_val = (reactions_val.equalsIgnoreCase("positive")) ? true : false;
	        	}	        
	        }
	        if (cmdline.hasOption("min-miles") ) {
	        	if (cmdline.getOptionValue("min-miles") != null) {
	        		min_miles_val = Integer.parseInt(cmdline.getOptionValue("min-miles"));
	        		verboseOut("Arg MinMiles:" + min_miles_val + ":");
	        		valueIntRangeCheck("min-miles", min_miles_val, 0, 25000);
	        	}
	        }
	        if (cmdline.hasOption("max-miles") ) {
	        	if (cmdline.getOptionValue("max-miles") != null) {
	        		max_miles_val = Integer.parseInt(cmdline.getOptionValue("max-miles"));
	        		verboseOut("Arg MaxMiles:" + max_miles_val + ":");
	        		valueIntRangeCheck("max-miles", max_miles_val, 0, 25000);
	        	}
	        }
	        if (cmdline.hasOption("max-meters") ) {
	        	if (cmdline.getOptionValue("max-meters") != null) {
	        		max_meters_val = Integer.parseInt(cmdline.getOptionValue("max-meters"));
	        		verboseOut("Arg MaxMeters:" + max_meters_val + ":");
	        		valueIntRangeCheck("max-meters", max_meters_val, 0, 80000);
	        	}
	        }
	        if (cmdline.hasOption("min-rating") ) {
	        	if (cmdline.getOptionValue("min-rating") != null) {
	        		min_rating_val = Double.parseDouble(cmdline.getOptionValue("min-rating"));
	        		verboseOut("Arg MinRating:" + min_rating_val + ":");
	        		valueDoubleRangeCheck("min-rating", min_rating_val, 0.0d, 5.0d);
	        	}
	        }
	        if (cmdline.hasOption("min-reviews") ) {
	        	if (cmdline.getOptionValue("min-reviews") != null) {
	        		min_reviews_val = Integer.parseInt(cmdline.getOptionValue("min-reviews"));
	        		verboseOut("Arg MinReviews:" + min_reviews_val + ":");
	        		valueIntRangeCheck("min-reviews", min_reviews_val, 0, 100000);
	        	}
	        }
	        if (cmdline.hasOption("min-cost") ) {
	        	if (cmdline.getOptionValue("min-cost") != null) {
	        		min_cost_val = Integer.parseInt(cmdline.getOptionValue("min-cost"));
	        		verboseOut("Arg MinCost:" + min_cost_val + ":");
	        		valueIntRangeCheck("min-cost", min_cost_val, 0, 100000);
	        	}
	        }
	        if (cmdline.hasOption("max-cost") ) {
	        	if (cmdline.getOptionValue("max-cost") != null) {
	        		max_cost_val = Integer.parseInt(cmdline.getOptionValue("max-cost"));
	        		verboseOut("Arg MaxCost:" + max_cost_val + ":");
	        		valueIntRangeCheck("max-cost", max_cost_val, 0, 100000);
	        	}
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
	
	
	// Test client
    public static void main(String[] args) {
    	String home_path = System.getenv("HOME");
    	String data_path = home_path + "/git/find-flights/data/";
    	String[] testargs = new String[]{ 
    			"-verbose",
    			"--flights-file",  data_path + "flights.json", 
    			"--photos-file",   data_path + "facebook_photos.json", 
    			"--hotels-file",   data_path + "hotels.json",
    			"--min-reactions", "5", 
    			"--reactions",     "positive",
    			"--min-miles",     "200",
    			"--max-miles",     "5000",
    			"--max-meters",    "8000",   // Approximately 5 miles
    			"--min-rating",    "3.5",
    			"--min-reviews",   "20", 
    			"--min-cost",      "2", 
    			"--max-cost",      "3" };
    	
    	ProgramOptions programOptions = new ProgramOptions();
    	
        if (! programOptions.parseOptions(testargs)) {
	        System.err.println("Parsing failed.");
    		System.exit(1);
    	}
        
        System.out.println("Parsing succeeded.");
        System.exit(0);
    }
}