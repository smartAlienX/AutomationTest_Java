package com.smart.bddtest.setup;

import io.cucumber.java.en.Given;
import lombok.val;
import org.openqa.selenium.WebElement;

public class LoginSetup extends BaseSetup {

    @Given("Client click dialog ok")
    public void debugStart() {
        try {
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        WebElement webElement = getAppiumDriver().findElementById("tv_market");
        webElement.click();

        try {
            Thread.sleep(10000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
