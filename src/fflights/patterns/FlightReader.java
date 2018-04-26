package fflights.patterns;

import java.util.List;

import fflights.vo.Flight;

public interface FlightReader {
    public List<Flight> getFlights();
}