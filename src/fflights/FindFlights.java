package fflights;

import java.util.List;

import fflights.patterns.FlightReader;
import fflights.patterns.FlightReaderFactory;
import fflights.patterns.PhotoReader;
import fflights.patterns.PhotoReaderFactory;
import fflights.util.ProgramOptions;
import fflights.vo.Flight;
import fflights.vo.Photo;


public class FindFlights {
		
	public static void main(String... args) {
		ProgramOptions programOptions = new ProgramOptions();
		if (! programOptions.parseOptions(args)) {
			System.exit(1);
	    }
			    
	    String flightsFilename = programOptions.getFlightsFile();
		FlightReaderFactory flightReaderFactory = new FlightReaderFactory();
		FlightReader flightReader = flightReaderFactory.getFlightReader(flightsFilename);
		List<Flight> flights = flightReader.getFlights();			    
	    for (Flight flight : flights) {
            System.out.println("FLIGHT:" + flight.toString());
        }	    
	    
	    String photosFilename = programOptions.getPhotosFile();
		PhotoReaderFactory photoReaderFactory = new PhotoReaderFactory();
		PhotoReader photoReader = photoReaderFactory.getPhotoReader(photosFilename);
		List<Photo> photos = photoReader.getPhotos();
		//System.out.println("country,city,latitude,longitude,like,love,haha,wow,sad,angry");
	    for (Photo photo : photos) {
            //System.out.println(photo.toCSV());
            System.out.println("PHOTO:" + photo.toString());
        }
	    
	    System.exit(0);	    
	}
	
}