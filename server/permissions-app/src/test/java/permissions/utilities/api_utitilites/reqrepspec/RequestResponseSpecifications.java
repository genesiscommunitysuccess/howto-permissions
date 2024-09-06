package permissions.utilities.api_utitilites.reqrepspec;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.Matchers;

import static io.restassured.RestAssured.expect;
import static io.restassured.RestAssured.given;
import static permissions.utilities.api_utitilites.constants.Authentication.SESSION_AUTH_TOKEN;
import static permissions.utilities.config_utilities.ConfigReader.readProperty;

public class RequestResponseSpecifications {
    public static RequestSpecification requestSpecification(Object body) {
        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
        try {
            return given()
                    .baseUri(readProperty("defaultAPIHost"))
                    .log().uri()
                    .log().headers()
                    .header("SESSION_AUTH_TOKEN", SESSION_AUTH_TOKEN)
                    .contentType(ContentType.JSON)
                    .body(objectWriter.writeValueAsString(body))
                    .relaxedHTTPSValidation();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static ResponseSpecification responseSpecification() {
        return expect()
                .log().ifError()
                .contentType(ContentType.JSON)
                .statusCode(200)
                .time(Matchers.lessThan(5000L));
    }
}
