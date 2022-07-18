package com.automationpanda;

import io.appium.java_client.AppiumDriver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

    private AppiumDriver driver;
    private WebDriverWait wait;

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
    }

    @BeforeEach
    public void initDriver() throws IOException {

        driver = new AppiumDriver(new URL(appiumUrl), capabilities);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterEach
    public void quitDriver() {
        driver.quit();
    }

    @Test
    public void mainPage() {

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
    }
}
