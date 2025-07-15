package com.telecom.api.base;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;

public class BaseTest {

    protected static String baseUrl = "https://thinking-tester-contact-list.herokuapp.com";
    protected static String token;
    protected static String userId;
    protected static String contactId;
    protected static String email;
    protected static String password;

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = baseUrl;
    }

    // Utility method to get Authorization Header
    protected String getAuthHeader() {
        return "Bearer " + token;
    }

    // Optional: You can add response logger if needed
    protected void printResponse(Response response) {
        System.out.println("Status Code: " + response.statusCode());
        System.out.println("Response Body: ");
        response.prettyPrint();
    }
}
