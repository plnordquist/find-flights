package fflights.patterns;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import fflights.vo.Flight;
import fflights.vo.Location;

public class CSVFlightReader implements FlightReader {

    private String filename;

    @Override
    public List<Flight> getFlights() {
        List<Flight> flights = new ArrayList<>();
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
            Long   airport_id = Long.parseLong(record.get("airport_id"));
            String name       = record.get("name");
            String city       = record.get("city");
            String country    = record.get("country");
            String iata_faa   = record.get("iata_faa");
            String iaco       = record.get("iaco");
            double latitude   = Double.parseDouble(record.get("latitude"));
            double longitude  = Double.parseDouble(record.get("longitude"));
            Long   altitude   = Long.parseLong(record.get("altitude"));
            double zone       = Double.parseDouble(record.get("zone"));
            String dst        = record.get("dst");
            Long   miles      = Long.parseLong(record.get("miles"));
            Long   cost       = Long.parseLong(record.get("cost"));
            Flight flight     = new Flight(new Location(country, city, latitude, longitude), airport_id.intValue(), name, iata_faa, iaco, altitude.intValue(), zone, dst, miles.intValue(), cost.intValue());
            flights.add(flight);
        }

        return flights;
    }

    public CSVFlightReader(String filename) {
        this.filename = filename;
    }
}