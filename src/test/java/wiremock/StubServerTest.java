package wiremock;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.junit.jupiter.api.Assertions.*;

public class StubServerTest {

    private static WireMockServer server;
    private final RestTemplate restTemplate = new RestTemplate();

    @BeforeAll
    static void startServer() {
        server = new WireMockServer(options().dynamicPort());
        server.start();
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }

    @Test
    void testUserListStub() {

        server.stubFor(get(urlEqualTo("/user/get/all"))
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
                "http://localhost:" + server.port() + "/user/get/all",
                String.class
        );

        assertEquals(200, response.getStatusCode().value());
        assertTrue(response.getBody().contains("Test user"));
    }

    @Test
    void testCourseListStub() {

        server.stubFor(get(urlEqualTo("/cource/get/all"))
                .willReturn(okJson("""
                        [
                          {
                            "name": "QA java",
                            "price": 15000
                          }
                        ]
                        """)));

        ResponseEntity<String> response = restTemplate.getForEntity(
                "http://localhost:" + server.port() + "/cource/get/all",
                String.class
        );

        assertEquals(200, response.getStatusCode().value());
        assertTrue(response.getBody().contains("QA java"));
    }

    @Test
    void testUserScoreStub() {

        server.stubFor(get(urlMatching("/user/get/\\d+"))
                .willReturn(okJson("""
                        {
                          "name": "Test user",
                          "score": 78
                        }
                        """)));

        ResponseEntity<String> response = restTemplate.getForEntity(
                "http://localhost:" + server.port() + "/user/get/1",
                String.class
        );

        assertEquals(200, response.getStatusCode().value());
        assertTrue(response.getBody().contains("score"));
    }
}
