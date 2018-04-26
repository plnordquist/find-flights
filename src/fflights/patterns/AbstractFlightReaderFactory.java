package fflights.patterns;

public abstract class AbstractFlightReaderFactory {
    abstract FlightReader getFlightReader(String filetype);
}