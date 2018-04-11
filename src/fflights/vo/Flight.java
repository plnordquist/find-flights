package fflights.vo;

/*
 * CRC Responsibilities
 *   - Hold information about a Flight from Pasco, WA to some Location
 *   - Answer questions about
 *     - Distance in miles from Pasco, WA
 *     - Cost of Flight from Pasco, WA to some Location
 */

/*
 * DEVELOPER NOTE: __STUDENT_PUT_SOME_DOCUMENTATION_HERE__
 */
public class Flight { 
    private Location location;
    private int miles;          // NOTE: from Pasco, WA
    private int cost;           // NOTE: in US Dollars
    
    public Flight(Location location,  
    		        int miles, 
    		        int cost
    		       ) {
        this.location = location;
        this.miles    = miles;
        this.cost     = cost;
    }

	public Location getLocation() {
		return location;
	}
	
	public int getMiles() {
		return miles;
	}
	
	public int getCost() {
		return cost;
	}
	
    public String toString() {
        return "Location: " + location.getCountry() + ", " + location.getCity() + ", is " + miles + " miles away and the flight will cost $" + cost + " USD.";
    }
    
    // Test client
    public static void main(String[] args) {
        Location location = new Location("Mexico", "San Jose Del Cabo", 23.15185, -109.721044);
        Flight flight = new Flight(location, 1681, 611);
        System.out.println(flight.toString());
    }
}
