package com.promontech.loanapp.config

import com.zaxxer.hikari.HikariDataSource
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.core.annotation.Order

// TODO EELS-9251 - Left this in as WIP regarding testing whether the SecureIncomeDataServiceTest would run with this | uncertain whether this resolves it! [Steven]
@Order(Integer.MIN_VALUE) // Order to ensure this data source is configured before our own datasources are, so that it can be used when building the Axon entity manager factory.
@Configuration
class AxonDataSourceTestConfigTwo {

    @Bean(name = ["axonDataSource"])
    @ConfigurationProperties(prefix = "spring.axon-data-source")
    @Primary
    fun axonDataSource(): HikariDataSource {
        return HikariDataSource()
    }

}
