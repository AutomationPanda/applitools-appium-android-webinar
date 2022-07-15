package com.automationpanda;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class ApiDemosTest {

    private final static String APPIUM_SERVER_URL = "http://127.0.0.1:4723/wd/hub";
    private final static String CAPABILITIES_PATH = "api-demos-caps.json";

    private AppiumDriver<MobileElement> driver;
    private WebDriverWait wait;

    @BeforeEach
    public void initDriver() throws IOException {

        ClassLoader load = getClass().getClassLoader();
        File file = new File(Objects.requireNonNull(load.getResource(CAPABILITIES_PATH)).getFile());
        String jsonString = new String(Files.readAllBytes(file.toPath()));
        Type tokenType = new TypeToken<HashMap<String, String>>(){}.getType();
        HashMap<String, Object> map = new Gson().fromJson(jsonString, tokenType);

        DesiredCapabilities desiredCapabilities = new DesiredCapabilities(map);
        driver = new AppiumDriver<>(new URL(APPIUM_SERVER_URL), desiredCapabilities);
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
