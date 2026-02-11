package wiremock;

import helpers.HttpHelper;
import io.cucumber.java.en.*;
import io.restassured.response.Response;
import com.github.tomakehurst.wiremock.WireMockServer;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserSteps {

    private WireMockServer wiremock;
    private Response response;

    @Given("stub server is running")
    public void stubServerIsRunning() {

        wiremock = new WireMockServer(options().dynamicPort());
        wiremock.start();

        wiremock.stubFor(get("/user/get/all")
                .willReturn(okJson("""
                        [
                          {
                            "name": "Test user",
                            "cource": "QA",
                            "email": "test@test.test",
                            "age": 23
                          }
                        ]
                        """)));
    }

    @When("I request user list")
    public void requestUserList() {
        HttpHelper helper = new HttpHelper("http://localhost", wiremock.port());
        response = helper.get("/user/get/all");
    }

    @Then("response status should be 200")
    public void responseStatus200() {
        assertEquals(200, response.statusCode());
        wiremock.stop(); // stop server after test
    }
}
