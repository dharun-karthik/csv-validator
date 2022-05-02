import org.gradle.api.tasks.testing.logging.TestLogEvent.*

plugins {
    kotlin("jvm") version "1.6.10"
}

group = "ai.sahaj.team2"
version = "1.0-SNAPSHOT"

apply(plugin = "application")

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.junit.jupiter:junit-jupiter:5.8.2")
    implementation("org.json:json:20220320")
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    implementation("com.google.code.gson:gson:2.9.0")

    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.8.2")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
}

tasks.withType<Test>{
    useJUnitPlatform()
    testLogging {
        events(PASSED, FAILED, SKIPPED)
    }
}