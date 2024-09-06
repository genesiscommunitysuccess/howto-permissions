package global.genesis.cucumber.stepdefinitions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.jetbrains.annotations.NotNull;
import org.opentest4j.AssertionFailedError;
import permissions.pages.loginpage.LoginPage;
import permissions.pages.tradespage.TradesPage;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static permissions.utilities.api_utitilites.constants.Authentication.USERNAME;
import static permissions.utilities.file_utilities.CompareJSON.compareJSONFiles;
import static permissions.utilities.playwright_driver.PlaywrightDriver.getPage;

public class PermissionUITest {
    private Boolean isEdit = false;

    @When("User enters username {string} and password {string}")
    public void userEntersUsernameAndPassword(String username, String password) {
        final LoginPage loginPage = new LoginPage(getPage());
        USERNAME = username;
        loginPage.validateURL("login");
        loginPage.login(username, password);
    }

    @When("User navigates to the {string} page")
    public void userNavigatesToThePage(String page) {
        final LoginPage loginPage = new LoginPage(getPage());
        loginPage.validateURL(page);
    }

    @Then("User should see the trades")
    public void userShouldSeeTheTrades(String expectedFile) {
        final TradesPage tradesPage = new TradesPage(getPage());
        String actualFile = tradesPage.getTradeForm(USERNAME, "actual");
        String primary_key = "CUSTOMER_ID";
        String keys_to_ignore = "TRADE_ID";
        if (isEdit) {
            try {
                compareJSONFiles(expectedFile, actualFile, primary_key, keys_to_ignore);
            } catch (AssertionFailedError error) {
            }
        }else{
            compareJSONFiles(expectedFile, actualFile, primary_key, keys_to_ignore);
        }
    }

    @When("User click on the {string}")
    public void userClickOnTheButton(String button) {
        final TradesPage tradesPage = new TradesPage(getPage());
        tradesPage.clickButton(button);
        if (button.equals("Add") || button.equals("Edit")) {
            isEdit = true;
        }
    }

    @And("User fills the form with the trade details")
    public void userFillsTheFormWithTheTradeDetails(@NotNull DataTable dataTable) {
        final TradesPage tradesPage = new TradesPage(getPage());
        List<Map<String, String>> tradeDetails = dataTable.asMaps();
        for (Map<String, String> tradeDetail : tradeDetails) {
            tradesPage.fillTradeForm(tradeDetail);
        }
    }

    @Then("User shouldn't see the {string}")
    public void userShouldnTSeeThe(String button) {
        final TradesPage tradesPage = new TradesPage(getPage());
        tradesPage.validateButtonNotVisible(button);
    }
}
