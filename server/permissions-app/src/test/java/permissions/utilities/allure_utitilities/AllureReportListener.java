package permissions.utilities.allure_utitilities;

import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import permissions.utilities.file_utilities.CompareJSON;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Optional;

import static permissions.utilities.api_utitilites.constants.Authentication.USERNAME;
import static permissions.utilities.snapshot.ImageComparator.captureFullPage;

public class AllureReportListener {
    private static final Logger LOG = LoggerFactory.getLogger(AllureReportListener.class);

    // This method is used to add the latest file in the actual directory as an attachment
    public static void addAttachment() {
        if (Files.exists(Paths.get("src/test/resources/result/" + USERNAME + "/actual"))) {
            try {
                Optional<Path> latestFilePath = Files.list(Paths.get("src/test/resources/result/" + USERNAME + "/actual"))
                        .filter(Files::isRegularFile)
                        .max(Comparator.comparingLong(p -> p.toFile().lastModified()));

                if (latestFilePath.isPresent()) {
                    Path filePath = latestFilePath.get();
                    Allure.attachment(filePath.getFileName().toString(), new String(Files.readAllBytes(filePath)));
                }
            } catch (IOException e) {
                LOG.error("Error adding attachment to allure report", e);
            }
        }
    }

    // This method is used to add a screenshot to the allure report
    public static void addScreenshot(@NotNull Scenario scenario) {
        String screenshotPath = "build/screenshots/" + scenario.getName() + ".png";
        captureFullPage(screenshotPath);
        try (InputStream is = Files.newInputStream(Paths.get(screenshotPath))) {
            Allure.attachment(scenario.getName() + ".png", is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
