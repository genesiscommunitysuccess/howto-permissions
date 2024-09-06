package permissions.utilities.snapshot;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static permissions.utilities.playwright_driver.PlaywrightDriver.getPage;

public class ImageComparator {
    private static final Logger LOG = LoggerFactory.getLogger(ImageComparator.class);

    public static void captureFullPage(String filePath) {
        getPage().screenshot(new Page.ScreenshotOptions().setPath(Paths.get(filePath)));
        LOG.info("Finished captureFullPage with filePath: {}", filePath);
    }

    public static void captureElement(String locator, String filePath) {
        Locator element = getPage().locator(locator);
        element.screenshot(new Locator.ScreenshotOptions().setPath(Paths.get(filePath)));
        LOG.info("Finished captureElement with locator: {} and filePath: {}", locator, filePath);
    }

    public static boolean compareImages(String image1Path, String image2Path) {
        LOG.info("Starting compareImages with image1Path: {} and image2Path: {}", image1Path, image2Path);
        BufferedImage imgA = null;
        BufferedImage imgB = null;

        try {
            Path path1 = Paths.get(image1Path);
            Path path2 = Paths.get(image2Path);

            if (!Files.exists(path1) || !Files.exists(path2)) {
                LOG.error("One or both image files do not exist: {}, {}", image1Path, image2Path);
                throw new IOException("One or both image files do not exist.");
            }

            imgA = ImageIO.read(new File(image1Path));
            imgB = ImageIO.read(new File(image2Path));
        } catch (IOException e) {
            LOG.error("Error reading images: {} or {}", image1Path, image2Path);
            throw new RuntimeException("Error reading images: " + image1Path + " or " + image2Path, e);
        }

        if (imgA.getWidth() != imgB.getWidth() || imgA.getHeight() != imgB.getHeight()) {
            LOG.info("Images are not the same size: {} and {}", image1Path, image2Path);
            return false;
        }

        for (int y = 0; y < imgA.getHeight(); y++) {
            for (int x = 0; x < imgA.getWidth(); x++) {
                if (imgA.getRGB(x, y) != imgB.getRGB(x, y)) {
                    LOG.info("Images differ at position ({}, {})", x, y);
                    return false;
                }
            }
        }
        LOG.info("Images are identical: {} and {}", image1Path, image2Path);
        return true;
    }
}