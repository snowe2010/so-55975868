apply(plugin = "project-report")
dependencies {
    implementation(project(":lp-server-common"))
    implementation(project(":lp-server-loanapp-common"))

    implementation("com.promontech.loanplatform:data-mapping:${extra["dataMappingVersion"]}")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
}
