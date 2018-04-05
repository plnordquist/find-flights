package fflights.vo;

public class Photo {
	String name;
	String city;
	String country;
	Location location;
	
	void setName(String name) {
		this.name = name;
	}

	String getName() {
		return name;
	}

	/**
	 {
            "picture": "https://scontent.xx.fbcdn.net/v/t1.0-0/q87/s130x130/29790054_10209225522275086_6205708145889347421_n.jpg?_nc_cat=0&oh=de5202545aace2eb5798cd51cf0a0fbe&oe=5B371260",
            "place": {
              "name": "Hoedspruit, South Africa",
              "location": {
                "city": "Hoedspruit",
                "country": "South Africa",
                "latitude": -24.35,
                "longitude": 30.9667
              },
              "id": "108131739214927"
            },
            "album": {
              "created_time": "2018-04-04T07:07:51+0000",
              "name": "South Africa, Hoedspruit",
              "id": "10209225520555043"
            },
            "created_time": "2018-04-04T07:08:17+0000",
            "id": "10209225522275086"
          },
	 
	 */
}
