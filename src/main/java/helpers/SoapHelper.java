package helpers;

import io.restassured.response.Response;
import static io.restassured.RestAssured.*;

public class SoapHelper {

    public Response sendSoapRequest(String url, String body) {
        return given()
                .header("Content-Type", "text/xml")
                .body(body)
                .post(url);
    }
}
