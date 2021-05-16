package stepDefinitions;

import cucumber.TestContext;
import cucumber.api.java.en.*;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import middleware.Controller;
import net.minidev.json.JSONObject;
import org.junit.Assert;
import requests.BASE_Request;

import java.util.List;
import java.util.stream.IntStream;


import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**************************
 *  (C) L Somni            *
 ***************************/

public class StepDefs {
    TestContext testContext;
    Response response;
    Controller controller;

    BASE_Request BASE_request = new BASE_Request();

    public StepDefs(TestContext context) {
        testContext = context;
        response = testContext.getResponse();
        controller = testContext.getController();
    }

    @Given("I use {string} API end point")
    public void iUseAPIEndPoint(final String sEndPoint) {
        BASE_request.chooseEndPoint(sEndPoint);
        testContext.setMock(false);
    }


    @When("I make a GET request")
    public void iMakeAGETRequest() {
        response = BASE_request.getRequest();
    }

    @When("I POST a user user {string} {string} {string}")
    public void makeAPostRequest(final String name, final String address, final String email) {
        JSONObject requestParams = new JSONObject();
        requestParams.put("name", name);
        requestParams.put("address", address);
        requestParams.put("email", email);

        response = BASE_request.postRequest(requestParams,"/");
    }




    @Then("I get success response {int} code")
    public void iGetSuccessResponseCode(final int expectedStatusCode) {
        Assert.assertThat(response.statusCode(), is(equalTo(expectedStatusCode)));
        Assert.assertEquals(response.getStatusCode(), SC_OK);
    }

    @And("response contains correct structure for the user end point response")
    public void responseContainsStructure() {
        response.then().body(matchesJsonSchemaInClasspath("users_response_schema.json"));
    }




    @io.cucumber.java.en.And("response contains {string}")
    public void validateCardType (final String cardTypes) {
        assertThat((response.jsonPath().get("eligibleCards")).toString(), containsString(cardTypes));
    }
}
