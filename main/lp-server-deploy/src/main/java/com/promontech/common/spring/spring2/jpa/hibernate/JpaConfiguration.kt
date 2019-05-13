package com.promontech.common.spring.spring2.jpa.hibernate

import com.promontech.loanapp.config.User
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.auditing.DateTimeProvider
import org.springframework.data.domain.AuditorAware
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.security.core.context.SecurityContextHolder
import java.time.temporal.TemporalAccessor
import java.util.*


/**
 * Configuration for datasource.
 */
@Configuration
@EnableJpaAuditing(dateTimeProviderRef = "dateTimeProvider")
class JpaConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "spring.jpa")
    fun springJpa(): SpringJpa {
        return SpringJpa()
    }

    @Bean(name = ["jpaProperties"])
    fun additionalJpaProperties(springJpa: SpringJpa): Properties {
        val properties = Properties()

        properties.setProperty(PROPERTY_NAME_HIBERNATE_DIALECT, springJpa.databaseDialect)
        properties.setProperty(PROPERTY_NAME_HIBERNATE_FORMAT_SQL, springJpa.formatSql)
        properties.setProperty(PROPERTY_NAME_HIBERNATE_SHOW_SQL, springJpa.showSql)
        properties.setProperty(PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO, springJpa.ddlAuto)
        properties.setProperty(PROPERTY_NAME_HIBERNATE_JDBC_TIME_ZONE, "UTC")

        return properties
    }

    @Bean
    fun createAuditorProvider(): AuditorAware<UUID> {
        return SecurityAuditor()
    }

    @Bean
    fun createAuditingListener(): AuditingEntityListener {
        return AuditingEntityListener()
    }
    
    @Bean
    fun dateTimeProvider(dateTimeService: DateTimeService): DateTimeProvider {
        return AuditingDateTimeProvider(dateTimeService)
    }

    @Bean
    fun currentTimeDateTimeService(): DateTimeService {
        return CurrentTimeDateTimeService()
    }

    class AuditingDateTimeProvider(private val dateTimeService: DateTimeService) : DateTimeProvider {
        override fun getNow(): Optional<TemporalAccessor> {
            return Optional.of(dateTimeService.currentDateAndTime)
        }
    }

    class SecurityAuditor : AuditorAware<UUID> {
        override fun getCurrentAuditor(): Optional<UUID> {
            val auth = SecurityContextHolder.getContext().authentication

            if (auth == null || !auth.isAuthenticated) {
                throw SecurityException("Require authenticated principal to maintain auditing for JPA.")
            }

            return Optional.of(UUID.fromString((auth.details as User).userId))
        }
    }

    companion object {


        private val PROPERTY_NAME_HIBERNATE_DIALECT = "hibernate.dialect"
        private val PROPERTY_NAME_HIBERNATE_FORMAT_SQL = "hibernate.format_sql"
        private val PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO = "hibernate.hbm2ddl.auto"
        private val PROPERTY_NAME_HIBERNATE_SHOW_SQL = "hibernate.show_sql"
        private val PROPERTY_NAME_HIBERNATE_JDBC_TIME_ZONE = "hibernate.jdbc.time_zone"
    }
}
