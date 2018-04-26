package fflights.patterns;

public class HotelReaderFactory extends AbstractHotelReaderFactory {

   public HotelReaderFactory() {
   }

   @Override
   public HotelReader getHotelReader(String filename) {
      if (filename == null) {
         return null;
      }
      if (filename.toLowerCase().endsWith("json")) {
         return new JsonHotelReader(filename);
      }
      else if (filename.toLowerCase().endsWith("csv")) {
         return new CSVHotelReader(filename);
      }
      return null;
   }
}