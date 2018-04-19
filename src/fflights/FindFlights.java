package fflights;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;

import fflights.patterns.FlightReader;
import fflights.patterns.FlightReaderFactory;
import fflights.patterns.HotelReader;
import fflights.patterns.HotelReaderFactory;
import fflights.patterns.PhotoReader;
import fflights.patterns.PhotoReaderFactory;
import fflights.util.ProgramOptions;
import fflights.vo.Flight;
import fflights.vo.Hotel;
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
//      }	    
	    
		List<Flight> filteredFlights = flights.stream()
                .filter(t -> t.getMiles() >= programOptions.getMinMiles() && 
                             t.getMiles() <= programOptions.getMaxMiles())
                .collect(toList());
	        
		int count = 1;
//	    for (Flight flight : filteredFlights) {
//	    	System.out.println("FLIGHT:" + count + ":" + flight.toString());
//	    	++count;
//	    }	 
	    
	    String photosFilename = programOptions.getPhotosFile();
		PhotoReaderFactory photoReaderFactory = new PhotoReaderFactory();
		PhotoReader photoReader = photoReaderFactory.getPhotoReader(photosFilename);
		List<Photo> photos = photoReader.getPhotos();
		
//		System.out.println("country,city,latitude,longitude,like,love,haha,wow,sad,angry");
//	    for (Photo photo : photos) {
//            //System.out.println(photo.toCSV());
//            System.out.println("PHOTO:" + photo.toString());
//      }		    

		List<Photo> filteredPhotos = photos.stream()
                .filter(t -> t.getMiles(pasco) >= programOptions.getMinMiles() && 
                             t.getMiles(pasco) <= programOptions.getMaxMiles() &&
                             t.getNumberReactions() >= programOptions.getMinReactions() &&
                             t.getMood().equalsIgnoreCase(programOptions.getReactions()))
                .collect(toList());
		
//		count = 1;
//	    for (Photo photo : filteredPhotos) {
//	    	System.out.println("PHOTO:" + count + ":" + photo.toString());
//	    	++count;
//	    }
		
		count = 1;
		int maxMileBetweenPhotoAndFlight = 100;
		Map<Location, Flight> uniqueFlights = new HashMap<>();
	    for (Photo photo : filteredPhotos) {
	    	for (Flight flight : filteredFlights) {
	    		int distance = (int) photo.getLocation().distanceTo(flight.getLocation());
		    	if (distance < maxMileBetweenPhotoAndFlight) {
		    		if (! uniqueFlights.containsKey(flight.getLocation())) {
		    			uniqueFlights.put(flight.getLocation(), flight);
//		                System.out.println("DISTANCE:" + count + ":" + distance + ":" + photo.toString() + " -> " + flight.toString());
		                ++count;
		    		}
		    	}
	        }
        }
	    
	    String hotelsFilename = programOptions.getHotelsFile();
		HotelReaderFactory hotelReaderFactory = new HotelReaderFactory();
		HotelReader hotelReader = hotelReaderFactory.getHotelReader(hotelsFilename);
		List<Hotel> hotels = hotelReader.getHotels();
		
//		System.out.println("country,city,latitude,longitude,name,cost,meters,rating,num_reviews");
//	    for (Hotel hotel : hotels) {
//	    	//System.out.println(hotel.toCSV());
//	    	System.out.println("HOTEL:" + hotel.toString());
//	    }
	    
		List<Hotel> filteredHotels = hotels.stream()
                .filter(t -> t.getRating() >= programOptions.getMinRating() && 
                             t.getNumReviews() >= programOptions.getMinReviews() &&
                             t.getCostLength() >= programOptions.getMinCost() &&
                             t.getCostLength() <= programOptions.getMaxCost())
                .collect(toList());
		
//	    for (Hotel hotel : filteredHotels) {
//	    	System.out.println("HOTEL:" + hotel.toString());
//	    }
		
		int maxMiles = (int) (programOptions.getMaxMeters() / 1609.34);
		Map<Flight, List<Hotel>> vacations = new HashMap<>();
    	for (Flight flight : uniqueFlights.values()) {
    		List<Hotel> vacation_hotels = new ArrayList<>();
  	        for (Hotel hotel : filteredHotels) {
	    		int miles = (int) (flight.getLocation().distanceTo(hotel.getLocation()));
		    	if (miles <= maxMiles) {
		    		vacation_hotels.add(hotel);
		    	}
  	        }
  	        if (! vacation_hotels.isEmpty()) {
  	        	vacations.put(flight, vacation_hotels);
  	        }
    	}
    	
    	for (Flight vacation : vacations.keySet()) {
    		System.out.println("FLIGHT:" + vacation.toString());
    		for (Hotel hotel : vacations.get(vacation)) {
        		System.out.println("  HOTEL:" + hotel.toString());
    		}
    	}
	    
	    System.exit(0);	    
	}
	
}