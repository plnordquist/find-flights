package fflights.vo;

import java.util.StringJoiner;

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
    private int    cost_len;
    private double meters;
    private double rating;
    private int    num_reviews;
    
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
        this.cost_len    = cost.length();
    }

	public Location getLocation() {
		return location;
	}
	
	public double getMeters() {
		return meters;
	}
	
	public double getMiles() {
		return meters/1609.34;
	}
	public double getRating() {
		return rating;
	}
	
	public String getCost() {
		return cost;
	}
	
	public int getCostLength() {
		return cost_len;
	}
	
	public String getName() {
		return name;
	}
	
	public int getNumReviews() {
		return num_reviews;
	}
	
    public String toString() {
        return "HOTEL:" + name + ", LOCATION: " + location.toString() + ", MILES:" + getMiles() + ", METERS:" + getMeters() + ", COST:" + getCost() + ", RATING:" + getRating() + ", REVIEWS:" + getNumReviews();
    }
    
	public String toCSV() {
		StringJoiner joiner = new StringJoiner(",");
		joiner.add(name);
		joiner.add(cost);
		joiner.add(String.valueOf(meters));
		joiner.add(String.valueOf(rating));
		joiner.add(String.valueOf(num_reviews));
		return location.toCSV() + "," + joiner.toString();
	}
	
    // Test client
    public static void main(String[] args) {
        // JSON:  { "city":         "Airport West", 
    	//          "country":      "Australia", 
    	//          "distance":     5871.404152234879, 
    	//          "latitude":     -37.71217, 
    	//          "longitude":    144.888543, 
    	//          "name":         "Skyways International Hotel Motel", 
    	//          "price":        "$$$", 
    	//          "rating":       1.5, 
    	//          "review_count": 3 }     
    	Location location = new Location("Australia", "Airport West", -37.71217, 144.888543);
        Hotel hotel = new Hotel(location, "Skyways International Hotel Motel", "$$$", 5871.404152234879, 1.5, 3);
        System.out.println(hotel.toString());
    }
}
