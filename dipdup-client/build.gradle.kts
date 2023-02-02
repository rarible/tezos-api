
val apolloVersion: String by project

plugins {
    id("com.apollographql.apollo3").version("3.2.0")
}

dependencies {
    api("com.apollographql.apollo3:apollo-runtime:$apolloVersion")
    api(project(":dipdup-core"))
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.12.1")
    implementation("org.springframework.boot:spring-boot-starter-webflux")

    testImplementation("com.apollographql.apollo3:apollo-mockserver:$apolloVersion")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test")

    testImplementation("com.fasterxml.jackson.core:jackson-databind:2.12.+")
    testImplementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.12.+")
}

apollo {
    srcDir("src/main/graphql/query")
    schemaFile.set(file("src/main/graphql/schema.graphql"))
    packageName.set("com.rarible.dipdup.client")

    generateAsInternal.set(false)
    generateKotlinModels.set(false)
}

java.sourceSets["main"].java {
    srcDir("build/generated/source/apollo")
}
