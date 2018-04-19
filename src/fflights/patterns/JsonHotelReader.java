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

import fflights.vo.Hotel;
import fflights.vo.Location;

public class JsonHotelReader implements HotelReader {

	private String filename;
	
	@Override
	public List<Hotel> getHotels() {
	    List<Hotel> hotels = new ArrayList<>();
	    try {
	        File file = new File(filename);
	        JSONParser parser = new JSONParser();
	        JSONArray allArray = (JSONArray) parser.parse(new FileReader(file.getPath()));
            for (Object record : allArray) {
	            String city         = (String) ((JSONObject) record).get("city");
            	String country      = (String) ((JSONObject) record).get("country");
	            double distance     = (double) ((JSONObject) record).get("distance");
	            double latitude     = (double) ((JSONObject) record).get("latitude");
	            double longitude    = (double) ((JSONObject) record).get("longitude");
	            String name         = (String) ((JSONObject) record).get("name");
	            String price        = (String) ((JSONObject) record).get("price");
	            double rating       = (double) ((JSONObject) record).get("rating");
	            Long   review_count = (Long)   ((JSONObject) record).get("review_count");
	            Hotel  hotel        = new Hotel(new Location(country, city, latitude, longitude), name, price, distance, rating, review_count.intValue());
	            hotels.add(hotel);
	        }
	    } catch (ParseException | IOException e) {
	        e.printStackTrace();
	    }
	    
	    return hotels;
	}
	
	public JsonHotelReader(String filename) {
		this.filename = filename;
	}
}