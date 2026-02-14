package wiremock;

import factory.WebDriverFactory;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Objects;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.junit.jupiter.api.Assertions.*;

public class UiTest {

    @Test
    void testFrontend() throws Exception {
        WireMockServer server = new WireMockServer(options().dynamicPort());
        server.start();

        server.stubFor(get(urlEqualTo("/frontend"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "text/html")
                        .withBodyFile("frontend.html")));

        WebDriver driver = new WebDriverFactory().create();

        String host = System.getProperty("run.type", "remote").equals("remote")
                ? "host.docker.internal"
                : "localhost";

        driver.get("http://" + host + ":" + server.port() + "/frontend");

        WebElement title = driver.findElement(By.id("title"));
        Assertions.assertEquals("Stub Frontend", title.getText());

        driver.quit();
        server.stop();

    }
}
