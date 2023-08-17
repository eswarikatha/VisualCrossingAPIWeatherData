package com.weatherdata.API;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

//https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/Tallinn?unitGroup=metric&key=YOUR_API_KEY&contentType=json
	
	

public class WeatherDataAPITest {
	@Test
	public void weather() {
		//Run the API and store the response in the variable
		RestAssured.baseURI = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline";
		Response response = 
				RestAssured.given()
							.header("Content-Type", "application/json")
							.pathParam("city", "Chennai")
							.queryParam("unitGroup", "metric")
							.queryParam("key", "JVGLTBAZR9L36HZKD6WE5PBYB")
						.when()
							.get("/{city}");

		
		 // Assert that correct status code 200 is returned.
        Assert.assertEquals(response.getStatusCode() , 200 , "Correct status code (200) returned");
        
		String responseBody = response.getBody().asString();
		System.out.println("Response Body: " + responseBody);

		//Validate Response
		try {
			if (responseBody.startsWith("{")) {
				JSONObject json = new JSONObject(responseBody);

				String resolvedAddress = json.getString("resolvedAddress");
				String timezone = json.getString("timezone");

				org.json.JSONArray daysArray = json.getJSONArray("days");
				JSONObject firstDay = daysArray.getJSONObject(0);
				String datetime = firstDay.getString("datetime");
				double tempMax = firstDay.getDouble("tempmax");
				double tempMin = firstDay.getDouble("tempmin");
				double temp = firstDay.getDouble("temp");
				String conditions = firstDay.getString("conditions");
				
				//Display the data for verification
				System.out.println("Resolved Address: " + resolvedAddress);
				System.out.println("Timezone: " + timezone);
				System.out.println("Date: " + datetime);
				System.out.println("Max Temperature: " + tempMax);
				System.out.println("Min Temperature: " + tempMin);
				System.out.println("Temperature: " + temp);
				System.out.println("Conditions: " + conditions);
			} else {
				System.out.println("Response is not a valid JSON object.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
