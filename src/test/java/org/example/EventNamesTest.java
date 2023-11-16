package org.example;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.List;

public class EventNamesTest {

    private static final String API_BASE_URL = "https://events.epam.com/api/v2/events";
    private static final String EXPECTED_LANGUAGE = "En";

    @Test
    public void verifyEventNamesInEnglish() {
        Response response = RestAssured.get(API_BASE_URL);

        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 200, "Status code is not 200");

        List<String> eventNames = response.jsonPath().getList("data.findAll { it.language == 'En' }.attributes.name");
        System.out.println(eventNames);
        List<String> expectedEventNames = List.of("EventName1", "EventName2", "EventName3");

        Assert.assertEquals(eventNames, expectedEventNames, "Event names do not match expected values");
    }
}
