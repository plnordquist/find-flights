package fflights.patterns;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import fflights.vo.Photo;
import fflights.vo.Location;

public class CSVPhotoReader implements PhotoReader {

    private String filename;

    @Override
    public List<Photo> getPhotos() {
        List<Photo> photos = new ArrayList<>();
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
            String country   = record.get("country");
            String city      = record.get("city");
            double latitude  = Double.parseDouble(record.get("latitude"));
            double longitude = Double.parseDouble(record.get("longitude"));
            int    like      = Integer.parseInt(record.get("like"));
            int    love      = Integer.parseInt(record.get("love"));
            int    haha      = Integer.parseInt(record.get("haha"));
            int    wow       = Integer.parseInt(record.get("wow"));
            int    sad       = Integer.parseInt(record.get("sad"));
            int    angry     = Integer.parseInt(record.get("angry"));
            Photo  photo     = new Photo(new Location(country, city, latitude, longitude), like, love, haha, wow, sad, angry);
            photos.add(photo);
        }

        return photos;
    }

    public CSVPhotoReader(String filename) {
        this.filename = filename;
    }
}