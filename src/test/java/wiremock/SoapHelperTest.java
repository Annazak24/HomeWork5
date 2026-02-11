package wiremock;

import helpers.SoapHelper;
import org.junit.jupiter.api.Test;

public class SoapHelperTest {

    @Test
    void testSoap() {

        String body = """
            <?xml version="1.0" encoding="utf-8"?>
            <soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                           xmlns:xsd="http://www.w3.org/2001/XMLSchema"
                           xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
              <soap:Body>
                <NumberToWords xmlns="http://www.dataaccess.com/webservicesserver/">
                  <ubiNum>100</ubiNum>
                </NumberToWords>
              </soap:Body>
            </soap:Envelope>
            """;

        SoapHelper helper = new SoapHelper();

        helper.sendSoapRequest(
                        "https://www.dataaccess.com/webservicesserver/NumberConversion.wso",
                        body)
                .then()
                .statusCode(200);
    }

}
