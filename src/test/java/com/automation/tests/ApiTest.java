package com.automation.tests;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.RequestOptions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * ApiTest - Demonstrates Playwright's built-in API testing capability.
 *
 * Playwright provides APIRequestContext which allows you to send HTTP requests
 * (GET, POST, PUT, DELETE etc.) without opening a browser.
 *
 * This is very powerful because you can:
 * 1. Do pure API testing
 * 2. Mix UI + API testing in the same test (e.g. create data via API, then verify in UI)
 */
public class ApiTest {

    private Playwright playwright;
    private APIRequestContext apiContext;

    /**
     * Runs once before all tests in this class.
     * Creates Playwright instance and APIRequestContext.
     */
    @BeforeClass
    public void setUp() {
        // Create Playwright instance
        playwright = Playwright.create();

        // Create APIRequestContext (this is Playwright's built-in API testing tool)
        // We set baseURL so we don't have to write full URL every time
        apiContext = playwright.request().newContext(new APIRequest.NewContextOptions()
                .setBaseURL("https://reqres.in")          // Popular free API for practice
                .setExtraHTTPHeaders(java.util.Map.of(
                        "Content-Type", "application/json",
                        "x-api-key", "reqres-free-v1"     // Required by reqres.in
                )));
    }

    /**
     * Test 1: Simple GET request - Fetch list of users
     */
    @Test(description = "GET - Fetch list of users and verify status code & response")
    public void testGetUsers() {
        // Send GET request to /api/users?page=2
        APIResponse response = apiContext.get("/api/users?page=2");

        // ========== Assertions ==========
        // 1. Verify status code is 200
        Assert.assertEquals(response.status(), 200, "Status code should be 200");

        // 2. Verify response is OK
        Assert.assertTrue(response.ok(), "Response should be OK");

        // 3. Print response body (for learning/debugging)
        System.out.println("GET Users Response: " + response.text());

        // 4. Verify response contains expected text
        Assert.assertTrue(response.text().contains("email"), "Response should contain email field");
    }

    /**
     * Test 2: GET single user by ID
     */
    @Test(description = "GET - Fetch single user by ID")
    public void testGetSingleUser() {
        APIResponse response = apiContext.get("/api/users/2");

        Assert.assertEquals(response.status(), 200);
        Assert.assertTrue(response.text().contains("janet.weaver@reqres.in"));
        System.out.println("Single User Response: " + response.text());
    }

    /**
     * Test 3: POST request - Create a new user
     */
    @Test(description = "POST - Create a new user")
    public void testCreateUser() {
        // Request body in JSON format
        String requestBody = """
                {
                    "name": "Balibhadra",
                    "job": "Playwright Automation Tester"
                }
                """;

        // Send POST request
        APIResponse response = apiContext.post("/api/users",
                RequestOptions.create().setData(requestBody));

        // Assertions
        Assert.assertEquals(response.status(), 201, "Status code should be 201 Created");
        Assert.assertTrue(response.text().contains("Balibhadra"));
        Assert.assertTrue(response.text().contains("Playwright Automation Tester"));

        System.out.println("Create User Response: " + response.text());
    }

    /**
     * Test 4: PUT request - Update an existing user
     */
    @Test(description = "PUT - Update an existing user")
    public void testUpdateUser() {
        String requestBody = """
                {
                    "name": "Balibhadra Singh",
                    "job": "SDET"
                }
                """;

        APIResponse response = apiContext.put("/api/users/2",
                RequestOptions.create().setData(requestBody));

        Assert.assertEquals(response.status(), 200);
        Assert.assertTrue(response.text().contains("Balibhadra Singh"));
        System.out.println("Update User Response: " + response.text());
    }

    /**
     * Runs once after all tests.
     * Closes API context and Playwright to free resources.
     */
    @AfterClass
    public void tearDown() {
        if (apiContext != null) {
            apiContext.dispose();
        }
        if (playwright != null) {
            playwright.close();
        }
    }
}
