package com.automationpanda;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Objects;

public class ApiDemosTest {

    private final static String APPIUM_SERVER_URL = "http://127.0.0.1:4723/wd/hub";
    private final static String CAPABILITIES_PATH = "api-demos-caps.json";

    private AppiumDriver<MobileElement> driver;

    @BeforeEach
    public void initDriver() throws IOException {

        ClassLoader load = getClass().getClassLoader();
        File file = new File(Objects.requireNonNull(load.getResource(CAPABILITIES_PATH)).getFile());
        String jsonString = new String(Files.readAllBytes(file.toPath()));
        Type tokenType = new TypeToken<HashMap<String, String>>(){}.getType();
        HashMap<String, Object> map = new Gson().fromJson(jsonString, tokenType);

        DesiredCapabilities desiredCapabilities = new DesiredCapabilities(map);
        driver = new AppiumDriver<>(new URL(APPIUM_SERVER_URL), desiredCapabilities);
    }

    @AfterEach
    public void quitDriver() {
        driver.quit();
    }

    @Test
    public void logText() {

    }
}
