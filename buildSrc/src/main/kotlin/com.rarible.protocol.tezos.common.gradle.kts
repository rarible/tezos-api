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
            url = uri(System.getenv("GRADLE_NEXUS_URL"))
            credentials {
                username = System.getenv("GRADLE_NEXUS_USER")
                password = System.getenv("GRADLE_NEXUS_PASS")
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
