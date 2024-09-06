package global.genesis.cucumber.stepdefinitions;

import com.microsoft.playwright.Page;
import io.cucumber.java.*;

import java.net.Socket;

import static permissions.utilities.allure_utitilities.AllureReportListener.addAttachment;
import static permissions.utilities.allure_utitilities.AllureReportListener.addScreenshot;
import static permissions.utilities.config_utilities.ConfigReader.readProperty;
import static permissions.utilities.playwright_driver.PlaywrightDriver.*;

public class Hooks {
    private static boolean isApiScenario(Scenario scenario) {
        return scenario.getSourceTagNames().stream().anyMatch(t -> t.contains("@API"));
    }

    private static boolean isUiAvailable() {
        try (Socket ignored = new Socket("localhost", 6060)) {
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @BeforeAll
    public static void beforeAll() {
        if (isUiAvailable()) {
            createPlaywright();
        }
    }

    @AfterAll
    public static void afterAll() {
        if (isUiAvailable()) {
            closePlaywright();
        }
    }

    @Before
    public void setup(Scenario scenario) {
        if (!isApiScenario(scenario)) {
            Page page = getPage();
            page.navigate(readProperty("defaultHost"));
        }
    }

    @After
    public void teardown(Scenario scenario) {
        if (scenario.isFailed()) {
            addAttachment();
            addScreenshot(scenario);
        }
        if (!isApiScenario(scenario)) closePage();
    }
}