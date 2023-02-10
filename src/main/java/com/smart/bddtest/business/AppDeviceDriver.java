package com.smart.bddtest.business;

import com.smart.bddtest.core.DeviceDriver;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;

@Component
public class AppDeviceDriver extends DeviceDriver {

    @Override
    protected URL getRemoteAddress() throws MalformedURLException {
        return new URL("http://localhost:4723/wd/hub");
    }
}
