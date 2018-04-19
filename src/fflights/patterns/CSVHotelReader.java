package fflights.patterns;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import fflights.vo.Hotel;
import fflights.vo.Location;

public class CSVHotelReader implements HotelReader {

	private String filename;
	
	@Override
	public List<Hotel> getHotels() {
	    List<Hotel> hotels = new ArrayList<>();
    	// NOTE: Based on https://commons.apache.org/proper/commons-csv/user-guide.html
    	Reader in = null;
		try {
			in = new FileReader(filename);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
    	Iterable<CSVRecord> records = null;
		try {
			records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	for (CSVRecord record : records) {
    	    String country      = record.get("country");
            String city         = record.get("city");
            double latitude     = Double.parseDouble(record.get("latitude"));
            double longitude    = Double.parseDouble(record.get("longitude"));
            double meters       = Double.parseDouble(record.get("meters"));
            String name         = record.get("name");
            String price        = record.get("price");
            double rating       = Double.parseDouble(record.get("rating"));
            Long   review_count = Long.parseLong(record.get("review_count"));
            Hotel  hotel        = new Hotel(new Location(country, city, latitude, longitude), name, price, meters, rating, review_count.intValue());
            hotels.add(hotel);
    	}
	    
	    return hotels;
	}
	
	public CSVHotelReader(String filename) {
		this.filename = filename;
	}
}