package wiremock;

import helpers.HttpHelper;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

public class HttpHelperTest {

    @RegisterExtension
    static WireMockExtension wiremock = WireMockExtension.newInstance()
            .options(wireMockConfig().dynamicPort())
            .build();

    @Test
    void testUserList() {

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

        HttpHelper helper = new HttpHelper("http://localhost", wiremock.getPort());

        helper.get("/user/get/all")
                .then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/user-schema.json"));
        ;
    }

    @Test
    void testCourseList() {

        wiremock.stubFor(get("/cource/get/all")
                .willReturn(okJson("""
                        [
                          {
                            "name": "QA java",
                            "price": 15000
                          },
                          {
                            "name": "Java",
                            "price": 12000
                          }
                        ]
                        """)));

        HttpHelper helper = new HttpHelper("http://localhost", wiremock.getPort());

        helper.get("/cource/get/all")
                .then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/course-schema.json"));
    }

    @Test
    void testUserScore() {

        wiremock.stubFor(get(urlPathMatching("/user/get/\\d+"))
                .willReturn(okJson("""
                        {
                          "name": "Test user",
                          "score": 78
                        }
                        """)));

        HttpHelper helper = new HttpHelper("http://localhost", wiremock.getPort());

        helper.get("/user/get/1")
                .then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/score-schema.json"));
    }
}
