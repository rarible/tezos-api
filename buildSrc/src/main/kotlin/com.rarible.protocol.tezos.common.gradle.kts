plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
    `java`
    `maven-publish`
}

val springBootVersion: String by project
val coroutinesVersion: String by project

java {
    withSourcesJar()
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation(kotlin("reflect"))
    implementation(kotlin("stdlib-jdk8"))

    implementation(platform("org.jetbrains.kotlinx:kotlinx-coroutines-bom:$coroutinesVersion"))
    implementation(platform("org.springframework.boot:spring-boot-dependencies:$springBootVersion"))

    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
}

tasks.compileKotlin {
    kotlinOptions.jvmTarget = "${JavaVersion.VERSION_1_8}"
}

tasks.compileTestKotlin {
    kotlinOptions.jvmTarget = "${JavaVersion.VERSION_1_8}"
}

tasks.test {
    useJUnitPlatform()
}

tasks.register<TestReport>("coverage")

publishing {
    repositories {
        maven {
            url = uri("https://repo.rarible.org/repository/maven-public")
            credentials {
                username = if (project.hasProperty("nexus_user")) project.property("nexus_user").toString() else ""
                password = if (project.hasProperty("nexus_pwd")) project.property("nexus_pwd").toString() else ""
            }
        }
    }
    publications {
        create<MavenPublication>("maven") {
            artifactId = tasks.jar.get().archiveBaseName.get()
            from(components["java"])
        }
    }
}
