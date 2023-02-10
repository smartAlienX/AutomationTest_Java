package com.smart.bddtest;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.io.IOException;
import java.net.URL;

public class BaseTest {

    private static AppiumDriverLocalService service;

    @BeforeSuite
    public void globalSetup() throws IOException {
//        service = AppiumDriverLocalService.buildService(new AppiumServiceBuilder()
//                .usingPort(4723)
//                .withIPAddress("127.0.0.1")
//        );
//        service.start();
    }

    @AfterSuite
    public void globalTearDown() {
        if (service != null) {
            service.stop();
        }
    }

    public URL getServiceUrl() {
        return service.getUrl();
    }

}
