plugins {
    kotlin("jvm")
    id("org.springframework.boot") version ("2.1.2.RELEASE")
    id("org.flywaydb.flyway")
    java
}

extra["rabbit-amqp-client.version"] = "5.4.3"
apply(plugin = "project-report")

dependencies {
    implementation(project(":lp-server-common"))
    implementation(project(":lp-server-loanapp-common"))
    implementation(project(":lp-server-read"))

    implementation("com.h2database:h2")
    implementation("com.zaxxer:HikariCP:2.7.9")
    implementation("io.dropwizard:dropwizard-metrics-graphite:1.3.5")
    implementation("org.axonframework:axon-distributed-commandbus-springcloud:3.4.1")
    implementation("org.axonframework:axon-metrics:3.4.1")
    implementation("org.axonframework:axon-spring-boot-starter:3.4.1")
    implementation("org.flywaydb:flyway-core")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:2.1.2.RELEASE")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-websocket")
    implementation("org.springframework.cloud:spring-cloud-commons")
    implementation("org.springframework.cloud:spring-cloud-starter-consul-discovery")
    implementation("org.springframework.cloud:spring-cloud-starter-consul-config")
    implementation("org.springframework.cloud:spring-cloud-context")
    implementation("org.springframework.data:spring-data-commons")
    implementation("org.springframework.data:spring-data-jpa")
    
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.junit.jupiter:junit-jupiter-engine")
    testImplementation("org.junit.platform:junit-platform-launcher")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.boot:spring-boot-starter-security")
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation(project(":lp-server-deploy"))
}

val sourcesJar by tasks.registering(Jar::class) {
    classifier = "sources"
    from(sourceSets["main"].allSource)
}
