package fflights.vo;

import java.util.StringJoiner;

/*
 * CRC Responsibilities
 *   - Hold information about a Vacation (Flight and Hotel)
 *   - Answer questions about
 *     - Be able to compare a Vacation against another Vacation
 *   - Be able to print the Vacation data out in the formats:
 *     - String
 *     - CSV
 *     - JSON
 */

// NOTE: The Vacation object is an example of the Builder Design Pattern
//       similar to the implementation at:
//         - https://dzone.com/articles/design-patterns-the-builder-pattern

public class Vacation implements Comparable<Vacation> {
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

        public Builder withFlight(Flight flight) {
            this.flight = flight;
            // NOTE: By returning the builder each time, we can create a fluent interface.
            return this;
        }

        public Builder withHotel(Hotel hotel) {
            this.hotel = hotel;
            return this;
        }

        public Vacation build() {
            //  Here we create the actual vacation object, which is always in a
            //  fully initialized state when it's returned.
            //  Since the builder is in the Vacation class, we can invoke its
            //  private constructor.
            Vacation vacation = new Vacation();
            vacation.distance_from_origin  = flight.getMiles();
            vacation.flight_cost           = flight.getCost();
            vacation.distance_from_airport = hotel.getMiles();
            vacation.number_hotel_reviews  = hotel.getNumReviews();
            vacation.hotel_name            = hotel.getName();
            vacation.hotel_rating          = hotel.getRating();
            vacation.hotel_cost            = hotel.getCost();
            //
            // DEVELOPER NOTE: The hotel_cost_estimate is a heuristic that assumes
            //                 each '$' in a Yelp rating is roughly 100 US dollars
            vacation.hotel_cost_estimate   = vacation.hotel_cost.length() * 100;
            vacation.airport_country       = flight.getLocation().getCountry();
            vacation.airport_city          = flight.getLocation().getCity();
            vacation.hotel_country         = hotel.getLocation().getCountry();
            vacation.hotel_city            = hotel.getLocation().getCity();
            //
            // DEVELOPER NOTE: If the hotel_cost is "", the hotel_cost_estimate
            //                 would be $0 which is artificially low. Let's
            //                 check and if the value is 0, let's artificially
            //                 inflate the cost to the maximum value of $500.
            vacation.hotel_cost_estimate   = (vacation.hotel_cost_estimate > 0) ? vacation.hotel_cost_estimate : 500;
            return vacation;
        }
    }

    private Vacation() {
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

    @Override
    public int compareTo(Vacation other) {
        //
        //  NOTE: This method is used for sorting collections of Vacations
        //        The comparison is prioritized by:
        //        1. cost (flight + estimated hotel price)
        //        2. distance we have to fly
        //        3. hotel rating
        //        4. number of reviews

        //  NOTE: Lower cost is better
        int cost = getVacationCostEstimate() - other.getVacationCostEstimate();
        if (cost > 0) {
            return 1;
        }
        if (cost < 0) {
            return -1;
        }

        //  NOTE: Lower distance is better
        int distance = getDistanceFromOrigin() - other.getDistanceFromOrigin();
        if (distance > 0) {
            return 1;
        }
        if (distance < 0) {
            return -1;
        }

        //  NOTE: Higher rating is better
        int rating = (int) ((getHotelRating() - other.getHotelRating()) * 10.0);
        if (rating > 0) {
            return -1;
        }
        if (rating < 0) {
            return 1;
        }

        //  NOTE: Higher number of reviews is better
        int reviews = getNumReviews() - other.getNumReviews();
        if (reviews > 0) {
            return -1;
        }
        if (reviews < 0) {
            return 1;
        }

        //  NOTE: The two Vacations are equal.
        return 0;
    }

    public String toString() {
        //
        // DEVELOPER NOTE: This method is mostly for debugging
        //
        String airport_location = airport_country + ", " + airport_city;
        String hotel_location   = hotel_country   + ", " + hotel_city;

        if (! airport_location.equalsIgnoreCase(hotel_location)) {
            airport_location    = "Airport Location: " + airport_location + "\n\t";
            hotel_location      = "Hotel Location:   " + hotel_location + "\n\t";
        }
        else {
            airport_location    = "Location:         " + airport_location + "\n\t";
            hotel_location      = "";
        }

        String vacation = "\n\t"                                                           +
                           airport_location                                                +
                          "Flight Cost:      $" + flight_cost          + " USD"   + "\n\t" +
                          "Flight Distance:  "  + distance_from_origin + " miles" + "\n\t" +
                           hotel_location                                                  +
                          "Hotel Name:       "  + hotel_name                      + "\n\t" +
                          "Hotel Cost:       "  + hotel_cost                      + "\n\t" +
                          "Hotel Rating:     "  + hotel_rating                    + "\n\t" +
                          "Hotel # Reviews:  "  + number_hotel_reviews            + "\n\t" +
                          "Hotel Distance:   "  + String.format("%.2f", distance_from_airport) + " miles from airport";
        return vacation;
    }

    public static String CSVHeader() {
        return "airport_country,airport_city,flight_cost,distance_from_origin,hotel_country,hotel_city,hotel_name,hotel_cost,hotel_rating,number_hotel_reviews,distance_from_airport";
    }

    public String toCSV() {
        String vacation         =  airport_country      + ","       +
                                   airport_city         + ","       +
                                   "$" + flight_cost    + " USD,"   +
                                   distance_from_origin + " miles," +
                                   hotel_country        + ","       +
                                   hotel_city           + ","       +
                                   hotel_name           + ","       +
                                   hotel_cost           + ","       +
                                   hotel_rating         + ","       +
                                   number_hotel_reviews + ","       +
                                   String.format("%.2f", distance_from_airport) + " miles";
        return vacation;
    }

    public String toJSON() {
        String spaces = "    ";
        StringJoiner joiner = new StringJoiner(",\n" + spaces);
        joiner.add("\"airport_country\": \"" + airport_country + "\"");
        joiner.add("\"airport_city\": \"" + airport_city + "\"");
        joiner.add("\"flight_cost\": \"$" + flight_cost + " USD\"");
        joiner.add("\"distance_from_origin\": \"" + distance_from_origin + " miles\"");
        joiner.add("\"hotel_country\": \"" + hotel_country + "\"");
        joiner.add("\"hotel_city\": \"" + hotel_city + "\"");
        joiner.add("\"hotel_name\": \"" + hotel_name + "\"");
        joiner.add("\"hotel_cost\": \"" + hotel_cost + "\"");
        joiner.add("\"hotel_rating\": " + hotel_rating);
        joiner.add("\"number_hotel_reviews\": " + number_hotel_reviews);
        joiner.add("\"distance_from_airport\": \"" + String.format("%.2f", distance_from_airport) + " miles\"");
        return "{\n" + spaces + joiner.toString() + "\n}";
    }

    // Test client
    public static void main(String[] args) {
        //  JSON:
        //        { "airport_country": "Canada",
        //            "airport_city": "Vancouver",
        //            "flight_cost": "$553 USD",
        //            "distance_from_origin": "276 miles",
        //            "hotel_country": "Canada",
        //            "hotel_city": "Vancouver",
        //            "hotel_name": "St. Regis Hotel",
        //            "hotel_cost": "$$",
        //            "hotel_rating": 4.5,
        //            "number_hotel_reviews": 72,
        //            "distance_from_airport": "6.89 miles" },
        //  CSV:
        //Canada,Vancouver,$553 USD,276 miles,Canada,Vancouver,St. Regis Hotel,$$,4.5,72,6.89 miles
        //
        //  FLIGHT: 93,Montreal Intl Mirabel,Montreal,Canada,YMX,CYMX,45.681944,-74.005278,270,-5,A,2137,718
        Location flight_location = new Location("Canada", "Vancouver", 45.681944, -74.005278);
        Flight flight = new Flight(flight_location, 93, "Montreal Intl Mirabel", "YMX", "CYMX", 270, -5, "A", 2137, 718);
        //
        //  HOTEL: Canada,Vancouver,49.28297,-123.11597,St. Regis Hotel,$$,11091.308944021952,4.5,72
        Location hotel_location = new Location("Canada", "Vancouver", 49.28297, -123.11597);
        Hotel hotel = new Hotel(hotel_location, "St. Regis Hotel", "$$", 11091.308944021952, 4.5, 72);
        //
        //  VACACTION
        Vacation vacation = new Vacation.Builder().withFlight(flight).withHotel(hotel).build();
        System.out.println(vacation.toString());
        System.out.println(Vacation.CSVHeader());
        System.out.println(vacation.toCSV());
        System.out.println(vacation.toJSON());
    }
}