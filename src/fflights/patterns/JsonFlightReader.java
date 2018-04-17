package fflights.patterns;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import fflights.vo.Flight;
import fflights.vo.Location;

public class JsonFlightReader implements FlightReader {

	private String filename;
	
	@Override
	public List<Flight> getFlights() {
	    List<Flight> flights = new ArrayList<>();
	    try {
	        File file = new File(filename);
	        JSONParser parser = new JSONParser();
	        JSONArray allArray = (JSONArray) parser.parse(new FileReader(file.getPath()));
            for (Object record : allArray) {
	            String country   = (String) ((JSONObject) record).get("country");
	            String city      = (String) ((JSONObject) record).get("city");
	            double latitude  = (double) ((JSONObject) record).get("latitude");
	            double longitude = (double) ((JSONObject) record).get("longitude");
	            Long   miles     = (Long)   ((JSONObject) record).get("miles");
	            Long   cost      = (Long)   ((JSONObject) record).get("cost");
	            Flight flight    = new Flight(new Location(country, city, latitude, longitude), miles.intValue(), cost.intValue());
	            flights.add(flight);
	        }
	    } catch (ParseException | IOException e) {
	        e.printStackTrace();
	    }
	    
	    return flights;
	}
	
	public JsonFlightReader(String filename) {
		this.filename = filename;
	}
}