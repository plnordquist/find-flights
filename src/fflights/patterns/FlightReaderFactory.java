package fflights.patterns;

public class FlightReaderFactory extends AbstractFlightReaderFactory {
	
   public FlightReaderFactory() {	   
   }
   
   @Override
   public FlightReader getFlightReader(String filename) {
      if (filename == null){
         return null;
      }
      String testname = filename.toLowerCase();
      if (testname.endsWith("json")) {
         return new JsonFlightReader(filename);
      } 
      else if (testname.endsWith("csv")) {
         return new CSVFlightReader(filename); 
      }   
      return null;
   }   
}