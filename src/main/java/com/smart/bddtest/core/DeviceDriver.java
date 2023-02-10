package com.smart.bddtest.core;

import com.smart.bddtest.core.exception.PlatformNotSupportException;
import com.smart.bddtest.core.spring.properties.models.ApplicationParams;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidStartScreenRecordingOptions;
import io.appium.java_client.android.AndroidStopScreenRecordingOptions;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSStartScreenRecordingOptions;
import io.appium.java_client.ios.IOSStopScreenRecordingOptions;
import io.appium.java_client.screenrecording.BaseStartScreenRecordingOptions;
import io.appium.java_client.screenrecording.BaseStopScreenRecordingOptions;
import io.appium.java_client.screenrecording.CanRecordScreen;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

@Slf4j
public abstract class DeviceDriver {

    @Autowired
    private ApplicationParams applicationParams;

    private Platform platform;

    private AppiumDriver<WebElement> appiumDriver;

    public boolean isSupportPlatForm() {
        return platform != null;
    }

    public synchronized void start() {
        switch (platform) {
            case ANDROID:
                startAosDriver();
                break;
            case IOS:
                startIosDriver();
                break;
            default:
                throw new RuntimeException(platform + " platform not found");
        }
    }

    public synchronized void startIfNeed() {
        if (appiumDriver == null) {
            start();
        }
    }

    public void launchApp() {
        checkDriveStart();
        log.info("launchApp");
        if (platform == Platform.ANDROID) {
            String pageSource = appiumDriver.getPageSource();
            if (pageSource == null || !pageSource.contains(applicationParams.getAosPackage())) {
                appiumDriver.launchApp();
            }
        } else {
            appiumDriver.launchApp();
        }
    }

    public void closeApp() {
        if (appiumDriver == null) {
            return;
        }
        log.info("closeApp");
        try {
            if (platform == Platform.IOS) {
                appiumDriver.terminateApp(applicationParams.getIosPackage());
            }
            appiumDriver.closeApp();
        } catch (Exception e) {
            //
        }
    }

    public void quitAppiumDriver() {
        if (appiumDriver != null) {
            closeApp();
            appiumDriver.quit();
            appiumDriver = null;
        }
    }

    public AppiumDriver<WebElement> getAppiumDriver() {
        checkDriveStart();
        return appiumDriver;
    }

    public Dimension getScreenSize() {
        checkDriveStart();
        return appiumDriver.manage().window().getSize();
    }

    public List<LogEntry> getAppLogs() {
        checkDriveStart();
        LogEntries logEntries;
        switch (platform) {
            case ANDROID:
                logEntries = appiumDriver.manage().logs().get("logcat");
                break;
            case IOS:
                logEntries = appiumDriver.manage().logs().get("syslog");
                break;
            default:
                throw new PlatformNotSupportException(null);
        }
        return logEntries.getAll();
    }

    public String startRecordingScreen() {
        checkDriveStart();
        BaseStartScreenRecordingOptions options = null;
        if (appiumDriver instanceof AndroidDriver) {
            options = new AndroidStartScreenRecordingOptions();
        } else if (appiumDriver instanceof IOSDriver) {
            options = new IOSStartScreenRecordingOptions();
        }
        if (options == null) {
            return null;
        }

        try {
            return ((CanRecordScreen) appiumDriver).startRecordingScreen(options);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String stopRecordingScreen() {
        checkDriveStart();
        BaseStopScreenRecordingOptions options = null;
        if (appiumDriver instanceof AndroidDriver) {
            options = new AndroidStopScreenRecordingOptions();
        } else if (appiumDriver instanceof IOSDriver) {
            options = new IOSStopScreenRecordingOptions();
        }
        if (options == null) {
            return null;
        }

        try {
            return ((CanRecordScreen) appiumDriver).stopRecordingScreen(options);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Value("${platform}")
    private void setPlatform(String platform) {
        switch (platform.toLowerCase()) {
            case "android":
            case "aos":
                this.platform = Platform.ANDROID;
                break;
            case "ios":
                this.platform = Platform.IOS;
                break;
            default:
                log.info("DeviceDriver not support platform : " + platform);
                break;
        }
    }

    protected void startAosDriver() {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setPlatform(platform);
        desiredCapabilities.setCapability("automationName", "UIAutomator2");
        desiredCapabilities.setCapability("noReset", true);
        desiredCapabilities.setCapability("fullReset", false);
        desiredCapabilities.setCapability("appPackage", applicationParams.getAosPackage());
        desiredCapabilities.setCapability("appActivity", applicationParams.getAosActivity());
        desiredCapabilities.setCapability("app", applicationParams.getAosAppPath());
        desiredCapabilities.setCapability("newCommandTimeout", 60);

        desiredCapabilities = interceptDesiredCapabilities(platform, desiredCapabilities);
        try {
            appiumDriver = new AndroidDriver<>(getRemoteAddress(), desiredCapabilities);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected void startIosDriver() {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setPlatform(platform);
        desiredCapabilities.setCapability("automationName", "XCUITest");
        desiredCapabilities.setCapability("noReset", true);
        desiredCapabilities.setCapability("fullReset", false);
        desiredCapabilities.setCapability("autoAcceptAlerts", false);
        desiredCapabilities.setCapability("app", applicationParams.getIosAppPath());
        desiredCapabilities.setCapability("newCommandTimeout", 60);

        desiredCapabilities = interceptDesiredCapabilities(platform, desiredCapabilities);

        try {
            appiumDriver = new IOSDriver<>(getRemoteAddress(), desiredCapabilities);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void checkDriveStart() {
        if (appiumDriver == null) {
            throw new RuntimeException("AppiumDriver not start");
        }
    }

    DesiredCapabilities interceptDesiredCapabilities(Platform platform, DesiredCapabilities desiredCapabilities) {
        return desiredCapabilities;
    }

    protected abstract URL getRemoteAddress() throws MalformedURLException;

}
