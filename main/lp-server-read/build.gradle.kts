apply(plugin = "project-report")
dependencies {
    implementation(project(":lp-server-common"))
    implementation(project(":lp-server-loanapp-common"))

    implementation("com.promontech.loanplatform:data-mapping:0.6/0")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
}
