package com.automationpanda;

import java.io.File;
import java.util.Arrays;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class InputReader {

    private String appBranch;
    private String appPath;
    private String appiumUrl;
    private String appiumDeviceName;
    private String appiumPlatformVersion;
    private String applitoolsApiKey;

    public InputReader() {
        appBranch = null;
        appPath = null;
        appiumUrl = null;
        appiumDeviceName = null;
        appiumPlatformVersion = null;
        applitoolsApiKey = null;
    }

    public String getAppBranch() {
        return appBranch;
    }

    public String getAppPath() {
        return appPath;
    }

    public String getAppiumUrl() {
        return appiumUrl;
    }

    public String getAppiumDeviceName() {
        return appiumDeviceName;
    }

    public String getAppiumPlatformVersion() {
        return appiumPlatformVersion;
    }

    public String getApplitoolsApiKey() {
        return applitoolsApiKey;
    }

    public void readInputs() {

        // Read inputs from environment variables
        appBranch = System.getenv().getOrDefault("APP_BRANCH", "main");
        appiumUrl = System.getenv().getOrDefault("APPIUM_URL", "http://127.0.0.1:4723/wd/hub");
        appiumDeviceName = System.getenv().getOrDefault("APPIUM_DEVICE_NAME", "Pixel 3a API 32");
        appiumPlatformVersion = System.getenv().getOrDefault("APPIUM_PLATFORM_VERSION", "12");
        applitoolsApiKey = System.getenv("APPLITOOLS_API_KEY");

        // Verify that the inputs are okay
        assertTrue(Arrays.asList("main", "dev", "prod").contains(appBranch));
        assertTrue(appiumUrl != null && !appiumUrl.isBlank());
        assertTrue(appiumDeviceName != null && !appiumDeviceName.isBlank());
        assertTrue(appiumPlatformVersion != null && !appiumPlatformVersion.isBlank());
        assertTrue(applitoolsApiKey != null && !applitoolsApiKey.isBlank());

        // Get the apk folder path from the resources directory
        String apkFolderPath = Objects.requireNonNull(
                ApplifashionTest.class.getClassLoader().getResource("instrumented-apk")).getPath();

        // Concatenate the app file path
        appPath = apkFolderPath + File.separator + getAppBranch() + "-app-inst.apk";
    }
}
