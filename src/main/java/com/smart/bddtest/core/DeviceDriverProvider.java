package com.smart.bddtest.core;

import io.appium.java_client.AppiumDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;

@Component
public class DeviceDriverProvider {

    @Autowired
    public Map<String, DeviceDriver> deviceDriverMap;

    public DeviceDriver getDeviceDriver() {
        Collection<DeviceDriver> values = deviceDriverMap.values();
        for (DeviceDriver value : values) {
            return value;
        }
        return null;
    }

    public AppiumDriver getAppiumDriver() {
        return getDeviceDriver().getAppiumDriver();
    }
}
