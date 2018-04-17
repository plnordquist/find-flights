package fflights.vo;

/*
 * CRC Responsibilities
 *   - Hold GPS Location information about a Country and City
 *   - Answer questions about
 *     - Distance from another GPS Location
 */

// Country Codes:
//   http://takahikokawasaki.github.io/nv-i18n/
// Calculate Distance:
//   https://stackoverflow.com/questions/3694380/calculating-distance-between-two-points-using-latitude-longitude-what-am-i-doi

/******************************************************************************
 *  Compilation:  javac Location.java
 *  Execution:    java Location
 *
 *  Immutable data type for a named location: name and 
 *  (latitude, longitude).
 *
 *  % java LocationTest
 *  172.367 miles from
 *  United States, Princeton (40.366633, 74.640832) to United States, Ithaca (42.443087, 76.488707)
 *
 ******************************************************************************/

/*
 * DEVELOPER NOTE: __STUDENT_PUT_SOME_DOCUMENTATION_HERE__
 */
public class Location { 
    private String country;
    private String city;
    private double longitude;  // NOTE: in degrees
    private double latitude;   // NOTE: in degrees
    
    public static final double MILES_PER_NAUTICAL_MILE = 1.15077945;

    public Location(String country, 
    		        String city, 
    		        double latitude, 
    		        double longitude  
    		       ) {
        this.country   = country;
        this.city      = city;
        this.latitude  = latitude;
        this.longitude = longitude;
    }

    // Calculate the distance between this location and that location
    // measured in statute miles
    public double distanceTo(Location that) {
        double lat1 = Math.toRadians(this.latitude);
        double lon1 = Math.toRadians(this.longitude);
        double lat2 = Math.toRadians(that.latitude);
        double lon2 = Math.toRadians(that.longitude);

        // Great circle distance in Radians, using law of cosines formula
        double angle = Math.acos(Math.sin(lat1) * Math.sin(lat2) +
                                 Math.cos(lat1) * Math.cos(lat2) *
                                 Math.cos(lon1 - lon2));

        // Each degree on a great circle of Earth is 60 nautical miles
        double nauticalMiles = 60 * Math.toDegrees(angle);
        double statuteMiles  = MILES_PER_NAUTICAL_MILE * nauticalMiles;
        return statuteMiles;
    }

    public String getCountry() {
    	return country;
    }
    
    public String getCity() {
    	return city;
    }
    
    public String toString() {
        return country + ", " + city + " (" + latitude + ", " + longitude + ")";
    }

	public String toCSV() {
		return country + "," + city + "," + latitude + "," + longitude;
	}

    // Test client
    public static void main(String[] args) {
        Location loc1 = new Location("United States", "Princeton", 40.366633, 74.640832);
        Location loc2 = new Location("United States", "Ithaca",    42.443087, 76.488707);
        double distance = loc1.distanceTo(loc2);
        System.out.printf("%6.3f miles from\n", distance);
        System.out.println(loc1 + " to " + loc2);
    }

}
