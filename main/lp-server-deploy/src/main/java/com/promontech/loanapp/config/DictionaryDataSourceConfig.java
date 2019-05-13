package com.promontech.loanapp.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy;
import org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = { "com.promontech.loanapp.document.mapping", "com.promontech.mapping" },
        entityManagerFactoryRef = "dictionaryEntityManagerFactory",
        transactionManagerRef = "dictionaryJpaTransactionManager"
)
public class DictionaryDataSourceConfig {

    private static final String PROPERTY_NAME_PHYSICAL_NAMING_STRATEGY = "hibernate.physical_naming_strategy";
    private static final String PROPERTY_NAME_IMPLICIT_NAMING_STRATEGY = "hibernate.implicit_naming_strategy";

    @Bean
    @ConfigurationProperties("spring.dictionary-datasource")
    public HikariDataSource dictionaryDataSource() {
        return new HikariDataSource();
    }

    @SuppressWarnings("Duplicates")
    @Bean
    public LocalContainerEntityManagerFactoryBean dictionaryEntityManagerFactory(@Qualifier("dictionaryDataSource") HikariDataSource dataSource,
                                                                                 @Qualifier("jpaProperties") Properties jpaProperties,
                                                                                 JpaVendorAdapter jpaVendorAdapter) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("com.promontech.loanapp.document.mapping", "com.promontech.mapping");
        em.setJpaVendorAdapter(jpaVendorAdapter);

        Properties dictionaryJpaProperties = new Properties();
        dictionaryJpaProperties.putAll(jpaProperties);
        dictionaryJpaProperties.setProperty(PROPERTY_NAME_PHYSICAL_NAMING_STRATEGY, SpringPhysicalNamingStrategy.class.getName());
        dictionaryJpaProperties.setProperty(PROPERTY_NAME_IMPLICIT_NAMING_STRATEGY, SpringImplicitNamingStrategy.class.getName());
        em.setJpaProperties(dictionaryJpaProperties);

        return em;
    }

    @Bean
    public JpaTransactionManager dictionaryJpaTransactionManager(@Qualifier("dictionaryEntityManagerFactory") LocalContainerEntityManagerFactoryBean dictionaryEntityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(dictionaryEntityManagerFactory.getObject());
        return transactionManager;
    }
}
