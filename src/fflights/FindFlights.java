package fflights;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Collections;

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
import fflights.vo.Vacation;


public class FindFlights {
	//
	// NOTE: Assumptions:
	//   - All photo data is from Facebook API:
	//       - https://developers.facebook.com/tools/explorer/?method=GET \
	//         &version=v2.12&path=me/albums?fields=photos.limit(100){place,reactions}
	//   - The flight data is a combination of data from: 
	//       - https://openflights.org/data.html
	//     and reducing the number of airports with the Country information
	//     from the photos to keep the list smaller.
	//   - The Latitude/Longitude Java code is based on: 
	//       - https://stackoverflow.com/questions/3694380/ \
	//         calculating-distance-between-two-points-using-latitude-longitude-what-am-i-doi
	//   - The hotel data is limited to 5 businesses by each airport location using
	//     the Yelp API:
	//       - https://api.yelp.com/v3/graphql
	//           data = """{  
	//                       search(term:      "hotel", 
	//                              limit:     5, 
	//                              sort_by:   "review_count",
	//				                latitude:  """ + latitude + """,
	//				                longitude: """ + longitude + """) 
	//		                 {  
	//                         business 
	//                         {
	//					         name
	//					         price
	//					         rating
	//					         review_count
	//					         location 
	//                           {
	//					           city
	//					           country
	//					         }
	//					         coordinates 
	//                           {
	//					           latitude
	//					           longitude
	//					         }
	//					         distance
	//					       }
	//				         }
	//				       }"""
	
	
	public static void main(String... args) {
		//
		// NOTE: Check all the command line arguments and unless all arguments
		//       are valid, then exit.
		ProgramOptions programOptions = new ProgramOptions();
		if (! programOptions.parseOptions(args)) {
			System.exit(1);
	    }
			
        // NOTE: All the data was generated using Pasco, WA, USA as the location
		Location origin = new Location("United States", "Pasco", 46.2647, -119.119);
		
        // NOTE: Just a default counter for debugging - do NOT comment out!
		int count = 1;   
		
		// NOTE: Read in all the flight data using an Abstract Factory Pattern.
		//       The data can be read in JSON or CSV format.
	    String flightsFilename = programOptions.getFlightsFile();
		FlightReaderFactory flightReaderFactory = new FlightReaderFactory();
		FlightReader flightReader = flightReaderFactory.getFlightReader(flightsFilename);
		List<Flight> flights = flightReader.getFlights();

//		DEVELOPER NOTE: Uncomment the following to debug the flight data
//      count = 1;
//	    for (Flight flight : flights) {
//            System.out.println("FLIGHT:" + count + ":"  + flight.toString());
//            ++count;
//      }	        
		
		// NOTE: Use the Java 8 Stream API to exclude flight data that
		//       does not match the criteria passed in on the command
		//       line. This is an easier way to implement the Filter
		//       Design Pattern.
		List<Flight> filteredFlights = flights.stream()
			.filter(t -> t.getMiles() >= programOptions.getMinMiles() && 
                         t.getMiles() <= programOptions.getMaxMiles())
            .collect(toList());
	        		
//		DEVELOPER NOTE: Uncomment the following lines to debug the filtered 
//		                flight data
//      count = 1;
//	    for (Flight flight : filteredFlights) {
//	    	System.out.println("FILTERFLIGHT:" + count + ":" + flight.toString());
//	    	++count;
//	    }	 
	    
		// NOTE: Read in all the photo data using an Abstract Factory Pattern.
		//       The data can be read in JSON or CSV format.
	    String photosFilename = programOptions.getPhotosFile();
		PhotoReaderFactory photoReaderFactory = new PhotoReaderFactory();
		PhotoReader photoReader = photoReaderFactory.getPhotoReader(photosFilename);
		List<Photo> photos = photoReader.getPhotos();
		
//      DEVELOPER NOTE: The following code was used to read in the photo data 
//                      in JSON format and create the data in CSV format.
//		System.out.println("country,city,latitude,longitude,like,love,haha,wow,sad,angry");
//	    for (Photo photo : photos) {
//            System.out.println(photo.toCSV());
//      }	
		
//		DEVELOPER NOTE: Uncomment the following lines to debug the photo data
//      count = 1;		
//	    for (Photo photo : photos) {
//      	System.out.println("PHOTO:" + count + ":" + photo.toString());
//    	    ++count;
//		}

		// NOTE: Use the Java 8 Stream API to exclude photo data that
		//       does not match the criteria passed in on the command
		//       line. This is an easier way to implement the Filter
		//       Design Pattern.
		List<Photo> filteredPhotos = photos.stream()
            .filter(t -> t.getMiles(origin)      >= programOptions.getMinMiles()     && 
                         t.getMiles(origin)      <= programOptions.getMaxMiles()     &&
                         t.getNumberReactions() >= programOptions.getMinReactions() &&
                         t.getMood().equalsIgnoreCase(programOptions.getReactions()))
            .collect(toList());

//		DEVELOPER NOTE: Uncomment the following lines to debug the filtered
//		                photo data
//		count = 1;
//	    for (Photo photo : filteredPhotos) {
//	    	System.out.println("FILTERPHOTO:" + count + ":" + photo.toString());
//	    	++count;
//	    }
		
		// DEVELOPER NOTE: We need to iterate over the filtered photos and the
		//		           filtered flights and keep track of the Location and
		//		           unique Flight data where the flight is close to where
		//                 the photos were taken.
		// TODO: __DEVELOPER_NAME__: April 23, 2018: 
		// 	     There should be a cleaner way to do this but it is fast enough for now.
		count = 1;
		int maxMilesBetweenPhotoAndFlight = 100; 
		Map<Location, Flight> uniqueFlights = new HashMap<>();
	    for (Photo photo : filteredPhotos) {
	    	for (Flight flight : filteredFlights) {
	    		int distance = (int) photo.getLocation().distanceTo(flight.getLocation());
		    	if (distance < maxMilesBetweenPhotoAndFlight) {
		    		if (! uniqueFlights.containsKey(flight.getLocation())) {
		    			uniqueFlights.put(flight.getLocation(), flight);
//		DEVELOPER NOTE: Uncomment the following lines to debug the distance, photo, and flight		    			                
//		                System.out.println("DISTANCE:" + count + ":" + distance + ":" + photo.toString() + " -> " + flight.toString());
		                ++count;
		    		}
		    	}
	        }
        }
	    
		// NOTE: Read in all the hotel data using an Abstract Factory Pattern.
		//       The data can be read in JSON or CSV format.
	    String hotelsFilename = programOptions.getHotelsFile();
		HotelReaderFactory hotelReaderFactory = new HotelReaderFactory();
		HotelReader hotelReader = hotelReaderFactory.getHotelReader(hotelsFilename);
		List<Hotel> hotels = hotelReader.getHotels();
		
//      DEVELOPER NOTE: The following code was used to read in the hotel data 
//                      in JSON format and create the data in CSV format
//		System.out.println("country,city,latitude,longitude,name,price,meters,rating,review_count");
//	    for (Hotel hotel : hotels) {
//	    	System.out.println(hotel.toCSV());
//	    }
	    
//		DEVELOPER NOTE: Uncomment the following lines to debug the hotel data
//      count = 1;		
//	    for (Hotel hotel : hotels) {
//      	System.out.println("HOTEL:" + count + ":" + hotel.toString());
//    	    ++count;
//		}
		
		// NOTE: Use the Java 8 Stream API to exclude hotel data that
		//       does not match the criteria passed in on the command
		//       line. This is an easier way to implement the Filter
		//       Design Pattern.
		List<Hotel> filteredHotels = hotels.stream()
            .filter(t -> t.getRating()     >= programOptions.getMinRating()  && 
                         t.getNumReviews() >= programOptions.getMinReviews() &&
                         t.getCostLength() >= programOptions.getMinCost()    &&
                         t.getCostLength() <= programOptions.getMaxCost())
            .collect(toList());

//		DEVELOPER NOTE: Uncomment the following lines to debug the filtered
//                      hotel data.
//      count = 1;
//	    for (Hotel hotel : filteredHotels) {
//	    	System.out.println("FILTERHOTEL:" + count + ":" + hotel.toString());
//          ++count;
//	    }
		
		// DEVELOPER NOTE: We need to iterate over the unique flights and the
		//                 filtered hotels and keep track of the Flight data 
		//                 and Hotel data to create options for Vacations.
		// TODO: __DEVELOPER_NAME__: April 23, 2018: 
		//       There should be a cleaner way to do this but it is fast enough for now.
		// NOTE: We need to convert the distance from the Hotel to the Flight from
		//       meters to miles by dividing the meters by 1609.34.
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
  	        // NOTE: Only keep vacation options if there is a flight AND a hotel.
  	        if (! vacation_hotels.isEmpty()) {
  	        	vacations.put(flight, vacation_hotels);
  	        }
    	}
    	
    	// DEVELOPER NOTE: We need to iterate over the flights and hotels to
    	//                 create a Vacation using the Builder Design Pattern.
		List<Vacation> vacationOptions = new ArrayList<>();
    	for (Flight flight : vacations.keySet()) {
    		for (Hotel hotel : vacations.get(flight)) {
        		Vacation vacation = new Vacation.Builder()
        	            .withFlight(flight)
        	            .withHotel(hotel)
        	            .build();
        		vacationOptions.add(vacation);
    		}
    	}
    	
		// NOTE: Sort Vacation Options by:
    	//       - Estimated costs (flight cost + hotel cost)
    	//       - Distance from "origin" location
    	//       - Hotel rating
    	//       - Number of reviews
		Collections.sort(vacationOptions);
		
//		DEVELOPER NOTE: Uncomment the following lines to debug the vacation data
//		count = 1;
//	    for (Vacation vacation : vacationOptions) {
//	    	System.out.println("VACATION: " + count + ":" + vacation.toString());
//    		++count;
//	    }
//		System.out.println("");
		
		// DEVELOPER NOTE: All we have to do now is print out the results in CSV format.
	    System.out.println(Vacation.CSVHeader());
		for (Vacation vacation : vacationOptions) {
	    	System.out.println(vacation.toCSV());
	    }
	    
	    System.exit(0);	    
	}
}