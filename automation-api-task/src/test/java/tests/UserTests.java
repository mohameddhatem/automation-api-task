package tests;

import dataprovider.UserDataProvider;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import io.qameta.allure.*;
import org.testng.annotations.Listeners;
@Epic("Pet Store API Automation")
@Feature("User Management")
public class UserTests {

    private static final String BASE_URL = "https://petstore3.swagger.io/api/v3";

    // create userr
    @Test(dataProvider = "userData", dataProviderClass = UserDataProvider.class, priority = 1)
    @Description("Test to create a new user in the Pet Store API")
    @Story("Create User")
    public void testCreateUserWithData(int id, String username, String firstName, String lastName, String email, String password, String phone, int userStatus) {
        String requestBody = String.format("""
        {
            "id": %d,
            "username": "%s",
            "firstName": "%s",
            "lastName": "%s",
            "email": "%s",
            "password": "%s",
            "phone": "%s",
            "userStatus": %d
        }
    """, id, username, firstName, lastName, email, password, phone, userStatus);

        //  debugginggg
        logStep("Sending POST request to create user");
        Response response = RestAssured.given()
                .baseUri(BASE_URL)
                .header("Content-Type", "application/json")
                .body(requestBody)
                .log().all() // Logs the request
                .post("/user");

        response.then().log().all(); // Logs the response

        Assert.assertEquals(response.statusCode(), 200,"Verify the status code is 200");
        Assert.assertEquals(response.jsonPath().getInt("id"), id);
    }

    //Get a user by ID
    @Test(dataProvider = "userData", dataProviderClass = UserDataProvider.class, priority = 2)
    @Description("Test to get an existing user from the Pet Store API")
    @Story("Read User")
    public void testGetUserById(int id, String username, String firstName, String lastName, String email, String password, String phone, int userStatus) {

        logStep("Sending GET request to fetch user details");
        // GET request to fetch the user by ID
        Response response = RestAssured.given()
                .baseUri(BASE_URL)
                .log().all()
                .get("/user/" + username); // Using the username to retrieve user

        response.then().log().all();
        Assert.assertEquals(response.statusCode(), 200,"Verify the status code is 200");
        Assert.assertEquals(response.jsonPath().getInt("id"), id);
    }
    //Update user details
    @Test(dataProvider = "userData", dataProviderClass = UserDataProvider.class, priority = 3)
    @Description("Test to update an existing user in the Pet Store API")
    @Story("Update User")
    public void testUpdateUser(int id, String username, String firstName, String lastName, String email, String password, String phone, int userStatus) {
        // Prepare updated user data
        String requestBody = String.format("""
        {
            "id": %d,
            "username": "%s",
            "firstName": "%s",
            "lastName": "%s",
            "email": "%s",
            "password": "%s",
            "phone": "%s",
            "userStatus": %d
        }
    """, id, username, firstName + "Updated", lastName + "Updated", email, password, phone, userStatus);

        logStep("Sending PUT request to update user");
        // PUT request to update the user
        Response response = RestAssured.given()
                .baseUri(BASE_URL)
                .header("Content-Type", "application/json")
                .body(requestBody)
                .log().all()
                .put("/user/" + username); // Using the username to identify the user to update

        response.then().log().all();
        Assert.assertEquals(response.statusCode(), 200,"Verify the status code is 200");
        Assert.assertEquals(response.jsonPath().getInt("id"), id);
        Assert.assertEquals(response.jsonPath().getString("firstName"), firstName + "Updated");
    }
//    /// delete user
    @Test(dataProvider = "userData", dataProviderClass = UserDataProvider.class, priority = 4)
    @Description("Test to delete an existing user from the Pet Store API")
    @Story("Delete User")
    public void testDeleteUser(int id, String username, String firstName, String lastName, String email, String password, String phone, int userStatus) {
        logStep("Sending DELETE request to remove user");
        // DELETE request to remove the user
        Response response = RestAssured.given()
                .baseUri(BASE_URL)
                .log().all()
                .delete("/user/" + username); // Using the username to identify the user to delete

        response.then().log().all();
        Assert.assertEquals(response.statusCode(), 200,"Verify the status code is 200");
    }
    @Step("{0}")
    private void logStep(String message) {
        // Logging helper for Allure
    }
}
