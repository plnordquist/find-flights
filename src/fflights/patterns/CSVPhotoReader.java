package fflights.patterns;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import fflights.util.Reaction;
import fflights.vo.Photo;
import fflights.vo.Location;

public class CSVPhotoReader implements PhotoReader {

	private String filename;
	
	@Override
	public List<Photo> getPhotos() {
	    List<Photo> photos = new ArrayList<>();
	    try {
	        File       file   = new File(filename);
	        JSONParser parser = new JSONParser();
	        JSONObject record = (JSONObject) parser.parse(new FileReader(file.getPath()));
	        JSONArray  data   = (JSONArray)record.get("data");

	        for (int i = 0; i < data.size(); ++i) {
		        String     dataRecord     = data.get(i).toString();
		        JSONObject dataArray      = (JSONObject) JSONValue.parse(dataRecord);
		        JSONObject photosRecord   = (JSONObject) dataArray.get("photos");
		        JSONObject photosArray    = (JSONObject) JSONValue.parse(photosRecord.toString());
		        JSONArray  photoDataArray = (JSONArray)  photosArray.get("data");
		        for (int j = 0; j < photoDataArray.size(); ++j) {
			        int like  = 0;
			        int love  = 0;
			        int haha  = 0;
			        int wow   = 0;
			        int sad   = 0;
			        int angry = 0;
			        JSONObject photoRecord      = (JSONObject) photoDataArray.get(j);
			        JSONObject photoRecordArray = (JSONObject) JSONValue.parse(photoRecord.toString());
			        JSONObject reactionsRecord  = (JSONObject) photoRecordArray.get("reactions");
			        JSONObject reactionsArray   = (JSONObject) JSONValue.parse(reactionsRecord.toString());
			        JSONArray reactionDataArray = (JSONArray)  reactionsArray.get("data");
			        for (int k = 0; k < reactionDataArray.size(); ++k) {
				        JSONObject reactionRecord = (JSONObject) reactionDataArray.get(k);
						String     reaction       = (String)     reactionRecord.get("type");
					    switch (Reaction.valueOf(reaction.toUpperCase()))
					    {
					      case LIKE:   ++like;
					      case LOVE:   ++love;
					      case HAHA:   ++haha;
					      case WOW:     ++wow;
					      case SAD:     ++sad;
					      case ANGRY: ++angry;
					    }
			        }
			        JSONObject placeRecord = (JSONObject)photoRecordArray.get("place");
					if (null != placeRecord)
					{
						JSONObject locationRecord = (JSONObject)placeRecord.get("location");
						if (null != locationRecord)
						{
							String   country   = (String) locationRecord.get("country");
							String   city      = (String) locationRecord.get("city");
							Number   latitude  = (Number) locationRecord.get("latitude");
							Number   longitude = (Number) locationRecord.get("longitude");
							Location location  = new Location(country, city, latitude.doubleValue(), longitude.doubleValue());
							Photo    photo     = new Photo(location, like, love, haha, wow, sad, angry);
							photos.add(photo);
						}
					}
		        }
	        }
	    } catch (ParseException | IOException e) {
	        e.printStackTrace();
	    }
	    
	    return photos;
	}
	
	public CSVPhotoReader(String filename) {
		this.filename = filename;
	}
	
}