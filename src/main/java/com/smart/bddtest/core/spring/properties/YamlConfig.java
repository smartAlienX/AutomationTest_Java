package com.smart.bddtest.core.spring.properties;

import com.smart.bddtest.core.spring.properties.models.ApplicationParams;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
@EnableConfigurationProperties({
        ApplicationParams.class
})
public class YamlConfig {
}
