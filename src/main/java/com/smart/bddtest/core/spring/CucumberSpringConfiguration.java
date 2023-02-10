package com.smart.bddtest.core.spring;

import com.smart.bddtest.core.spring.properties.YamlConfig;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.test.context.ContextConfiguration;

@ComponentScan(
        basePackages = {
                "com.smart.bddtest"
        }
)
@EnableAspectJAutoProxy
@CucumberContextConfiguration
@ContextConfiguration(
        classes = {CucumberSpringConfiguration.class, YamlConfig.class},
        loader = SpringBootContextLoader.class
)
public class CucumberSpringConfiguration {
}
