package com.smart.bddtest;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        tags = "@debug_start",
        stepNotifications = true,
        features = {
                "src/main/resources/features"
        }
)
public class BddTestRunner {
}
