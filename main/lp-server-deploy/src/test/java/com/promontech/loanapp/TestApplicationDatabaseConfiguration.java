package com.promontech.loanapp;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@AutoConfigurationPackage
@EnableJpaRepositories(basePackages = { "com.promontech.loanapp", "com.promontech.factory", "com.promontech.decisioning" })
@EntityScan(basePackages = {
        "com.datapublica.pg.types",
        "com.promontech.loanapp",
        "com.promontech.factory",
        "org.axonframework.eventhandling.saga.repository.jpa",
        "org.axonframework.eventsourcing.eventstore.jpa",
})
@SpringBootConfiguration
public class TestApplicationDatabaseConfiguration {

}
