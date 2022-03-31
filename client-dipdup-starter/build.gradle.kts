
val apolloVersion: String by project

dependencies {
    api(project(":client-dipdup"))
    implementation("org.springframework.boot:spring-boot-autoconfigure")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}
