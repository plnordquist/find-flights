package fflights.patterns;

public abstract class AbstractHotelReaderFactory {
	abstract HotelReader getHotelReader(String filetype);
}