package com.promontech.loanapp.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

/**
 * This class is used purely for data source bean dependency management.
 * When Flyway is turned off, flyway beans will not exist. However when Flyway is turned on,
 * Hibernate validation need to run after flyway runs. So this bean is used to manage this
 * dependency order.
 */
@Configuration
public class FlywayDependencyConfig {

    public class FlywayDependency {
    }

    @Bean("flywayDependency")
    @ConditionalOnExpression("${promontech.flyway.enabled} == true")
    @DependsOn({ "eventstoreFlyway", "aggregateFlyway" })
    public FlywayDependency flywayDependencyBeanEnabled() {
        return new FlywayDependency();
    }

    @Bean("flywayDependency")
    @ConditionalOnExpression("${promontech.flyway.enabled} == false")
    public FlywayDependency flywayDependencyBeanDisabled() {
        return new FlywayDependency();
    }
}
