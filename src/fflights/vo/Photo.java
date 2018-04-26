package fflights.vo;

import java.util.StringJoiner;

/*
 * CRC Responsibilities
 *   - Hold information about a Facebook Photo
 *   - Answer questions about
 *     - Number of reactions
 *     - Are reactions positive or negative
 *   - Be able to print the Hotel data out in the formats:
 *     - String
 *     - CSV
 *     - JSON
 */

/*
 * DEVELOPER NOTE: __STUDENT_PUT_SOME_DOCUMENTATION_HERE__
 */
public class Photo {
    private Location location;
    private int like;
    private int love;
    private int haha;
    private int wow;
    private int sad;
    private int angry;
    private int positive;  // = like + love + haha + wow
    private int negative;  // = sad + angry
    private int reactions; // = positive + negative

    public Photo(Location location,
                 int like,
                 int love,
                 int haha,
                 int wow,
                 int sad,
                 int angry
                ) {
        this.location  = location;
        this.like      = like;
        this.love      = love;
        this.haha      = haha;
        this.wow       = wow;
        this.sad       = sad;
        this.angry     = angry;
        this.positive  = like + love + haha + wow;
        this.negative  = sad + angry;
        this.reactions = positive + negative;
    }

    public Location getLocation() {
        return location;
    }

    public double getMiles(Location location) {
        return this.location.distanceTo(location);
    }

    public int getNumberReactions() {
        return reactions;
    }

    public String getMood() {
        if (positive > negative)
        {
            return "positive";
        }
        return "negative";
    }

    public String toString() {
        return location.toString() + ", Reactions:" + getNumberReactions() + ", Mood:" + getMood();
    }

    public static String CSVHeader() {
        return "country,city,latitude,longitude,like,love,haha,wow,sad,angry";
    }

    public String toCSV() {
        StringJoiner joiner = new StringJoiner(",");
        joiner.add(String.valueOf(like));
        joiner.add(String.valueOf(love));
        joiner.add(String.valueOf(haha));
        joiner.add(String.valueOf(wow));
        joiner.add(String.valueOf(sad));
        joiner.add(String.valueOf(angry));
        return location.toCSV() + "," + joiner.toString();
    }

    public String toJSON() {
        String spaces = "    ";
        StringJoiner joiner = new StringJoiner(",\n" + spaces);
        joiner.add("\"TODO\": \"NOT IMPLEMENTED\"");
        return "{\n" + spaces + joiner.toString() + "\n}";
    }
/**
  NOTE: The JSON data structure at the bottom is from the Facebook Developer API
    https://developers.facebook.com/tools/explorer
  NOTE: After getting an Access Token the following is the query:
    GET /v2.12 /me/albums?fields=photos.limit(100){place,reactions}
  NOTE: The actual URL looks like:
    https://developers.facebook.com/tools/explorer/?method=GET&path=me%2Falbums%3Ffields%3Dphotos.limit(100)%7Bplace%2Creactions%7D&version=v2.12

  NOTE: This data would result in producing a single photo
        with the following values:
    - Location("United States", "West Yellowstone", 44.6625, -111.10583333333);
    - like = 7
    - love = 3
    - haha = 1
    - wow  = 1
    - sad  = 1
    - angry = 1
    - positive  = like + love + haha + wow
    - negative  = sad + angry
    - reactions = positive + negative
    - feeling = (positive > negative) ? positive : negative

  NOTE: The following is a pseudo data structure of the JSON where
        the JSON is assigned to a "top". The pseudo structure
        mimics Python dicts and lists. The '[#]' below represents
        arrays indexed by numbers.
    top['data'][#]['photos']['data'][#]['place']
    top['data'][#]['photos']['data'][#]['place']['location']
    top['data'][#]['photos']['data'][#]['place']['location']['city']
    top['data'][#]['photos']['data'][#]['place']['location']['country']
    top['data'][#]['photos']['data'][#]['place']['location']['latitude']
    top['data'][#]['photos']['data'][#]['place']['location']['longitude']
    top['data'][#]['photos']['data'][#]['place']['location']['state']
    top['data'][#]['photos']['data'][#]['place']['location']['zip']
    top['data'][#]['photos']['data'][#]['place']['name']
    top['data'][#]['photos']['data'][#]['place']['id']
    top['data'][#]['photos']['data'][#]['reactions']['id']
    top['data'][#]['photos']['data'][#]['reactions']['data'][#]['type']
    top['data'][#]['photos']['data'][#]['id']
    top['data'][#]['id']

  NOTE: There is an "id" for each of the "photos", "reactions",
        and "place". We will not use an id or name fields.

  IMPORTANT NOTE: It is possible to have "reactions" without "place"
                  Be very careful not to create Photo objects without
                  a location! Do not create a Photo object without
                  a "place" containing:
                    "country", "city", "latitude", "longitude"
  NOTE: A JSON result set containing an array of a single photo
{
  "data": [
    {
      "photos": {
        "data": [
          {
            "reactions": {
              "data": [
                {
                  "type": "LIKE"
                },
                {
                  "type": "LIKE"
                },
                {
                  "type": "LIKE"
                },
                {
                  "type": "LIKE"
                },
                {
                  "type": "HAHA"
                },
                {
                  "type": "SAD"
                },
                {
                  "type": "LOVE"
                },
                {
                  "type": "ANGRY"
                },
                {
                  "type": "LIKE"
                },
                {
                  "type": "LIKE"
                },
                {
                  "type": "WOW"
                },
                {
                  "type": "LOVE"
                },
                {
                  "type": "LOVE"
                },
                {
                  "type": "LIKE"
                }
              ]
            },
            "place": {
              "location": {
                "city": "West Yellowstone",
                "zip": "59758",
                "country": "United States",
                "longitude": -111.10583333333,
                "state": "MT",
                "latitude": 44.6625
              },
              "id": "105651049468788",
              "name": "West Yellowstone, MT"
            },
            "id": "10209225583196609"
          }
        ]
      }
      "id": "4492626773729"
    }
  ]
}
*/

    // Test client
    public static void main(String[] args) {
        //  JSON:
        //        See Record above
        //  CSV:
        //United States,West Yellowstone,44.6625,-111.10583333333,7,3,1,1,1,1
        Location location = new Location("United States", "West Yellowstone", 44.6625, -111.10583333333);
        Photo photo = new Photo(location, 7, 3, 1, 1, 1, 1);
        System.out.println(photo.toString());
        System.out.println(Photo.CSVHeader());
        System.out.println(photo.toCSV());
        System.out.println(photo.toJSON());
    }
}