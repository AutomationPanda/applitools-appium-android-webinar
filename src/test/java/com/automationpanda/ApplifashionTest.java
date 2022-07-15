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

import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class ApplifashionTest {

    private static String appiumUrl;
    private static DesiredCapabilities capabilities;

    private AppiumDriver driver;
    private WebDriverWait wait;

    private static DesiredCapabilities buildCapabilities() {

        // Read and build app path
        String appVersion = System.getenv().getOrDefault("APP_BRANCH", "main");
        assertTrue(Arrays.asList("main", "dev", "prod").contains(appVersion));
        String appPath = "/Users/automationpanda/Desktop/Applifashion/" + appVersion + "-app-debug.apk";

        // Read inputs from environment variables
        String platformVersion = System.getenv().getOrDefault("APPIUM_PLATFORM_VERSION", "12");
        String deviceName = System.getenv().getOrDefault("APPIUM_DEVICE_NAME", "Pixel 3a API 32");

        // Create capabilities
        capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "android");
        capabilities.setCapability("appium:automationName", "uiautomator2");
        capabilities.setCapability("appium:platformVersion", platformVersion);
        capabilities.setCapability("appium:deviceName", deviceName);
        capabilities.setCapability("appium:app", appPath);
        capabilities.setCapability("appium:appPackage", "com.applitools.applifashion.main");
        capabilities.setCapability("appium:appActivity", "com.applitools.applifashion.main.activities.MainActivity");

        // Return the capabilities
        return capabilities;
    }

    @BeforeAll
    public static void setUpAllTests() {

        // Read the Appium URL
        appiumUrl = System.getenv().getOrDefault("APPIUM_URL", "http://127.0.0.1:4723/wd/hub");

        // Create the capabilities
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