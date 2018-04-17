package fflights.patterns;

public class PhotoReaderFactory extends AbstractPhotoReaderFactory {
	
   public PhotoReaderFactory() {	   
   }
   
   @Override
   public PhotoReader getPhotoReader(String filename) {
      if (filename == null){
         return null;
      }
      String testname = filename.toLowerCase();
      if (testname.endsWith("json")) {
         return new JsonPhotoReader(filename);
      } 
//
//  TODO: NOTE: We should add a CSVPhotoReader
//
//      else if (filetype.equalsIgnoreCase("CSV")) {
//         return new CSVPhotoReader(); 
//      }   
      return null;
   }   
}