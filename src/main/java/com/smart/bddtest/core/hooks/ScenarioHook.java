package com.smart.bddtest.core.hooks;

import com.smart.bddtest.business.AppDeviceDriver;
import com.smart.bddtest.core.hooks.eventListener.AppScenarioLifeCycle;
import io.cucumber.java.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.BeforeClass;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ScenarioHook {

    @Autowired
    private AppScenarioLifeCycle appScenarioLifeCycle;
    private final static List<EventListener> eventListeners = new ArrayList<>();
    private boolean initEventListener = false;

    public ScenarioHook() {
        log.info("Create ScenarioHook");
    }

    @Before
    public void beforeScenario(Scenario scenario) {
        log.info("beforeScenario: " + scenario.getName());

        if (!initEventListener) {
            eventListeners.add(appScenarioLifeCycle);

            for (EventListener eventListener : eventListeners) {
                eventListener.onBeforeAllScenario();
            }
        }
        initEventListener = true;

        for (EventListener eventListener : eventListeners) {
            eventListener.onBeforeScenario(scenario);
        }
    }

    @After
    public void afterScenario(Scenario scenario) {
        log.info("afterScenario: " + scenario.getName());
        for (EventListener eventListener : eventListeners) {
            eventListener.onAfterScenario(scenario);
        }
    }

    @BeforeAll
    public static void beforeAllScenario() {
        log.info("beforeAllScenario");
        for (EventListener eventListener : eventListeners) {
            eventListener.onBeforeAllScenario();
        }
    }

    @AfterAll
    public static void afterAllScenario() {
        log.info("afterAllScenario");
        for (EventListener eventListener : eventListeners) {
            eventListener.onAfterAllScenario();
        }
    }

    public interface EventListener {
        void onBeforeScenario(Scenario scenario);

        void onAfterScenario(Scenario scenario);

        void onBeforeAllScenario();

        void onAfterAllScenario();
    }

}
