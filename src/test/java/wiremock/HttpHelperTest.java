package wiremock;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.junit.jupiter.api.Assertions.*;

public class HttpHelperTest {

    @RegisterExtension
    static WireMockExtension wiremock = WireMockExtension.newInstance()
            .options(wireMockConfig().dynamicPort())
            .build();

    private final RestTemplate restTemplate = new RestTemplate();

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

        ResponseEntity<String> response = restTemplate.getForEntity(
                "http://localhost:" + wiremock.getPort() + "/user/get/all",
                String.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
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

        ResponseEntity<String> response = restTemplate.getForEntity(
                "http://localhost:" + wiremock.getPort() + "/cource/get/all",
                String.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
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

        ResponseEntity<String> response = restTemplate.getForEntity(
                "http://localhost:" + wiremock.getPort() + "/user/get/1",
                String.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }
}
