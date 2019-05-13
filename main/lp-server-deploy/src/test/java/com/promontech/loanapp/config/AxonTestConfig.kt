package com.promontech.loanapp.config

import com.promontech.loanapp.common.loan.Profiles
import org.axonframework.config.EventProcessingConfiguration
import org.axonframework.metrics.GlobalMetricRegistry
import org.axonframework.queryhandling.DefaultQueryGateway
import org.axonframework.queryhandling.QueryBus
import org.axonframework.queryhandling.SimpleQueryBus
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.Profile


@Configuration
class AxonTestConfig {

    @Bean
    fun queryBus() = SimpleQueryBus()

    @Bean
    @Primary
    @Profile(Profiles.Names.INTEGRATION_TEST)
    fun queryGateway(queryBus: QueryBus) = DefaultQueryGateway(queryBus)

    @Bean
    fun eventProcessingConfiguration() = EventProcessingConfiguration()
    
    @Bean
    fun globalMetricRegistry() = GlobalMetricRegistry()
}
