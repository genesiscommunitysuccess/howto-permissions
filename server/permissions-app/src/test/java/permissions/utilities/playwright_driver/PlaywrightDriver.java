package permissions.utilities.playwright_driver;

import com.microsoft.playwright.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static permissions.utilities.config_utilities.ConfigReader.readProperty;

public abstract class PlaywrightDriver {
    private static final Logger LOG = LoggerFactory.getLogger(PlaywrightDriver.class);
    private static final String browserName = System.getProperty("browser") == null ? readProperty("browser") : System.getProperty("browser");
    private static final ThreadLocal<Playwright> playwrightThread = new ThreadLocal<>();
    private static final ThreadLocal<Browser> browserThread = new ThreadLocal<>();
    private static final ThreadLocal<BrowserContext> browserContextThread = new ThreadLocal<>();
    private static final ThreadLocal<Page> pageThread = new ThreadLocal<>();
    private static Playwright playwright;
    private static Browser browser;
    private static BrowserContext context;
    public static Page page;

    private PlaywrightDriver() {
    }

    public static synchronized void createPlaywright() {
        LOG.info("Launching Playwright");
        playwright = Playwright.create();
        playwrightThread.set(playwright);
        playwrightThread.get();
    }

    public static synchronized void closePlaywright() {
        LOG.info("Closing Playwright");
        if (playwright != null) {
            playwright.close();
            playwrightThread.remove();
        }
    }

    public static synchronized Page getPage() {
        if (browserThread.get() == null) {
            LOG.info("Getting Page");
            page = createPage().get().newPage();
            pageThread.set(page);
        }
        return pageThread.get();
    }

    public static void closePage() {
        LOG.info("Closing Page");
        if (pageThread.get() != null) {
            browser.close();
            browserThread.remove();
        }
    }

    private static synchronized ThreadLocal<BrowserContext> createPage() {
        switch (browserName.toLowerCase()) {
            case "firefox":
                browser = playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(false));
                break;
            case "firefox-headless":
                browser = playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(true));
                break;
            case "chrome":
                browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
                break;
            case "chrome-headless":
                browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
                break;
            case "edge":
                browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setChannel("msedge").setHeadless(false));
                break;
            case "edge-headless":
                browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setChannel("msedge").setHeadless(true));
                break;
            default:
                LOG.error("The {} browser not found", browserName);
                throw new IllegalStateException("Unexpected value: " + browserName);
        }
        context = browser.newContext(new Browser
                .NewContextOptions()
                .setIgnoreHTTPSErrors(true)
        );
        browserThread.set(browser);
        browserContextThread.set(context);
        return browserContextThread;
    }
}
