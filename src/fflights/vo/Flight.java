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
    
    public static final double MILES_PER_NAUTICAL_MILE = 1.15077945;

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
}
