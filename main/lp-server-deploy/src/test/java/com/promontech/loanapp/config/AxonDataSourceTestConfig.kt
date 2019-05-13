package com.promontech.loanapp.config


// TODO EELS-9251 - Left this in as WIP regarding testing whether the SecureIncomeDataServiceTest would run with this | uncertain whether this resolves it! [Steven]
//@Configuration
////@ConfigurationProperties(prefix = "spring.axon-datasource")
//@ConfigurationProperties(prefix = "spring.axon-data-source")
//@EnableJpaRepositories(
//    basePackages = ["org.axonframework"],
//    entityManagerFactoryRef = "axonEntityManagerFactory",
//    transactionManagerRef = "axonJpaTransactionManager"
//)
//@EnableSpringDataWebSupport
//@EnableTransactionManagement
//class AxonDataSourceTestConfig(
//    var driverClassName: String? = null,
//    var `jdbc-url`: String? = null,
//    var `maximum-pool-size`: Int = 1,
//    var `minimum-idle`: Int = 1,
//    var `validation-timeout`: Long = 1,
//    var username: String? = null,
//    var password: String? = null
//) {
//
//    @Bean(name = ["axonDataSource"])
//    @Primary
//    @Profile(Profiles.Names.INTEGRATION_TEST)
//    fun axonDataSource(): DataSource = createRestrictedDataSource()
//
//    private fun createRestrictedDataSource(): HikariDataSource {
//        val hikariDataSource = createHikariDataSource()
//        hikariDataSource.maximumPoolSize = `maximum-pool-size`
//        hikariDataSource.username = username
//        hikariDataSource.password = password
//        hikariDataSource.poolName = "restricted-connection-pool"
//        return hikariDataSource
//    }
//
//    private fun createHikariDataSource(): HikariDataSource {
//        val hikariDataSource = HikariDataSource()
//        hikariDataSource.driverClassName = driverClassName
//        hikariDataSource.jdbcUrl = `jdbc-url`
//        hikariDataSource.validationTimeout = `validation-timeout`
//        hikariDataSource.minimumIdle = `minimum-idle`
//        return hikariDataSource
//    }
//
//}
