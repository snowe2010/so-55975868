package com.promontech.loanapp.config

import com.promontech.loanapp.common.loan.Profiles
import com.zaxxer.hikari.HikariDataSource
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.Profile
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.data.web.config.EnableSpringDataWebSupport
import org.springframework.orm.jpa.JpaVendorAdapter
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.transaction.annotation.EnableTransactionManagement
import java.util.*
import javax.sql.DataSource


@Configuration
@EnableJpaRepositories(
    basePackages = ["com.promontech.loanapp", "com.promontech.factory", "com.promontech.decisioning"],
    entityManagerFactoryRef = "loanappEntityManagerFactory",
    transactionManagerRef = "loanappJpaTransactionManager"
)
@EnableSpringDataWebSupport
@EnableTransactionManagement
@ConfigurationProperties(prefix = "spring.loanapp-datasource")
class LoanappDataSourceTestConfig(
    var defaultUsername: String? = null,
    var defaultPassword: String? = null,
    var restrictedMaxPoolSize: Int = 1,
    var commonProperties: HikariDataSource = HikariDataSource()
) {

    @Bean(name = ["aggregateTestDataSource"])
    @Primary
    @Profile(Profiles.Names.INTEGRATION_TEST)
    fun aggregateDataSource(): DataSource {
        return createRestrictedDataSource()
    }

    private fun createRestrictedDataSource(): HikariDataSource {
        val hikariDataSource = createHikariDataSource()
        hikariDataSource.maximumPoolSize = restrictedMaxPoolSize
        hikariDataSource.username = defaultUsername
        hikariDataSource.password = defaultPassword
        hikariDataSource.poolName = "restricted-connection-pool"
        return hikariDataSource
    }

    private fun createHikariDataSource(): HikariDataSource {
        val hikariDataSource = HikariDataSource()
        hikariDataSource.driverClassName = commonProperties.driverClassName
        hikariDataSource.jdbcUrl = commonProperties.jdbcUrl
        hikariDataSource.validationTimeout = commonProperties.validationTimeout
        hikariDataSource.minimumIdle = commonProperties.minimumIdle
        return hikariDataSource
    }

    @Bean
    @Primary
    fun entityManagerFactory(
        @Qualifier("aggregateTestDataSource") dataSource: DataSource,
        jpaProperties: Properties,
        jpaVendorAdapter: JpaVendorAdapter
    ): LocalContainerEntityManagerFactoryBean {
        val em = LocalContainerEntityManagerFactoryBean()
        em.dataSource = dataSource
        em.setPackagesToScan(
            "com.promontech.loanapp.projection",
            "com.promontech.loanapp.common.fanniemae",
            "com.promontech.factory.decisioning.projection",
            "com.promontech.decisioning.projection",
            "com.promontech.loanapp.common",
            "com.promontech.loanapp",
            "com.promontech.factory.decisioning.common",
            "com.datapublica.pg.types"
        )

        em.jpaVendorAdapter = jpaVendorAdapter
        val loanAppProperties = Properties(jpaProperties)
        // Only set these properties for loan app, not for all beans that might reference "jpaProperties".
        em.setJpaProperties(loanAppProperties)
        return em
    }

}
