package com.smart.bddtest.core.spring.properties.models;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "application")
public class ApplicationParams {

    private String aosPackage;
    private String aosActivity;
    private String aosAppPath;
    private String iosPackage;
    private String iosAppPath;

}
