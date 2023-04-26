package com.smart.bddtest.setup;

import com.smart.bddtest.core.DeviceDriverProvider;
import io.appium.java_client.AppiumDriver;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseSetup {

    @Autowired
    private DeviceDriverProvider deviceDriverProvider;

    public AppiumDriver getAppiumDriver() {
        return deviceDriverProvider.getAppiumDriver();
    }

}
