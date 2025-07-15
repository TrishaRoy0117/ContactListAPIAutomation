package com.telecom.api.tests;

import com.telecom.api.base.BaseTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.UUID;

import static io.restassured.RestAssured.given;

public class UserTests extends BaseTest {

	@Test(priority = 1)
	public void testAddNewUser() {
		// Unique email to avoid duplicate error
		String randomEmail = "user_" + UUID.randomUUID().toString().substring(0, 6) + "@fake.com";

		// Request Body
		String requestBody = "{\n" + "  \"firstName\": \"Test\",\n" + "  \"lastName\": \"User\",\n" + "  \"email\": \""
				+ randomEmail + "\",\n" + "  \"password\": \"myPassword\"\n" + "}";

		// Send POST request
		Response response = given().contentType(ContentType.JSON).body(requestBody).when().post("/users");

		// Print response (optional)
		printResponse(response);

		// Assertions
		Assert.assertEquals(response.getStatusCode(), 201, "Status code should be 201 Created");
		Assert.assertTrue(response.getBody().asString().contains("token"), "Response should contain a token");

		// Extract and store token
		token = response.jsonPath().getString("token");
		userId = response.jsonPath().getString("user._id");

		System.out.println("User created with email: " + randomEmail);
		System.out.println("Token: " + token);
	}

	@Test(priority = 2)
	public void testGetUserProfile() {
		// Send GET request with token
		Response response = given().header("Authorization", getAuthHeader()).when().get("/users/me");

		// Print response
		printResponse(response);

		// Assertions
		Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200 OK");
		String returnedEmail = response.jsonPath().getString("email");
		Assert.assertTrue(returnedEmail.endsWith("@fake.com"), "Email should be valid: " + returnedEmail);
	}

	@Test(priority = 3)
	public void testUpdateUser() {
		// Updated user details
		String updatedEmail = "updated_" + System.currentTimeMillis() + "@fake.com";
		String updatedPassword = "newPassword123";

		String requestBody = "{\n" + "  \"firstName\": \"Updated\",\n" + "  \"lastName\": \"UserNew\",\n"
				+ "  \"email\": \"" + updatedEmail + "\",\n" + "  \"password\": \"" + updatedPassword + "\"\n" + "}";

		// Send PATCH request to update user
		Response response = given().header("Authorization", getAuthHeader()).contentType(ContentType.JSON)
				.body(requestBody).when().patch("/users/me");

		// Print response
		printResponse(response);

		// Assert status
		Assert.assertEquals(response.getStatusCode(), 200, "Status should be 200 OK");

		// Check updated email
		String responseEmail = response.jsonPath().getString("email");
		Assert.assertEquals(responseEmail, updatedEmail, "Email should be updated");

		// Store updated email/password for login test
		BaseTest.email = updatedEmail;
		BaseTest.password = updatedPassword;
	}

}
