package fflights.patterns;

public class HotelReaderFactory extends AbstractHotelReaderFactory {
	
   public HotelReaderFactory() {	   
   }
   
   @Override
   public HotelReader getHotelReader(String filename) {
      if (filename == null){
         return null;
      }
      String testname = filename.toLowerCase();
      if (testname.endsWith("json")) {
         return new JsonHotelReader(filename);
      } 
//      else if (testname.endsWith("csv")) {
//         return new CSVHotelReader(filename); 
//      }   
      return null;
   }   
}