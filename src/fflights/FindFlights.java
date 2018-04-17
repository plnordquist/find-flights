package fflights;

import java.util.List;
import static java.util.stream.Collectors.toList;

import fflights.patterns.FlightReader;
import fflights.patterns.FlightReaderFactory;
import fflights.patterns.PhotoReader;
import fflights.patterns.PhotoReaderFactory;
import fflights.util.ProgramOptions;
import fflights.vo.Flight;
import fflights.vo.Location;
import fflights.vo.Photo;


public class FindFlights {
		
	public static void main(String... args) {
		ProgramOptions programOptions = new ProgramOptions();
		if (! programOptions.parseOptions(args)) {
			System.exit(1);
	    }
		
		Location pasco = new Location("United States", "Pasco", 46.2647, -119.119);
		
	    String flightsFilename = programOptions.getFlightsFile();
		FlightReaderFactory flightReaderFactory = new FlightReaderFactory();
		FlightReader flightReader = flightReaderFactory.getFlightReader(flightsFilename);
		List<Flight> flights = flightReader.getFlights();			    
//	    for (Flight flight : flights) {
//            System.out.println("FLIGHT:" + flight.toString());
//        }	    
	    
		List<Flight> filteredFlights = flights.stream()
                .filter(t -> t.getMiles() >= programOptions.getMinMiles() && 
                             t.getMiles() <= programOptions.getMaxMiles())
                .collect(toList());
		
		int count = 1;
	    for (Flight flight : filteredFlights) {
	    	System.out.println("FLIGHT:" + count + ":" + flight.toString());
	    	++count;
	    }	 
	    
	    String photosFilename = programOptions.getPhotosFile();
		PhotoReaderFactory photoReaderFactory = new PhotoReaderFactory();
		PhotoReader photoReader = photoReaderFactory.getPhotoReader(photosFilename);
		List<Photo> photos = photoReader.getPhotos();
//		System.out.println("country,city,latitude,longitude,like,love,haha,wow,sad,angry");
//	    for (Photo photo : photos) {
//            //System.out.println(photo.toCSV());
//            System.out.println("PHOTO:" + photo.toString());
//        }
		
		List<Photo> filteredPhotos = photos.stream()
                .filter(t -> t.getMiles(pasco) >= programOptions.getMinMiles() && 
                             t.getMiles(pasco) <= programOptions.getMaxMiles() &&
                             t.getNumberReactions() >= programOptions.getMinReactions() &&
                             t.isPositive())
                .collect(toList());
		
		count = 1;
	    for (Photo photo : filteredPhotos) {
	    	System.out.println("PHOTO:" + count + ":" + photo.toString());
	    	++count;
	    }
		
	    //System.exit(0);	    

		count = 1;
	    for (Photo photo : filteredPhotos) {
	    	for (Flight flight : filteredFlights) {
	    		int distance = (int) photo.getLocation().distanceTo(flight.getLocation());
		    	if (distance < 100) {
		            System.out.println("DISTANCE:" + count + ":" + distance + ":" + photo.toString() + ":" + flight.toString());
		            ++count;
		    	}
	        }
            //System.exit(1);
        }
	    
	    System.exit(0);	    
	}
	
}