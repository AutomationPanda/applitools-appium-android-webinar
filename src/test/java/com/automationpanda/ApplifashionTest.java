package com.automationpanda;

import io.appium.java_client.AppiumDriver;
import org.junit.jupiter.api.AfterEach;
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

public class ApplifashionTest {

    private AppiumDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void initDriver() throws IOException {

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "android");
        capabilities.setCapability("appium:automationName", "uiautomator2");
        capabilities.setCapability("appium:platformVersion", "12");
        capabilities.setCapability("appium:deviceName", "Pixel 3a API 32");
        capabilities.setCapability("appium:app", "/Users/automationpanda/Desktop/Applifashion/main-app-debug.apk");
        capabilities.setCapability("appium:appPackage", "com.applitools.applifashion.main");
        capabilities.setCapability("appium:appActivity", "com.applitools.applifashion.main.activities.MainActivity");

        driver = new AppiumDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
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
