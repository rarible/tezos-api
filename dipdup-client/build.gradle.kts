
val apolloVersion: String by project

plugins {
    id("com.apollographql.apollo3").version("3.2.0")
}

dependencies {
    api("com.apollographql.apollo3:apollo-runtime:$apolloVersion")
    api(project(":dipdup-core"))
    testImplementation("com.apollographql.apollo3:apollo-mockserver:$apolloVersion")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

apollo {
    srcDir("src/main/graphql/query")
    schemaFile.set(file("src/main/graphql/schema.graphql"))
    packageName.set("com.rarible.dipdup.client")
}


