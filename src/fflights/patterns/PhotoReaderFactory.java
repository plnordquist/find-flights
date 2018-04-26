package fflights.patterns;

public class PhotoReaderFactory extends AbstractPhotoReaderFactory {

   public PhotoReaderFactory() {
   }

   @Override
   public PhotoReader getPhotoReader(String filename) {
      if (filename == null) {
         return null;
      }
      if (filename.toLowerCase().endsWith("json")) {
         return new JsonPhotoReader(filename);
      }
      else if (filename.toLowerCase().endsWith("csv")) {
         return new CSVPhotoReader(filename);
      }
      return null;
   }
}