package fflights;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import fflights.util.ProgramOptions;
import fflights.vo.Flight;
import fflights.vo.Location;
import fflights.vo.Photo;

/**
 *
 */
public class FindFlights {
	
	public static final String FLIGHT_FILE="/Users/d3c572/git/find-flights/data/flights.json";
	public static final String PHOTO_FILE="/Users/d3c572/git/find-flights/data/facebook_photos.json";

	static String readFile(String path, Charset encoding) throws IOException
	{
		try {
			byte[] encoded = Files.readAllBytes(Paths.get(path));
			return new String(encoded, encoding);
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public enum Reaction {
	    LIKE, LOVE, HAHA, WOW,
	    SAD, ANGRY 
	}
	
	public static void main(String... args) {
		ProgramOptions programOptions = new ProgramOptions();
		if (! programOptions.parseOptions(args)) {
			System.exit(1);
	    }
		// TODO: Start processing files and get to work
		
	    ArrayList<Flight> flights = new ArrayList<>();
	    try {
	        File file = new File(FLIGHT_FILE);
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
	    
	    for (Flight flight : flights) {
            System.out.println("FLIGHT:" + flight.toString());
        }
	    
	    ArrayList<Photo> photos = new ArrayList<>();
	    try {
			String content = readFile(PHOTO_FILE, StandardCharsets.UTF_8);

	        //File file = new File(PHOTO_FILE);
	        //JSONParser parser = new JSONParser();
	        //JSONObject data = (JSONObject) parser.parse(new FileReader(file.getPath()));
	        JSONObject record = (JSONObject)JSONValue.parse(content);
	        JSONArray data = (JSONArray)record.get("data");

	        for (int i = 0; i < data.size(); ++i) {
		        String dataRecord = data.get(i).toString();
		        JSONObject dataArray = (JSONObject)JSONValue.parse(dataRecord);
		        JSONObject photosRecord = (JSONObject)dataArray.get("photos");
		        JSONObject photosArray = (JSONObject)JSONValue.parse(photosRecord.toString());
		        JSONArray photoDataArray = (JSONArray)photosArray.get("data");
		        for (int j = 0; j < photoDataArray.size(); ++j) {
			        int like    = 0;
			        int love    = 0;
			        int haha    = 0;
			        int wow     = 0;
			        int sad     = 0;
			        int angry   = 0;
			        //int unknown = 0;
			        JSONObject photoRecord = (JSONObject)photoDataArray.get(j);
			        JSONObject photoRecordArray = (JSONObject)JSONValue.parse(photoRecord.toString());
			        JSONObject reactionsRecord = (JSONObject)photoRecordArray.get("reactions");
			        JSONObject reactionsArray = (JSONObject)JSONValue.parse(reactionsRecord.toString());
			        JSONArray reactionDataArray = (JSONArray)reactionsArray.get("data");
			        for (int k = 0; k < reactionDataArray.size(); ++k) {
				        JSONObject reactionRecord = (JSONObject) reactionDataArray.get(k);
						String reaction = (String) reactionRecord.get("type");
						//System.out.println("REACTION:" + reaction);
					    switch (Reaction.valueOf(reaction.toUpperCase()))
					    {
					      case LIKE:     ++like;
					      case LOVE:     ++love;
					      case HAHA:     ++haha;
					      case WOW:       ++wow;
					      case SAD:       ++sad;
					      case ANGRY:   ++angry;
					      //default:    ++unknown;
					    }
			        }
					//System.out.println("REACTIONS:" + reactionsArray.toJSONString());
			        JSONObject placeRecord = (JSONObject)photoRecordArray.get("place");
					if (null != placeRecord)
					{
						JSONObject locationRecord = (JSONObject)placeRecord.get("location");
						if (null != locationRecord)
						{
							String country = (String) locationRecord.get("country");
							String city = (String) locationRecord.get("city");
							Number latitude = (Number) locationRecord.get("latitude");
							Number longitude = (Number) locationRecord.get("longitude");
							Location location = new Location(country, city, latitude.doubleValue(), longitude.doubleValue());
							//System.out.println("LOCATION:" + location.toString());				
							Photo photo = new Photo(location, like, love, haha, wow, sad, angry);
							photos.add(photo);
						}
					}
		        }
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    
	    for (Photo photo : photos) {
            System.out.println("PHOTO:" + photo.toString());
        }
	}
}