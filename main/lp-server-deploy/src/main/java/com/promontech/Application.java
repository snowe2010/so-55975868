package com.promontech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableConfigurationProperties
@EnableDiscoveryClient
@EntityScan(
        basePackages = {
                "com.datapublica.pg.types",
                "com.promontech.loanapp",
                "com.promontech.factory",
                "org.axonframework.eventhandling.saga.repository.jpa",
                "org.axonframework.eventsourcing.eventstore.jpa",
                "com.promontech.loanapp.document.mapping",
                "com.promontech.mapping"
        }
)
@EnableJpaRepositories(basePackages = { "com.promontech.loanapp.document.mapping", "com.promontech.mapping" },
        entityManagerFactoryRef = "dictionaryEntityManagerFactory", transactionManagerRef = "dictionaryJpaTransactionManager")
@SpringBootApplication(
        exclude = { SecurityAutoConfiguration.class },
        scanBasePackages = {
                "com.promontech.common.spring.spring2",
                "com.promontech.common.spring.spring2.config",
                "com.promontech.decisioning",
                "com.promontech.factory",
                "com.promontech.loanapp",
                "com.promontech.loanapp.config",
                "com.promontech.lp",
                "com.promontech.mapping",
                "com.datapublica.pg.types"
        }
)
public class Application extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(Application.class);
        springApplication.addListeners(new ApplicationPidFileWriter());
        springApplication.run(args);
    }
}
