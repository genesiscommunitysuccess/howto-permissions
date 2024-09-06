package global.genesis.cucumber.stepdefinitions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.jetbrains.annotations.NotNull;
import permissions.utilities.api_utitilites.dataserver.DataServer;
import permissions.utilities.api_utitilites.events.authentication.EventLoginAuth;
import permissions.utilities.api_utitilites.events.eventhandler.EventHandler;
import permissions.utilities.api_utitilites.reqrep.ReqRep;

import java.util.List;
import java.util.Map;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static permissions.pages.tradespage.TradesPage.getTradeJSON;
import static permissions.utilities.api_utitilites.constants.Authentication.*;
import static permissions.utilities.file_utilities.CompareJSON.compareJSONFiles;
import static permissions.utilities.file_utilities.FileConstants.generateJSONFilePath;
import static permissions.utilities.file_utilities.WriteReadFile.writeJSON;

public class PermissionAPITest {
    private String username;
    private String endpoint;
    private String queryParam;
    private Response response;

    @Given("User connect with username {string} and password {string}")
    public void user_connect_with_username_and_password(String username, String password) {
        this.username = username;
        EventLoginAuth eventLoginAuth = new EventLoginAuth(username, password);
        Response response = eventLoginAuth.post();
        SESSION_AUTH_TOKEN = response.jsonPath().get("SESSION_AUTH_TOKEN");
        response.then().assertThat().body(matchesJsonSchemaInClasspath(LOGIN_SCHEMA_PATH));
    }

    @When("User sends request to dataserver gets response body {string}")
    public void user_sends_request_to_dataserver_with_endpoint(String endpoint) {
        this.endpoint = endpoint;
        DataServer dataServer = new DataServer(endpoint);
        Response response = dataServer.post();
    }

    @When("User compares it to expected {string}, {string} and, {string}")
    public void user_compares_it_to_expected(String result, String primary_key, String keys_to_ignore) {
        if (endpoint.contains("ALL")) {
            DataServer dataServer = new DataServer(endpoint);
            response = dataServer.post();
        }
        if (endpoint.contains("RR")) {
            ReqRep reqRep = new ReqRep(endpoint, queryParam);
            response = reqRep.get();
        }
        String actualFilePath = getTradeJSON(response, USERNAME, endpoint, "actual");
        compareJSONFiles(result, actualFilePath, primary_key , keys_to_ignore);
    }

    @When("User send the event {string}")
    public void userSendsTheEvent(String event, @NotNull DataTable dataTable) {
        List<Map<String, String>> tradeDetails = dataTable.asMaps();
        tradeDetails.forEach(tradeDetail -> {
            if (tradeDetail.get("EVENT").equals(event)) {
                response = EventHandler.handleEvent(event, tradeDetail);
            }
        });
    }

    @Then("User verifies the event successfully {string} processed")
    public void userVerifiesTheEventHandlerRequestSuccessful(String endpoint) {
        EventHandler.verifyEvent(endpoint);
    }

    @When("User sends request to {string} with {string}")
    public void userSendsRequestToWith(String endpoint, String queryParam) {
        this.queryParam = queryParam;
        this.endpoint = endpoint;
        ReqRep reqRep = new ReqRep(endpoint, queryParam);
        response = reqRep.get();
    }
}
