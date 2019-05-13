plugins {
    id("java")
    id("maven")
    kotlin("jvm") version "1.2.51"
    kotlin("plugin.jpa") version ("1.2.51")
    kotlin("plugin.spring") version ("1.2.51")
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    compile("com.fasterxml.jackson.module:jackson-module-kotlin:2.9.7")
    compile("org.springframework.boot:spring-boot-starter-data-jpa:1.5.6.RELEASE")
    compile("org.jetbrains.kotlin:kotlin-stdlib:1.2.51")
    compile("ch.qos.logback:logback-classic:1.1.11")
    testCompile("junit:junit:4.12")
    testCompile("org.mockito:mockito-core:2.11.0")
    testCompile("org.jetbrains.kotlin:kotlin-test-junit:1.2.51")
}

group = "com.promontech.loanplatform"
version = "0.6.0"
description = "data-mapping"
