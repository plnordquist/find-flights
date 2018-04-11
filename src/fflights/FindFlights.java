package fflights;

import fflights.util.ProgramOptions;

/**
 *
 */
public class FindFlights {
	
	public static void main(String... args) {
		ProgramOptions programOptions = new ProgramOptions();
		if (! programOptions.parseOptions(args)) {
			System.exit(1);
	    }
		// TODO: Start processing files and get to work
	}

}
