package com.smart.bddtest.debugger;

import com.smart.bddtest.core.DeviceDriverProvider;
import io.cucumber.java.en.Given;
import org.springframework.beans.factory.annotation.Autowired;

public class DebuggerSetup {

    @Autowired
    private DeviceDriverProvider deviceDriverProvider;

    @Given("Client debug start")
    public void debugStart() {
        String deviceTime = deviceDriverProvider.getAppiumDriver().getDeviceTime();
        System.out.println("debugStart: " + deviceTime);
    }

    @Given("Client debug {} start")
    public void debugStart(String a) {
        System.out.println("debugStart");
    }

}
