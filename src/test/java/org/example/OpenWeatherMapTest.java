package org.example;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class OpenWeatherMapTest {

    private static final String API_KEY = "42e79de0925a607e3a5bf8255bcbede1";
    private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/weather";
    WeatherResponse weatherResponse;
    CustomWeatherResponse customWeatherResponse;
    private RequestSpecification requestSpec;
    private ResponseSpecification responseSpec;

    @BeforeClass
    public void setUp() {
        requestSpec = new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .addQueryParam("appid", API_KEY)
                .setContentType(ContentType.JSON)
                .build();

        responseSpec = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .build();
    }

    @Test(priority = 1)
    public void verifyWeatherDetails() {
        weatherResponse = given()
                .spec(requestSpec)
                .queryParam("q","hyderabad")
                .when().log().all()
                .get()
                .then()
                .log().all()
                .spec(responseSpec)
                .extract()
                .as(WeatherResponse.class);

        weatherResponse.getName().equals("Hyderabad");
        weatherResponse.getSys().getCountry().equals("IN");
        Assert.assertTrue(weatherResponse.getMain().getTempMin() > 0.0);
        Assert.assertTrue(weatherResponse.getMain().getTemp() > 0.0);
    }

    @Test(priority = 2)
    public void testWeatherDetails() {
        customWeatherResponse = given()
                .spec(requestSpec)
                .queryParam("lat",weatherResponse.getCoord().getLat())
                .queryParam("lon",weatherResponse.getCoord().getLon())
                .when().log().all()
                .get()
                .then()
                .log().all()
                .spec(responseSpec)
                .extract()
                .as(CustomWeatherResponse.class);

        Assert.assertEquals(customWeatherResponse.getName(), "Hyderabad");
        Assert.assertEquals(customWeatherResponse.getSys().getCountry(), "IN");
        Assert.assertTrue(customWeatherResponse.getMain().getTempMin() > 0);
        Assert.assertTrue(customWeatherResponse.getMain().getTemp() > 0);
    }


}
