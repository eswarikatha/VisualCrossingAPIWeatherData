package com.weatherdata.API;

import org.json.JSONObject;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class WeatherDataAPITest2 {
	@Test
	public void weather() {
		RestAssured.baseURI = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline";
		Response response = RestAssured
				.given()
					.header("Content-Type", "application/json")
					.param("v", "api")
				.when().get("/Chennai");

		String responseBody = response.getBody().asString();

		System.out.println("Response Body: " + responseBody);

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
