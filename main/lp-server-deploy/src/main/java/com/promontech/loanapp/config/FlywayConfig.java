package com.promontech.loanapp.config;


import javax.annotation.PostConstruct;

import org.flywaydb.core.Flyway;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
@ConditionalOnProperty(name = "promontech.flyway.enabled", havingValue = "true")
public class FlywayConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.flyway-eventstore-datasource")
    public HikariDataSource flywayEventStoreDataSource() {
        return new HikariDataSource();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.flyway-aggregate-datasource")
    public HikariDataSource flywayIamDataSource() {
        return new HikariDataSource();
    }

    @Bean
    public Flyway eventstoreFlyway() {
        // Config and migrate LP-SERVER Event Store DB
        Flyway flyway = Flyway.configure()
                              .validateOnMigrate(false)
                              .baselineOnMigrate(true)
                              .locations("db/migration/eventstore")
                              .dataSource(flywayEventStoreDataSource())
                              .load();
        flyway.migrate();

        return flyway;
    }

    @Bean
    public Flyway aggregateFlyway() {
        // Config and migrate AGGREGATE DB
        Flyway flyway = Flyway.configure()
                              .validateOnMigrate(false)
                              .baselineOnMigrate(true)
                              .locations("db/migration/aggregate")
                              .dataSource(flywayIamDataSource())
                              .load();
        flyway.migrate();

        return flyway;
    }

    @PostConstruct
    public void migrateFlyway() {
        eventstoreFlyway().migrate();
        aggregateFlyway().migrate();
    }
}
