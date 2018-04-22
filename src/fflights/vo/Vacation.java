package fflights.vo;

public class Vacation {
	private int    distance_from_origin;
	private int    flight_cost;
	private int    hotel_cost_estimate;
	private int    number_hotel_reviews;
	private double distance_from_airport;
	private double hotel_rating;
	private String hotel_cost;
	private String airport_country;
	private String airport_city;
	private String hotel_country;
	private String hotel_city;
	private String hotel_name;

	public static class Builder {
        private Flight flight;
        private Hotel  hotel;
        
        public Builder() {
        }
        
        public Builder withFlight(Flight flight){
            this.flight = flight;
            return this;  //By returning the builder each time, we can create a fluent interface.
        }
        
        public Builder withHotel(Hotel hotel){
            this.hotel = hotel;
            return this;
        }
        
        public Vacation build(){
            //  Here we create the actual vacation object, which is always in a fully initialized state when it's returned.
            //  Since the builder is in the Vacation class, we can invoke its private constructor.
            Vacation vacation = new Vacation();  
        	vacation.distance_from_origin  = flight.getMiles();
        	vacation.flight_cost           = flight.getCost();
        	vacation.distance_from_airport = hotel.getMiles();
        	vacation.number_hotel_reviews  = hotel.getNumReviews();
        	vacation.hotel_name            = hotel.getName();
        	vacation.hotel_rating          = hotel.getRating();
        	vacation.hotel_cost            = hotel.getCost();
        	vacation.hotel_cost_estimate   = vacation.hotel_cost.length() * 100;
        	vacation.airport_country       = flight.getLocation().getCountry();
        	vacation.airport_city          = flight.getLocation().getCity();
        	vacation.hotel_country         = hotel.getLocation().getCountry();
        	vacation.hotel_city            = hotel.getLocation().getCity();
            return vacation;
        }
    }
    
	//Fields omitted for brevity.
    private Vacation() {
        //Constructor is now private.
    }
    
    //Getters and setters omitted for brevity.
    public String toString() {
    	String airport_location = airport_country + ", " + airport_city;
    	String hotel_location   = hotel_country   + ", " + hotel_city;
    	if (! airport_location.equalsIgnoreCase(hotel_location)) {
    		airport_location =    "Airport Location: " + airport_location + "\n\t";
    		hotel_location =      "Hotel Location:   " + hotel_location + "\n\t";
    	}
    	else {
    		airport_location =    "Location:         " + airport_location + "\n\t";
    		hotel_location   =    "";
    	}
    	String vacation         = "VACATION:\n\t" +
    	                           airport_location +
                				  "Flight Cost:      $" + flight_cost + " USD\n\t" +
                				  "Flight Distance:  " + distance_from_origin + " miles\n\t" +
                				   hotel_location +
    							  "Hotel Name:       " + hotel_name + "\n\t" +
    							  "Hotel Cost:       " + hotel_cost + "\n\t" +
    							  "Hotel Rating:     " + hotel_rating + "\n\t" +
    							  "Hotel # Reviews:  " + number_hotel_reviews + "\n\t" +
    							  "Hotel Distance:   " + String.format("%.2f", distance_from_airport) + " miles from airport\n\t";
    	return vacation;
    }
    
    public int getVacationCostEstimate() {
    	return hotel_cost_estimate + flight_cost;
    }
    
    public int getDistanceFromOrigin() {
    	return distance_from_origin;
    }
    
    public double getHotelRating() {
    	return hotel_rating;
    }
    
    public int getNumReviews() {
    	return number_hotel_reviews;
    }
}
