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

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.Arrays;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class ApplifashionTest {

    private static String appiumUrl;
    private static DesiredCapabilities capabilities;
    private static Configuration config;
    private static VisualGridRunner runner;

    private AppiumDriver driver;
    private WebDriverWait wait;
    private Eyes eyes;

    private static String readAppiumUrl() {
        return System.getenv().getOrDefault("APPIUM_URL", "http://127.0.0.1:4723/wd/hub");
    }

    private static String readPlatformVersion() {
        return System.getenv().getOrDefault("APPIUM_PLATFORM_VERSION", "12");
    }

    private static String readDeviceName() {
        return System.getenv().getOrDefault("APPIUM_DEVICE_NAME", "Pixel 3a API 32");
    }

    private static String readAppPath() {

        // Read the app branch
        String appVersion = System.getenv().getOrDefault("APP_BRANCH", "main");
        assertTrue(Arrays.asList("main", "dev", "prod").contains(appVersion));

        // Get the apk folder path from the resources directory
        String apkFolderPath = Objects.requireNonNull(
                ApplifashionTest.class.getClassLoader().getResource("instrumented-apk")).getPath();

        // Concatenate and return the app path
        return apkFolderPath + File.separator + appVersion + "-app-inst.apk";
    }

    private static DesiredCapabilities buildCapabilities() {

        // Create capabilities
        // Hard-coding these values is typically not a recommended practice
        // Instead, they should be read from a resource file (like a properties or JSON file)
        // They are set here like this to make this example code simpler
        capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "android");
        capabilities.setCapability("appium:automationName", "uiautomator2");
        capabilities.setCapability("appium:platformVersion", readPlatformVersion());
        capabilities.setCapability("appium:deviceName", readDeviceName());
        capabilities.setCapability("appium:app", readAppPath());
        capabilities.setCapability("appium:appPackage", "com.applitools.applifashion.main");
        capabilities.setCapability("appium:appActivity", "com.applitools.applifashion.main.activities.MainActivity");
        capabilities.setCapability("appium:fullReset", "true");

        // Return the capabilities
        return capabilities;
    }

    @BeforeAll
    public static void setUpAllTests() {

        appiumUrl = readAppiumUrl();
        capabilities = buildCapabilities();

        // Create the runner for the Ultrafast Grid
        // Warning: If you have a free account, then concurrency will be limited to 1
        runner = new VisualGridRunner(new RunnerOptions().testConcurrency(5));

        // Create a configuration for Applitools Eyes
        config = new Configuration();

        // Set the Applitools API key so test results are uploaded to your account
        config.setApiKey("jTr0GGxyKWuiIQNckADfa1104JzFKlvY2cAjlmOsmv1J4110" /*System.getenv("APPLITOOLS_API_KEY")*/);

        // Create a new batch
        config.setBatch(new BatchInfo("Applifashion x NMG"));

        // Add mobile devices to test in the Native Mobile Grid
        // TODO: add tablets once they are ready
        config.addMobileDevices(
                new AndroidDeviceInfo(AndroidDeviceName.Pixel_4, ScreenOrientation.PORTRAIT),
                new AndroidDeviceInfo(AndroidDeviceName.Galaxy_S21_ULTRA, ScreenOrientation.LANDSCAPE));
    }

    @BeforeEach
    public void setUpVisualAi(TestInfo testInfo) throws IOException {

        // Initialize the Appium driver
        driver = new AppiumDriver(new URL(appiumUrl), capabilities);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Initialize Applitools Eyes
        eyes = new Eyes(runner);
        eyes.setConfiguration(config);
        eyes.setIsDisabled(false);

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
        WebElement shoeMainImage = driver.findElement(By.id("com.applitools.applifashion.main:id/shoe_image"));
        wait.until(ExpectedConditions.visibilityOf(shoeMainImage));
        shoeMainImage.click();

        // Wait for the product page to appear
        WebElement shoeProductImage = driver.findElement(By.id("com.applitools.applifashion.main:id/shoe_image_product_page"));
        wait.until(ExpectedConditions.visibilityOf(shoeProductImage));

        // Take a visual snapshot
        eyes.check("Product Page", Target.window().fully());
    }
}
