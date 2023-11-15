package org.example;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.io.File;

public class PetstoreAPITest {

    private static final String PETSTORE_BASE_URL = "https://petstore.swagger.io/v2";
    private static final String PET_ENDPOINT = "/pet/12345";
    private static final String USERS_ENDPOINT = "https://jsonplaceholder.typicode.com/users";
    private static final String PET_PAYLOAD_FILE = "src/test/resources/petPayload.json";

    @Test(priority = 1)
    public void createPet() {
        RestAssured.given()
                .contentType("application/json")
                .body(new File(PET_PAYLOAD_FILE))
                .post(PETSTORE_BASE_URL + "/pet")
                .then()
                .statusCode(200);
    }

    @Test(priority = 2)
    public void getPetAndValidateDetails() {
        Response response = RestAssured.get(PETSTORE_BASE_URL + PET_ENDPOINT);

        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 200, "Status code is not 200");

        String contentType = response.getContentType();
        Assert.assertEquals(contentType, "application/json", "Content type is not application/json");

        String petCategory = response.path("category.name");
        Assert.assertEquals(petCategory, "dog", "Pet is not a dog");

        String petName = response.path("name");
        Assert.assertEquals(petName, "snoopie", "Pet name is not snoopie");

        String petStatus = response.path("status");
        Assert.assertEquals(petStatus, "pending", "Pet status is not pending");
    }

    @Test(priority = 3)
    public void getUsersAndValidateDetails() {
        Response response = RestAssured.get(USERS_ENDPOINT);

        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 200, "Status code is not 200");

        int numberOfUsers = response.jsonPath().getList("$").size();
        Assert.assertTrue(numberOfUsers > 3, "Number of users is not greater than 3");

        boolean userFound = response.jsonPath().getList("findAll { it.name == 'Ervin Howell' }").size() > 0;
        Assert.assertTrue(userFound, "User with name 'Ervin Howell' not found");
    }
}
