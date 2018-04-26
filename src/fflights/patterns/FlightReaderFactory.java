package fflights.patterns;

public class FlightReaderFactory extends AbstractFlightReaderFactory {

   public FlightReaderFactory() {
   }

   @Override
   public FlightReader getFlightReader(String filename) {
      if (filename == null) {
         return null;
      }
      if (filename.toLowerCase().endsWith("json")) {
         return new JsonFlightReader(filename);
      }
      else if (filename.toLowerCase().endsWith("csv")) {
         return new CSVFlightReader(filename);
      }
      return null;
   }
}