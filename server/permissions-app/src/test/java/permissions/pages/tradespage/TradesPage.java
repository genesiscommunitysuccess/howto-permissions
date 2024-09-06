package permissions.pages.tradespage;

import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.restassured.response.Response;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static permissions.utilities.file_utilities.FileConstants.generateJSONFilePath;
import static permissions.utilities.file_utilities.WriteReadFile.writeJsonArrayFile;
import static permissions.utilities.functions.DateFunctions.todayDate_yyyy_MM_dd;
import static permissions.utilities.playwright_driver.PlaywrightDriver.getPage;
import static permissions.utilities.file_utilities.WriteReadFile.writeJSON;

public class TradesPage {
    private final Page page;
    private final Locator editButton;
    private final Locator addButton;
    private final Locator deleteButton;
    private final Locator submitButton;
    private final Locator confirmButton;
    private final Locator tradeForm_Version;
    private final Locator tradeForm_CountryName;
    private final Locator tradeForm_Side;
    private final Locator tradeForm_Rate;
    private final Locator tradeForm_Notional;
    private final Locator tradeForm_SettlementDate;
    private final Locator tradeForm_SourceCurrency;
    private final Locator tradeForm_TargetCurrency;
    private final Locator tradeForm_CustomerID;
    private final Locator tradeForm_CustomerName;
    public final String tradeGrid;

    public TradesPage(@NotNull Page page) {
        this.page = page;
        this.editButton = page.locator("button[aria-label=Edit]");
        this.addButton = page.locator("rapid-button.crud-button");
        this.deleteButton = page.locator("rapid-button[aria-label=Delete]");
        this.submitButton = page.locator("rapid-button.submit-button");
        this.confirmButton = page.locator("rapid-button:has-text('Confirm')");
        this.tradeForm_Version = initInput("#VERSION");
        this.tradeForm_CountryName = initRapidCombobox("#COUNTRY_NAME");
        this.tradeForm_Side = initRapidCombobox("#SIDE");
        this.tradeForm_Rate = initInput("#RATE");
        this.tradeForm_Notional = initInput("#NOTIONAL");
        this.tradeForm_SettlementDate = page.locator("rapid-text-field[data-test-id=SETTLEMENT_DATE]");
        this.tradeForm_SourceCurrency = initInput("#SOURCE_CURRENCY");
        this.tradeForm_TargetCurrency = initInput("#TARGET_CURRENCY");
        this.tradeForm_CustomerID = initInput("#CUSTOMER_ID");
        this.tradeForm_CustomerName = initInput("#CUSTOMER_NAME");
        this.tradeGrid = ("div.lm_column");
    }

    private Locator initInput(String locator) {
        return page.locator("input" + locator);
    }

    private Locator initRapidCombobox(String locator) {
        return page.locator("rapid-combobox" + locator);
    }

    public void clickButton(@NotNull String button) {
        switch (button) {
            case "Edit":
                editButton.first().click();
                break;
            case "Add":
                addButton.first().click();
                break;
            case "Delete":
                deleteButton.first().click();
                break;
            case "Submit":
                submitButton.click();
                break;
            case "Confirm":
                confirmButton.click();
                break;
            default:
                throw new IllegalArgumentException("Invalid button: " + button);
        }
    }

    public void validateButtonNotVisible(@NotNull String button) {
        switch (button) {
            case "Edit":
                assertFalse(editButton.isVisible());
                break;
            case "Add":
                assertFalse(addButton.isVisible());
                break;
            case "Delete":
                assertFalse(deleteButton.isVisible());
                break;
            default:
                throw new IllegalArgumentException("Invalid button: " + button);
        }
    }

    private void selectRapidCombobox(@NotNull Locator locator, @NotNull String value) {
        locator.click();
        page.waitForSelector("rapid-option:has-text('" + value + "')").click();
    }

    public void fillTradeForm(@NotNull Map<String, String> tradeDetail) {
        tradeForm_Version.fill(tradeDetail.get("VERSION"));
        selectRapidCombobox(tradeForm_CountryName, tradeDetail.get("COUNTRY_NAME"));
        selectRapidCombobox(tradeForm_Side, tradeDetail.get("SIDE"));
        tradeForm_Rate.click();
        tradeForm_Rate.fill(tradeDetail.get("RATE"));
        tradeForm_Notional.click();
        tradeForm_Notional.fill(tradeDetail.get("NOTIONAL"));
        insertDate();
        tradeForm_SourceCurrency.click();
        tradeForm_SourceCurrency.fill(tradeDetail.get("SOURCE_CURRENCY"));
        tradeForm_TargetCurrency.click();
        tradeForm_TargetCurrency.fill(tradeDetail.get("TARGET_CURRENCY"));
        tradeForm_CustomerID.click();
        tradeForm_CustomerID.fill(tradeDetail.get("CUSTOMER_ID"));
        tradeForm_CustomerName.click();
        tradeForm_CustomerName.fill(tradeDetail.get("CUSTOMER_NAME"));
    }

    private void insertDate(){
        tradeForm_SettlementDate.click();
        tradeForm_SettlementDate.evaluate("element => element.setAttribute('current-value', '" + todayDate_yyyy_MM_dd() + "')");
    }

    public String getTradeForm(String username, String expectedOrActual) {
        List<Map<String, String>> tradeGridList = getTradeGrid();
        String actualFile = generateJSONFilePath(username, "trades-grid", expectedOrActual);
        writeJsonArrayFile(actualFile, tradeGridList);
        return actualFile;
    }

    public static String getTradeJSON(Response response, String username, String endpoint, String expectedOrActual) {
        Map<String, Object> expected = response.jsonPath().get("");
        String expectedFilePath = generateJSONFilePath(username, endpoint, expectedOrActual);
        writeJSON(expectedFilePath, expected);
        return expectedFilePath;
    }

    private @NotNull List<Map<String, String>> getTradeGrid() {
        List<Map<String, String>> tradeGridList = new LinkedList<>();
        Map<String, String> map = new LinkedHashMap<>();
        final String selector = "div.ag-cell-value";
        getPage().waitForTimeout(5000);
        List<ElementHandle> elements = getPage().querySelectorAll(selector);
        for (ElementHandle element : elements) {
            String outerHTML = element.evaluate("element => element.outerHTML").toString();
            String textContent = element.textContent().replace(",", ".");
            if (!textContent.isEmpty()) {
                String columnId = columnIdExtractor(outerHTML);
                if ("TRADE_ID".equals(columnId)) {
                    if (!map.isEmpty()) {
                        tradeGridList.add(new LinkedHashMap<>(map));
                        map.clear();
                    }
                }
                map.put(columnId, textContent);
            }
        }
        if (!map.isEmpty()) {
            tradeGridList.add(new LinkedHashMap<>(map));
        }
        return tradeGridList;
    }

    private String columnIdExtractor(String outerHTML) {
        String patternString = "col-id=\"([^\"]*)\"";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(outerHTML);
        String colId = null;
        if (matcher.find()) {
            colId = matcher.group(1);
        }
        return colId;
    }
}
