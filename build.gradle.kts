plugins {
    val kotlinVersion = "1.4.30"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.serialization") version kotlinVersion
    id("org.springframework.boot") version "2.4.2"
    id("io.spring.dependency-management") version "1.0.10.RELEASE"
    id("org.flywaydb.flyway") version "7.5.0"
}

group = "com.severett"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    val coroutinesVersion: String by project

    implementation(kotlin("stdlib-jdk8"))
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.12.1")
    implementation("com.github.jasync-sql:jasync-postgresql:1.1.6")
    implementation("io.github.microutils:kotlin-logging:2.0.4")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.0.1")
    implementation("org.postgresql:postgresql:42.2.18")
    implementation("org.springframework.boot:spring-boot-starter-webflux")

    runtimeOnly("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:$coroutinesVersion")

    testImplementation("io.mockk:mockk:1.10.5")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

tasks {
    withType(org.jetbrains.kotlin.gradle.tasks.KotlinCompile::class.java).configureEach {
        kotlinOptions {
            jvmTarget = "15"
        }
    }
    test {
        useJUnitPlatform()
    }
}
