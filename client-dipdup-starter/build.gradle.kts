
val apolloVersion: String by project

dependencies {
    implementation(project(":client-dipdup"))
    implementation("com.apollographql.apollo3:apollo-runtime:$apolloVersion")
    implementation("org.springframework.boot:spring-boot-autoconfigure")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}
