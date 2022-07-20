package com.automationpanda;

import com.applitools.eyes.BatchInfo;
import com.applitools.eyes.appium.Eyes;
import com.applitools.eyes.appium.Target;
import com.applitools.eyes.config.Configuration;
import com.applitools.eyes.visualgrid.model.AndroidDeviceInfo;
import com.applitools.eyes.visualgrid.model.AndroidDeviceName;
import com.applitools.eyes.visualgrid.model.ScreenOrientation;
import com.applitools.eyes.visualgrid.services.RunnerOptions;
import com.applitools.eyes.visualgrid.services.VisualGridRunner;
import io.appium.java_client.AppiumDriver;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;

public class ApplifashionTest {

    private static InputReader inputReader;
    private static Configuration config;
    private static VisualGridRunner runner;

    private AppiumDriver driver;
    private WebDriverWait wait;
    private Eyes eyes;

    @BeforeAll
    public static void setUpAllTests() {

        // Read inputs
        inputReader = new InputReader();
        inputReader.readInputs();

        // Create the runner for the Ultrafast Grid
        // Warning: If you have a free account, then concurrency will be limited to 1
        runner = new VisualGridRunner(new RunnerOptions().testConcurrency(5));

        // Create a configuration for Applitools Eyes
        config = new Configuration();

        // Set the Applitools API key so test results are uploaded to your account
        config.setApiKey(inputReader.getApplitoolsApiKey());

        // Create a new batch
        config.setBatch(new BatchInfo("Applifashion in the NMG"));

        // Add mobile devices to test in the Native Mobile Grid
        config.addMobileDevices(
                new AndroidDeviceInfo(AndroidDeviceName.Galaxy_S21),
                new AndroidDeviceInfo(AndroidDeviceName.Galaxy_Note_10),
                new AndroidDeviceInfo(AndroidDeviceName.Pixel_4));
    }

    @BeforeEach
    public void setUpVisualAi(TestInfo testInfo) throws IOException {

        // Create Appium capabilities
        // Hard-coding these values is typically not a recommended practice
        // Instead, they should be read from a resource file (like a properties or JSON file)
        // They are set here like this to make this example code simpler
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "android");
        capabilities.setCapability("appium:automationName", "uiautomator2");
        capabilities.setCapability("appium:platformVersion", inputReader.getAppiumPlatformVersion());
        capabilities.setCapability("appium:deviceName", inputReader.getAppiumDeviceName());
        capabilities.setCapability("appium:app", inputReader.getAppPath());
        capabilities.setCapability("appium:appPackage", "com.applitools.applifashion.main");
        capabilities.setCapability("appium:appActivity", "com.applitools.applifashion.main.activities.MainActivity");
        capabilities.setCapability("appium:fullReset", "true");

        // Initialize the Appium driver
        driver = new AppiumDriver(new URL(inputReader.getAppiumUrl()), capabilities);
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        // Initialize Applitools Eyes
        eyes = new Eyes(runner);
        eyes.setConfiguration(config);
        eyes.setIsDisabled(false);
        eyes.setForceFullPageScreenshot(true);

        // Open Eyes to start visual testing
        eyes.open(driver, "Applifashion Mobile App", testInfo.getDisplayName());
    }

    @AfterEach
    public void quitDriver() {

        // Quit the Appium driver cleanly
        driver.quit();

        // Close Eyes to tell the server it should display the results
        eyes.closeAsync();
    }

    @Test
    public void mainPage() {

        // Take a visual snapshot
        eyes.check("Main Page", Target.window().fully());
    }

    @Test
    public void productPage() {

        // Tap the first shoe
        final By shoeMainImageLocator = By.id("com.applitools.applifashion.main:id/shoe_image");
        wait.until(ExpectedConditions.presenceOfElementLocated(shoeMainImageLocator));
        driver.findElement(shoeMainImageLocator).click();

        // Wait for the product page to appear
        final By shoeProductImageLocator = By.id("com.applitools.applifashion.main:id/shoe_image_product_page");
        wait.until(ExpectedConditions.presenceOfElementLocated(shoeProductImageLocator));

        // Take a visual snapshot
        eyes.check("Product Page", Target.window().fully());
    }
}
