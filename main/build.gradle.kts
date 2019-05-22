plugins {
    base
    java
    idea
    kotlin("jvm") version "1.2.71"
    kotlin("plugin.jpa") version ("1.2.71")
    kotlin("plugin.spring") version ("1.2.71")
    id("com.vanniktech.dependency.graph.generator") version ("0.5.0")
    id("org.flywaydb.flyway") version ("5.2.1")
}

repositories {
    mavenLocal()
    jcenter()
}
extra["rabbit-amqp-client.version"] = "4.0.2"
apply(plugin = "project-report")

val springBootVersion: String by project
val springDataVersion: String by project
val springCloudVersion: String by project

description = "Loan Platform Server :: Root"

allprojects {
    group = "com.promontech.loanplatform"
    repositories {
        jcenter {
            content {
                excludeGroup("com.datapublica.pg")
            }
        }
        mavenLocal()
    }
    ext {
        set("springBootVersion", springBootVersion)
        set("springDataVersion", springDataVersion)
        set("springCloudVersion", springCloudVersion)
        set("springCloudVaultVersion", "2.1.1.RELEASE")
    }
    apply {
        plugin("org.jetbrains.kotlin.jvm")
        plugin("org.jetbrains.kotlin.plugin.jpa")
        plugin("org.jetbrains.kotlin.plugin.spring")
        plugin("idea")
    }
    dependencies {
        implementation("com.datapublica.pg:hibernate-json:0.5")

        implementation(platform("org.springframework.boot:spring-boot-dependencies:${extra["springBootVersion"]}"))
        implementation(platform("org.springframework.cloud:spring-cloud-dependencies:${extra["springCloudVersion"]}"))
        implementation(platform("org.springframework.cloud:spring-cloud-vault-dependencies:${extra["springCloudVaultVersion"]}"))
        implementation(platform("org.springframework.data:spring-data-releasetrain:${extra["springDataVersion"]}"))
        
        // TODO only for spring boot 2 migration
        runtime("org.springframework.boot:spring-boot-properties-migrator:${extra["springBootVersion"]}")

        implementation(kotlin("stdlib-jdk8"))
        implementation(kotlin("reflect"))
        implementation("org.springframework.amqp:spring-amqp")
        implementation("org.springframework.cloud:spring-cloud-starter-vault-config")
        implementation("org.springframework.cloud:spring-cloud-vault-config-consul")
        implementation("org.springframework.cloud:spring-cloud-vault-config-rabbitmq")
        testImplementation(kotlin("test"))
        testImplementation(kotlin("test-junit"))
        testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:2.1.0")

        implementation(platform("org.springframework.boot:spring-boot-dependencies:${springBootVersion}"))
        implementation("org.apache.pdfbox:pdfbox:2.0.4")
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            suppressWarnings = true
            jvmTarget = "1.8"
            freeCompilerArgs = listOf("-java-parameters")
        }
    }

    val generatedSourcesPath = file("build/generated")

    java {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    tasks.withType<JavaCompile> {
        options.compilerArgs.add("-parameters")
        options.annotationProcessorGeneratedSourcesDirectory = generatedSourcesPath
    }
}

subprojects {
    dependencies {
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.9.7")
    }
}

dependencies {
    implementation(kotlin(module = "stdlib-jdk8"))
}

// configurations.all {
    // resolutionStrategy.failOnVersionConflict()
// }
