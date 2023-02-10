package com.smart.bddtest.core.hooks.eventListener;

import com.smart.bddtest.core.DeviceDriverProvider;
import com.smart.bddtest.core.hooks.ScenarioHook;
import io.cucumber.java.Scenario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AppScenarioLifeCycle implements ScenarioHook.EventListener {

    @Autowired
    private DeviceDriverProvider deviceDriverProvider;

    @Override
    public void onBeforeScenario(Scenario scenario) {
        deviceDriverProvider.getDeviceDriver().startIfNeed();
    }

    @Override
    public void onAfterScenario(Scenario scenario) {

    }

    @Override
    public void onBeforeAllScenario() {
    }

    @Override
    public void onAfterAllScenario() {
        deviceDriverProvider.getDeviceDriver().quitAppiumDriver();
    }
}
