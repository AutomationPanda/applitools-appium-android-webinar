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

import static org.junit.jupiter.api.Assertions.*;

public class ApiDemosTest {

    private AppiumDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void initDriver() throws IOException {

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "android");
        capabilities.setCapability("appium:automationName", "uiautomator2");
        capabilities.setCapability("appium:platformVersion", "12");
        capabilities.setCapability("appium:deviceName", "Pixel 3a API 32");
        capabilities.setCapability("appium:app", "/Users/automationpanda/Code/scratch/ApiDemos-debug.apk");
        capabilities.setCapability("appium:appPackage", "io.appium.android.apis");
        capabilities.setCapability("appium:appActivity", "io.appium.android.apis.ApiDemos");

        driver = new AppiumDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterEach
    public void quitDriver() {
        driver.quit();
    }

    @Test
    public void logText() {

        // Open text
        WebElement textButton = driver.findElement(By.xpath("//android.widget.TextView[@content-desc=\"Text\"]"));
        wait.until(ExpectedConditions.visibilityOf(textButton));
        textButton.click();

        // Tap on log text box
        WebElement logTextBoxButton = driver.findElement(By.xpath("//android.widget.TextView[@content-desc=\"LogTextBox\"]"));
        wait.until(ExpectedConditions.visibilityOf(logTextBoxButton));
        logTextBoxButton.click();

        // Tap on add button
        WebElement addButton = driver.findElement(By.id("io.appium.android.apis:id/add"));
        wait.until(ExpectedConditions.visibilityOf(addButton));
        addButton.click();

        // Get log text
        WebElement logTextButtonArea = driver.findElement(By.id("io.appium.android.apis:id/text"));
        wait.until(ExpectedConditions.visibilityOf(logTextButtonArea));
        String logText = logTextButtonArea.getText().trim();

        // Verify the log text
        assertEquals("This is a test", logText);
    }
}
