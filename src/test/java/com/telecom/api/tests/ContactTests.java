package com.telecom.api.tests;

import com.telecom.api.base.BaseTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class ContactTests extends BaseTest {

	@Test(priority = 1)
	public void testAddContact() {
		String uniqueEmail = "jdoe" + System.currentTimeMillis() + "@fake.com";

		String requestBody = "{\n" + "  \"firstName\": \"John\",\n" + "  \"lastName\": \"Doe\",\n"
				+ "  \"birthdate\": \"1970-01-01\",\n" + "  \"email\": \"" + uniqueEmail + "\",\n"
				+ "  \"phone\": \"8005555555\",\n" + "  \"street1\": \"1 Main St.\",\n"
				+ "  \"street2\": \"Apartment A\",\n" + "  \"city\": \"Anytown\",\n" + "  \"stateProvince\": \"KS\",\n"
				+ "  \"postalCode\": \"12345\",\n" + "  \"country\": \"USA\"\n" + "}";

		// Send POST request
		Response response = given().header("Authorization", getAuthHeader()).contentType(ContentType.JSON)
				.body(requestBody).when().post("/contacts");

		// Print response
		printResponse(response);

		// Validate response
		Assert.assertEquals(response.getStatusCode(), 201, "Contact should be created");

		// Save contactId for later tests
		contactId = response.jsonPath().getString("_id");
		Assert.assertNotNull(contactId, "Contact ID must not be null");

		System.out.println("Contact created with ID: " + contactId);
	}

	@Test(priority = 2)
	public void testGetContactList() {
		Response response = given().header("Authorization", getAuthHeader()).when().get("/contacts");

		// Print response
		printResponse(response);

		// Validate status
		Assert.assertEquals(response.getStatusCode(), 200, "Should return 200 OK");

		// Ensure contact list is not empty
		int count = response.jsonPath().getList("$").size();
		Assert.assertTrue(count > 0, "Contact list should not be empty");
	}

	@Test(priority = 3)
	public void testGetContactById() {
		Response response = given().header("Authorization", getAuthHeader()).when().get("/contacts/" + contactId);

		printResponse(response);

		Assert.assertEquals(response.getStatusCode(), 200, "Should return 200 OK");

		// Optional: validate email or first name
		String firstName = response.jsonPath().getString("firstName");
		Assert.assertNotNull(firstName, "First name should not be null");
	}

	@Test(priority = 4)
	public void testUpdateFullContact() {
		String updatedEmail = "amiller" + System.currentTimeMillis() + "@fake.com";

		String requestBody = "{\n" + "  \"firstName\": \"Amy\",\n" + "  \"lastName\": \"Miller\",\n"
				+ "  \"birthdate\": \"1992-02-02\",\n" + "  \"email\": \"" + updatedEmail + "\",\n"
				+ "  \"phone\": \"8005554242\",\n" + "  \"street1\": \"13 School St.\",\n"
				+ "  \"street2\": \"Apt. 5\",\n" + "  \"city\": \"Washington\",\n" + "  \"stateProvince\": \"QC\",\n"
				+ "  \"postalCode\": \"A1A1A1\",\n" + "  \"country\": \"Canada\"\n" + "}";

		Response response = given().header("Authorization", getAuthHeader()).contentType(ContentType.JSON)
				.body(requestBody).when().put("/contacts/" + contactId);

		printResponse(response);

		Assert.assertEquals(response.getStatusCode(), 200, "Should return 200 OK");

		String emailReturned = response.jsonPath().getString("email");
		Assert.assertEquals(emailReturned, updatedEmail, "Email should match updated value");
	}

	@Test(priority = 5)
	public void testUpdatePartialContact() {
		String updatedFirstName = "Anna";

		String requestBody = "{\n" + "  \"firstName\": \"" + updatedFirstName + "\"\n" + "}";

		Response response = given().header("Authorization", getAuthHeader()).contentType(ContentType.JSON)
				.body(requestBody).when().patch("/contacts/" + contactId);

		printResponse(response);

		Assert.assertEquals(response.getStatusCode(), 200, "Should return 200 OK");

		String returnedFirstName = response.jsonPath().getString("firstName");
		Assert.assertEquals(returnedFirstName, updatedFirstName, "First name should be updated");
	}

	@Test(priority = 6)
	public void testLogoutUser() {
		Response response = given().header("Authorization", getAuthHeader()).when().post("/users/logout");

		printResponse(response);

		Assert.assertEquals(response.getStatusCode(), 200, "Logout should return 200 OK");

		// Clear token after logout
		token = null;
		System.out.println("Logged out successfully.");
	}

}
