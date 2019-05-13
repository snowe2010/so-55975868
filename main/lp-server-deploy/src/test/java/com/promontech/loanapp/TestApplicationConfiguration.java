package com.promontech.loanapp;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigurationExcludeFilter;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.context.TypeExcludeFilter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@AutoConfigurationPackage
@ComponentScan(
        basePackages = {
                "com.promontech.common.spring.spring2",
                "com.promontech.common.spring.spring2.config",
                "com.promontech.loanapp",
                "com.promontech.loanapp.config",
                "com.promontech.lp",
                "com.promontech.mapping"
        },
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.CUSTOM, classes = TypeExcludeFilter.class),
                @ComponentScan.Filter(type = FilterType.CUSTOM, classes = AutoConfigurationExcludeFilter.class),
        }
)
@SpringBootConfiguration
public class TestApplicationConfiguration {

}
