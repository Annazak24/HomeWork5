package helpers;

import io.restassured.response.Response;
import static io.restassured.RestAssured.*;

public class HttpHelper {

    private final String baseUrl;
    private final int port;

    public HttpHelper(String baseUrl, int port) {
        this.baseUrl = baseUrl;
        this.port = port;
    }

    public Response get(String endpoint) {
        return given()
                .baseUri(baseUrl)
                .port(port)
                .when()
                .get(endpoint);
    }
}
