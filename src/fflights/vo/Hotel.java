package fflights.vo;

/*
 * CRC Responsibilities
 *   - Hold information about a Hotel at some Location
 *   - Answer questions about
 *     - Distance in meters from Location
 *     - Cost of Hotel (Values can be "", $, $$, $$$, $$$$)
 *     - Rating of Hotel (Values 0.0-5.0)
 *     - Number of Customer Reviews
 */

/*
 * DEVELOPER NOTE: __STUDENT_PUT_SOME_DOCUMENTATION_HERE__
 */
public class Hotel { 
    private Location location;
    private String name;
    private String cost;
    private double meters;
    private double rating;
    private int num_reviews;
    
    public Hotel(Location location, 
    		        String name,
    		        String cost,
    		        double meters, 
    		        double rating,
    		        int num_reviews
    		       ) {
        this.location    = location;
        this.name        = name;
        this.cost        = cost;
        this.meters      = meters;
        this.rating      = rating;
        this.num_reviews = num_reviews;
    }

	public Location getLocation() {
		return location;
	}
	
	public double getMeters() {
		return meters;
	}
	
	public double getRating() {
		return rating;
	}
	
	public String getCost() {
		return cost;
	}
	
	public String getName() {
		return name;
	}
	
	public int getNumReviews() {
		return num_reviews;
	}
	
    public String toString() {
        return "Hotel, " + name + ", at Location: " + location.getCountry() + ", " + location.getCity() + ", is " + meters + " meters away from the airport";
    }
    
    // Test client
    public static void main(String[] args) {
        // JSON: "{name":"Imperia Hotel et Suites","price":"$$","rating":3.5,"review_count":4,"location":{"city":"Saint-Eustache","country":"CA"},"coordinates":{"latitude":45.581035,"longitude":-73.882618},"distance":14726.299645023084}
        Location location = new Location("Canada", "Saint-Eustache", 45.581035, -73.882618);
        Hotel hotel = new Hotel(location, "Imperia Hotel et Suites", "$$", 14726.299645023084, 3.5, 4);
        System.out.println(hotel.toString());
    }
}
