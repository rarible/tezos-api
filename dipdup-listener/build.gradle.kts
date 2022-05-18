
val raribleCommonVersion: String by project

dependencies {
    api("com.rarible.core:rarible-core-kafka:$raribleCommonVersion")
    api(project(":dipdup-core"))
    implementation("org.springframework.boot:spring-boot-autoconfigure")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}
