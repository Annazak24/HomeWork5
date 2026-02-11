package wiremock;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

public class StubServerTest {

    @RegisterExtension
    static WireMockExtension wiremock = WireMockExtension.newInstance()
            .options(wireMockConfig().dynamicPort().dynamicHttpsPort())
            .build();

    @Test
    public void setupStubs() {

        // user/get/all
        wiremock.stubFor(get(urlEqualTo("/user/get/all"))
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

        // cource/get/all
        wiremock.stubFor(get(urlEqualTo("/cource/get/all"))
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

        // user/get/{id}
        wiremock.stubFor(get(urlMatching("/user/get/\\d+"))
                .willReturn(okJson("""
                        {
                          "name": "Test user",
                          "score": 78
                        }
                        """)));
    }
}
