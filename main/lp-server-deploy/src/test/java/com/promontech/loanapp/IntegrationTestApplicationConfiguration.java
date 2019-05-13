package com.promontech.loanapp;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigurationExcludeFilter;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.TypeExcludeFilter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

// TODO EELS-9251 - Left this in as WIP regarding testing whether the SecureIncomeDataServiceTest would run with this | uncertain whether this resolves it! [Steven]
@AutoConfigurationPackage
@ComponentScan(
        basePackages = {
                "com.promontech.common.spring.spring2",
                "com.promontech.common.spring.spring2.config",
                "com.promontech.loanapp",
                "com.promontech.loanapp.config",
                "com.promontech.lp",
                "com.promontech.mapping"
        },
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.CUSTOM, classes = TypeExcludeFilter.class),
                @ComponentScan.Filter(type = FilterType.CUSTOM, classes = AutoConfigurationExcludeFilter.class),
        }
)
@EnableJpaRepositories(
        basePackages = {
                "com.promontech.loanapp.config",
                "com.promontech.loanapp",
                "com.promontech.mapping",
                "com.promontech.loanapp.document.mapping",
                "com.promontech.loanapp.common",
                "com.promontech.loanapp.common.applicant.viewmodel",
        }
// TODO uncomment these lines
//        ,
//        transactionManagerRef = "dictionaryJpaTransactionManager",
//        entityManagerFactoryRef = "dictionaryEntityManagerFactory"
)
@EntityScan(basePackages = {
        "com.datapublica.pg.types",
        "com.promontech.loanapp.config",
        "com.promontech.loanapp",
        "com.promontech.factory",
        "com.promontech.decisioning",
        "com.promontech.mapping",
        "com.promontech.loanapp.document.mapping",
        "com.promontech.loanapp.common",
        "com.promontech.loanapp.common.applicant.viewmodel"})
@EnableConfigurationProperties
@SpringBootConfiguration
public class IntegrationTestApplicationConfiguration {

}
