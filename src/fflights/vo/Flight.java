package fflights.vo;

import java.util.StringJoiner;

/*
 * CRC Responsibilities
 *   - Hold information about a Flight from Pasco, WA to some Location
 *   - Answer questions about
 *     - Distance in miles from Pasco, WA
 *     - Cost of Flight from Pasco, WA to some Location
 *   - Be able to print the Flight data out in the formats:
 *     - String
 *     - CSV
 *     - JSON
 */

/*
 * DEVELOPER NOTE: __STUDENT_PUT_SOME_DOCUMENTATION_HERE__
 */
public class Flight {
    private Location location;
    private int      airport_id;
    private String   name;
    private String   iata_faa;
    private String   iaco;
    private int      altitude;
    private double   zone;
    private String   dst;
    private int      miles;          // NOTE: from Pasco, WA
    private int      cost;           // NOTE: in US Dollars

    public Flight(Location location,
                    int    airport_id,
                    String name,
                    String iata_faa,
                    String iaco,
                    int    altitude,
                    double zone,
                    String dst,
                    int    miles,
                    int    cost
                   ) {
        this.location   = location;
        this.airport_id = airport_id;
        this.name       = name;
        this.iata_faa   = iata_faa;
        this.iaco       = iaco;
        this.altitude   = altitude;
        this.zone       = zone;
        this.dst        = dst;
        this.miles      = miles;
        this.cost       = cost;
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

    public static String CSVHeader() {
        return "airport_id,name,city,country,iata_faa,iaco,latitude,longitude,altitude,zone,dst,miles,cost";
    }

    public String toCSV() {
        StringJoiner joiner = new StringJoiner(",");
        joiner.add(String.valueOf(airport_id));
        joiner.add(name);
        joiner.add(location.getCity());
        joiner.add(location.getCountry());
        joiner.add(iata_faa);
        joiner.add(iaco);
        joiner.add(String.valueOf(location.getLatitude()));
        joiner.add(String.valueOf(location.getLongitude()));
        joiner.add(String.valueOf(altitude));
        joiner.add(String.valueOf(zone));
        joiner.add(dst);
        joiner.add(String.valueOf(miles));
        joiner.add(String.valueOf(cost));
        return joiner.toString();
    }

    public String toJSON() {
        String spaces = "    ";
        StringJoiner joiner = new StringJoiner(",\n" + spaces);
        joiner.add("\"airport_id\": " + airport_id);
        joiner.add("\"name\": \"" + name + "\"");
        joiner.add("\"city\": \"" + location.getCity() + "\"");
        joiner.add("\"country\": \"" + location.getCountry() + "\"");
        joiner.add("\"iata_faa\": \"" + iata_faa + "\"");
        joiner.add("\"iaco\": \"" + iaco + "\"");
        joiner.add("\"latitude\": " + location.getLatitude());
        joiner.add("\"longitude\": " + location.getLongitude());
        joiner.add("\"altitude\": " + altitude);
        joiner.add("\"zone\": " + zone);
        joiner.add("\"dst\": \"" + dst + "\"");
        joiner.add("\"miles\": " + miles);
        joiner.add("\"cost\": " + cost);
        return "{\n" + spaces + joiner.toString() + "\n}";
    }

    // Test client
    public static void main(String[] args) {
        //  JSON:
        //        { "airport_id": 93,
        //            "name": "Montreal Intl Mirabel",
        //            "city": "Montreal",
        //            "country": "Canada",
        //            "iata_faa": "YMX",
        //            "iaco": "CYMX",
        //            "latitude": 45.681944,
        //            "longitude": -74.005278,
        //            "altitude": 270,
        //            "zone": -5,
        //            "dst": "A",
        //            "miles": 2137,
        //            "cost": 718 },
        //  CSV:
        //93,Montreal Intl Mirabel,Montreal,Canada,YMX,CYMX,45.681944,-74.005278,270,-5,A,2137,718
        Location location = new Location("Canada", "Montreal", 45.681944, -74.005278);
        Flight flight = new Flight(location, 93, "Montreal Intl Mirabel", "YMX", "CYMX", 270, -5, "A", 2137, 718);
        System.out.println(flight.toString());
        System.out.println(Flight.CSVHeader());
        System.out.println(flight.toCSV());
        System.out.println(flight.toJSON());
    }
}