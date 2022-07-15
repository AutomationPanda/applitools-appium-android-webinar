package com.automationpanda;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
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

import static org.junit.jupiter.api.Assertions.*;

public class ApiDemosTest {

    private AppiumDriver<MobileElement> driver;
    private WebDriverWait wait;

    @BeforeEach
    public void initDriver() throws IOException {

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "android");
        capabilities.setCapability("automationName", "uiautomator2");
        capabilities.setCapability("platformVersion", "12");
        capabilities.setCapability("deviceName", "Pixel 3a API 32");
        capabilities.setCapability("app", "/Users/automationpanda/Code/scratch/ApiDemos-debug.apk");
        capabilities.setCapability("appPackage", "io.appium.android.apis");
        capabilities.setCapability("appActivity", "io.appium.android.apis.ApiDemos");

        driver = new AppiumDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        wait = new WebDriverWait(driver, 10);
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
