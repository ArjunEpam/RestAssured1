package org.example;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

public class EmployeeTests {

    private static final String BASE_URL = "https://jsonplaceholder.typicode.com";
    private static final String EMPLOYEE_NAME = "John Doe";
    private static final String UPDATED_SALARY = "50000";
    private static final String UPDATED_AGE = "30";
    Response response;
    int id;
    @BeforeClass
    public void setup() {
        RestAssured.baseURI = BASE_URL;
    }

    @Test(priority = 1)
    public void testGetListOfEmployees() {
         response = RestAssured.given()
                .contentType(ContentType.JSON).log().all().get("/users");
        List<Employee> employees = response.jsonPath().getList(".", Employee.class);
        int employeeCount = employees.size();
        Assert.assertEquals(response.statusCode(), 200, "Status code doesn't match");
        Assert.assertEquals(response.statusLine(), "HTTP/1.1 200 OK", "Status line doesn't match");
        Assert.assertEquals(response.contentType(), "application/json; charset=utf-8", "Content type doesn't match");
        Assert.assertTrue(employeeCount > 0, "Employee count is not greater than 0");
    }

    @Test(priority = 2)
    public void testCreateUser() {

        CreateUserRequest user = new CreateUserRequest();
        user.setName("Summa");
        user.setUsername("Bret");
        user.setEmail("Sincere@april.biz");

        CreateUserRequest.Address address = new CreateUserRequest.Address();
        address.setStreet("Kulas Light");
        address.setSuite("Apt. 556");
        address.setCity("Gwenborough");
        address.setZipcode("92998-3874");

        CreateUserRequest.Address.Geo geo = new CreateUserRequest.Address.Geo();
        geo.setLat("-37.3159");
        geo.setLng("81.1496");
        address.setGeo(geo);

        user.setAddress(address);

        user.setPhone("1-770-736-8031 x56442");
        user.setWebsite("hildegard.org");

        CreateUserRequest.Company company = new CreateUserRequest.Company();
        company.setName("Romaguera-Crona");
        company.setCatchPhrase("Multi-layered client-server neural-net");
        company.setBs("harness real-time e-markets");

        user.setCompany(company);

        response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(user)
                .log().all()
                .when()
                .post("/users");
        Assert.assertEquals(response.statusCode(), 201, "Status code doesn't match");
        id= response.jsonPath().getInt("id");
        System.out.println("id is "+id);
        // Assuming 201 is the expected status code for a successful creation
    }

        // Verify that the employee is created successfully


  @Test(priority = 3, dependsOnMethods = {"testCreateUser"})
    public void testGetDetailsOfCreatedEmployee() {
      //  Response response = RestAssured.get("/{"+id+"}");
      response = RestAssured.given()
              .contentType(ContentType.JSON)
              .pathParam("id",1)
              .log().all()
              .when()
              .get("/users/{id}");
        // Verify all the details given in step 2
        Employee employeeDetails = response.as(Employee.class);
        Assert.assertEquals(response.statusCode(), 200, "Status code doesn't match");
        Assert.assertEquals(response.statusLine(), "HTTP/1.1 200 OK", "Status line doesn't match");
        Assert.assertEquals(response.contentType(), "application/json; charset=utf-8", "Content type doesn't match");

        // Verify details
       /* Assert.assertEquals(employeeDetails.getName(), EMPLOYEE_NAME, "Employee name doesn't match");
        Assert.assertEquals(employeeDetails.getSalary(), UPDATED_SALARY, "Employee salary doesn't match");
        Assert.assertEquals(employeeDetails.getAge(), UPDATED_AGE, "Employee age doesn't match");
    */}

    /*  @Test(priority = 4)
    public void testUpdateEmployeeDetails() {
        Employee updatedEmployee = new Employee(null, EMPLOYEE_NAME, "employee@example.com", "60000", "32");

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(updatedEmployee)
                .put("/{id}", 11); // Assuming the created employee ID is 11

        // Verify the update is successful
        Assert.assertEquals(response.statusCode(), 200, "Status code doesn't match");
        Assert.assertEquals(response.statusLine(), "HTTP/1.1 200 OK", "Status line doesn't match");
        Assert.assertEquals(response.contentType(), "application/json; charset=utf-8", "Content type doesn't match");

        // Verify the updated details
        Response getUpdatedResponse = RestAssured.get("/{id}", 11); // Assuming the created employee ID is 11
        Employee updatedDetails = getUpdatedResponse.as(Employee.class);
        Assert.assertEquals(updatedDetails.getSalary(), "60000", "Updated salary doesn't match");
        Assert.assertEquals(updatedDetails.getAge(), "32", "Updated age doesn't match");
    }

    @Test(priority = 5)
    public void testGetUpdatedEmployeeDetails() {
        Response response = RestAssured.get("/{id}", 11); // Assuming the created employee ID is 11

        // Verify the updated details in step 4
        Employee updatedDetails = response.as(Employee.class);
        Assert.assertEquals(response.statusCode(), 200, "Status code doesn't match");
        Assert.assertEquals(response.statusLine(), "HTTP/1.1 200 OK", "Status line doesn't match");
        Assert.assertEquals(response.contentType(), "application/json; charset=utf-8", "Content type doesn't match");

        // Verify details
        Assert.assertEquals(updatedDetails.getSalary(), "60000", "Updated salary doesn't match");
        Assert.assertEquals(updatedDetails.getAge(), "32", "Updated age doesn't match");
    }

    @Test(priority = 6)
    public void testDeleteEmployee() {
        Response response = RestAssured.delete("/{id}", 11); // Assuming the created employee ID is 11

        // Verify the delete is successful
        Assert.assertEquals(response.statusCode(), 200, "Status code doesn't match");
        Assert.assertEquals(response.statusLine(), "HTTP/1.1 200 OK", "Status line doesn't match");
        Assert.assertEquals(response.contentType(), "application/json; charset=utf-8", "Content type doesn't match");

        // Verify the total list of employees is decreased by -1
        Response getListResponse = RestAssured.get();
        List<Employee> employees = getListResponse.jsonPath().getList(".", Employee.class);
        int newEmployeeCount = employees.size();
        Assert.assertEquals(newEmployeeCount, employeeCount, "Employee count did not decrease by -1");
    }*/
}
