# Find Flights

This code base is a concept of an application that can be used for:
   - Computer Science Software Design Pattern courses
   - Code includes examples using the following Software Design Patterns
     - Abstract Factory Pattern
     - Factory Pattern
     - Filter Pattern (also known as the Criteria Pattern)
     - Builder Pattern
     
The idea that this source code is trying to address is to show how to implement software using simple design processes such as Class Responsibilities Collaborators (CRC) cards.

The opportunity that this software is trying to solve is the following idea:
   - A Facebook user wants to find a vacation that they didn't know they wanted to go on.
   
Think about it using a simple scenario:
   - A user has 150 friends and 10-20 of them go on international travel while some other friends only travel domestically.
   - If we can inspect the meta-data from all the photos we should find "clusters" of photos where multiple friends have traveled to the same area (e.g. New York City, United States or Cape Town, South Africa).
   - The user may have never considered going to New York or Cape Town but now we have the ability to show them vacations options including flights and hotels.
   - If the vacations are good deals, the user may be more likely to go on a trip where there friends had a positive experience.
   - The user can find vacations that meet their own criteria (location, price, comfort, etc).

The approach was partially thwarted after the Cambridge Analytica/Facebook situation. Before Facebook enforced the Facebook API, a user could search all their friends photos. After Facebook enforced the API, the user could only search their own photos. Luckily, one user had enough photos to give us a good corpus of data to play with. Some additional code had to be written to artificially (randomly) assign reactions to the photo data.

The idea was to have a Facebook application that would allow a user to search all meta-data about their friends photos. The meta-data would have the GPS coordinates of the photo and the collections of reactions.

This would allow the user to apply search criteria to the photos such as only considering the photos that had a minimum number of reactions and those reactions needed to have a positive sentiment (e.g. the number of like, love, haha, and wow reactions out numbered the number of sad and angry reactions).

Once the application had a collection of photos, the GPS coordinates could be correlated with airports within some distance of where the photos were taken.

Finding the intersection of photo coordinates with the airport coordinates would result in a reduced set of unique airports for consideration.

The next step would be to find Hotels (lodging) within some distance from the airports using the Yelp API.

Now we can combine the flight and hotel information into vacation options.

The source code can be compiled and run with the command line options such as:
  --flights-file data/flights.csv 
  --photos-file data/facebook_photos.json
  --hotels-file data/hotels.json
  --min-reactions 5 
  --reactions positive 
  --min-miles 200 
  --max-miles 5000 
  --max-meters 80000
  --min-rating 2.0 
  --min-reviews 5
  --min-cost 1 
  --max-cost 3
  
NOTE: The data files (facebook_photos, flights, and hotels) are in JSON and CSV formats. This allows us to use the Abstract Factory Pattern to use any combination of input files and file formats.

The min-reactions and reactions allow us to reduce the set of photos to consider using the Filter Design Pattern.
The min-miles and max miles allow us to reduce the set of photos and flights to consider.
The max-meters, min-rating, min-reviews, min-cost, and max-cost allow us to reduce the set of hotels to consider.

The command line options listed above limit the set of photos that are positive and have a minimum of 5 reactions. In addition, the photos and airport destinations (flights) must be at least 200 miles but no more than 5000 miles from the user's location. This makes sense because a user probably does not want to fly less than 200 miles because it would probably be cheaper to drive. The Yelp API returns distances in meters so the hotels are selected within 80000 meters (approximately 50 miles) from the airport. In addition, the hotels need to be rated 2.0 or higher, have at least 5 reviews, and be at least a cost of one dollar sign ($) but no more than three dollar signs ($$$).

A final note is that some of the data was reduced to a minimal set. This was done for instructional purposes that could be used to give students enough information for the source code to be more than a toy but less than a fully functional application.