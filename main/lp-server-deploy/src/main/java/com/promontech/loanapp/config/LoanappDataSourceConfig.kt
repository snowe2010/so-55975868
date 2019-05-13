package com.promontech.loanapp.config

import com.promontech.common.spring.spring2.db.multitenancy.CurrentTenantIdResolver
import com.zaxxer.hikari.HikariDataSource
import org.hibernate.MultiTenancyStrategy
import org.hibernate.cfg.Environment
import org.hibernate.context.spi.CurrentTenantIdentifierResolver
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.DependsOn
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.data.web.config.EnableSpringDataWebSupport
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.JpaVendorAdapter
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.annotation.EnableTransactionManagement
import java.util.*
import javax.sql.DataSource

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = ["com.promontech.loanapp"],
        entityManagerFactoryRef = "loanappEntityManagerFactory",
        transactionManagerRef = "loanappJpaTransactionManager"
)
@EnableSpringDataWebSupport
@ConfigurationProperties(prefix = "spring.loanapp-datasource")
class LoanappDataSourceConfig(
        var defaultUsername: String? = null,
        var defaultPassword: String? = null,
        var restrictedMaxPoolSize: Int = 1,
        var commonProperties: HikariDataSource = HikariDataSource(),
        var clients: List<ClientConfig> = mutableListOf()
) {

    @Bean
    @DependsOn("flywayDependency")
    fun loanappEntityManagerFactory(
        @Qualifier("aggregateDataSource") dataSource: DataSource,
        jpaProperties: Properties,
        multiTenantConnectionProvider: MultiTenantConnectionProvider,
        currentTenantIdResolver: CurrentTenantIdResolver,
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
                "com.promontech.factory.decisioning.common",
                "com.datapublica.pg.types"
        )

        em.jpaVendorAdapter = jpaVendorAdapter
        val loanAppProperties = Properties(jpaProperties)
        // Only set these properties for loan app, not for all beans that might reference "jpaProperties".
        loanAppProperties[Environment.MULTI_TENANT] = MultiTenancyStrategy.DATABASE
        loanAppProperties[Environment.MULTI_TENANT_CONNECTION_PROVIDER] = multiTenantConnectionProvider
        loanAppProperties[Environment.MULTI_TENANT_IDENTIFIER_RESOLVER] = currentTenantIdResolver
        em.setJpaProperties(loanAppProperties)
        return em
    }

    @Bean
    fun jpaVendorAdapter(): HibernateJpaVendorAdapter = HibernateJpaVendorAdapter()


    @Bean
    fun getMultiTenantConnectionProvider(@Qualifier("aggregateDataSource") dataSource: DataSource): MultiTenantConnectionProvider {
        return MultiTenantConnectionProvider(dataSource)
    }


    @Bean
    fun aggregateDataSource(currentTenantIdResolver: CurrentTenantIdentifierResolver): DataSource {
        val perTenantDataSources = HashMap<Any, Any>()
        clients.forEach { client -> perTenantDataSources[client.tenantId] = createDataSourceForTenant(client) }

        val multiTenantDataSource = MultiTenantDataSource(currentTenantIdResolver)
        multiTenantDataSource.setTargetDataSources(perTenantDataSources)
        multiTenantDataSource.setDefaultTargetDataSource(createRestrictedDataSource())
        return multiTenantDataSource
    }

    @Bean
    fun loanappJpaTransactionManager(@Qualifier("loanappEntityManagerFactory") loanappEntityManagerFactory: LocalContainerEntityManagerFactoryBean): JpaTransactionManager {
        val transactionManager = JpaTransactionManager()
        transactionManager.entityManagerFactory = loanappEntityManagerFactory.getObject()
        return transactionManager
    }

    @Bean
    fun provideJdbcTemplate(@Qualifier("aggregateDataSource") dataSource: DataSource): JdbcTemplate {
        return JdbcTemplate(dataSource)
    }

    @Bean
    fun exceptionTranslation(): PersistenceExceptionTranslationPostProcessor {
        return PersistenceExceptionTranslationPostProcessor()
    }

    /**
     * This DataSource is used by the MultiTenant DataSource as the default connection when a tenant Id cannot be found
     * in the security context. The only known valid use case for this is when the application is bootstrapping and
     * hibernate tries to validate the schema. Therefore, we expect this connection to be used exactly once and want it
     * to be configured to have as minimal a pool as possible to prevent the waste of DB resources.
     */
    private fun createRestrictedDataSource(): HikariDataSource {
        val hikariDataSource = createHikariDataSource()
        hikariDataSource.maximumPoolSize = restrictedMaxPoolSize
        hikariDataSource.username = defaultUsername
        hikariDataSource.password = defaultPassword
        hikariDataSource.poolName = "restricted-connection-pool"
        return hikariDataSource
    }

    private fun createDataSourceForTenant(client: ClientConfig): HikariDataSource {
        val hikariDataSource = createHikariDataSource()
        hikariDataSource.maximumPoolSize = commonProperties.maximumPoolSize
        hikariDataSource.username = convertTenantIdToUserName(client.tenantId)
        hikariDataSource.password = client.password
        val tenantConnectionPoolName = client.name + "-connection-pool"
        hikariDataSource.poolName = tenantConnectionPoolName
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


    private fun convertTenantIdToUserName(id: String): String {
        return ("u" + id.replace("-", "")).toLowerCase(Locale.US)
    }
}
