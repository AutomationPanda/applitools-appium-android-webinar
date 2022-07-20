# applitools-appium-android-webinar

This repository contains an example Java project showing how to use
[Applitools Native Mobile Grid](https://applitools.com/platform/native-mobile-grid/)
with Appium for Android apps.
It is my first Appium test automation project!


## Influences

I learned how to start testing with Appium from
[Gaurav Singh](https://twitter.com/automationhacks)'s guides and example code below:

* [Hello Appium, Part 1: What is Appium? An Introduction to Appium and its Tooling](https://applitools.com/blog/what-is-appium-introduction-to-appium/)
* [Hello Appium, Part 2: Writing Your First Android Test](https://applitools.com/blog/how-to-write-android-test-appium/)
* [Appium Fast Boilerplate GitHub repository](https://github.com/automationhacks/appium-fast-boilerplate)


## Workbench Setup

Setting up an Appium project is no small task.
You'll need to install and configure the following:

* A Java IDE like [IntelliJ IDEA](https://www.jetbrains.com/idea/) or [Eclipse](https://www.eclipse.org/)
* [Java Development Kit](https://www.oracle.com/java/technologies/downloads/) (JDK)
* [Node.js](https://nodejs.org/)
* [Appium](https://appium.io/) 1.22.x
* [Appium Doctor](https://github.com/appium/appium-doctor)
* [Android Studio](https://developer.android.com/studio)
* An [Android emulator](https://developer.android.com/studio/run/emulator)
* [Appium Inspector](https://github.com/appium/appium-inspector)

Follow Gaurav's guides listed above for instructions on setting up everything.

*Warning:* Setup might take several hours due to large download sizes.


## Instrumented App Builds

The Android mobile app under test is the Applifashion demo app.
Three versions of the app are located under `src/test/resources/instrumented-apk`:

1. A *main* branch that represents the starting point for testing.
2. A *dev* branch that represents changes made to the app (with bugs).
3. A *prod* branch that represents the changes with bug fixes ready for deployment.

The .apk files for each version have already been instrumented for testing with Applitools Native Mobile Grid.


## Test Inputs

The tests require the following environment variables as inputs:

* `APP_BRANCH`
  * The branch of the app to target from `src/test/resources/instrumented-apk`
  * Must be `main`, `dev`, or `prod`
  * Defaults to `main`
* `APPIUM_URL`
  * The URL for the Appium driver
  * Defaults to `http://127.0.0.1:4723/wd/hub`
* `APPIUM_DEVICE_NAME`
  * The name of the local Android device to target
  * Defaults to `Pixel 3a API 32`
* `APPIUM_PLATFORM_VERSION`
  * The Android platform version for the target device
  * Defaults to `12`
* `APPLITOOLS_API_KEY`
  * The Applitools API key for the user's account
  * Register for a free Applitools account [here](https://auth.applitools.com/users/register)
  * Required


## Test Execution

Before launching tests, make sure:

* Appium is running
* The target Android device is running
* The environment variables for test inputs are set

The test class is `ApplifashionTest`.
It is located at `src/test/java/com/automationpanda/ApplifashionTest.java`.

The test can be run from within an IDE or from the command line.
To run from the command line, run the following Gradle directive:

```
:test --tests "com.automationpanda.ApplifashionTest"
```
