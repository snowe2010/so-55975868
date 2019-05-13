apply(plugin = "project-report")

dependencies {
    implementation(platform("org.springframework.boot:spring-boot-dependencies:${extra["springBootVersion"]}"))
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
}
