package permissions.pages.loginpage;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static permissions.utilities.config_utilities.ConfigReader.readProperty;

public class LoginPage {
    private final Page page;
    private final Locator usernameField;
    private final Locator passwordField;
    private final Locator loginButton;

    public LoginPage(Page page) {
        this.page = page;
        this.usernameField = page.locator("[data-test-id=username] input");
        this.passwordField = page.locator("[data-test-id=password] input");
        this.loginButton = page.locator("[data-test-id=submit-login] button");
    }

    public void validateURL(String url) {
        assertThat(page).hasURL(readProperty("defaultHost") + url);
    }

    public void login(String username, String password) {
        usernameField.fill(username);
        passwordField.fill(password);
        loginButton.click();
    }
}
