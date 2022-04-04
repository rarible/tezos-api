
val raribleCommonVersion: String by project

dependencies {
    api("com.rarible.core:rarible-core-kafka:$raribleCommonVersion")
    implementation("org.springframework.boot:spring-boot-autoconfigure")
}
