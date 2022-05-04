import org.gradle.api.tasks.testing.logging.TestLogEvent.*

plugins {
    kotlin("jvm") version "1.6.10"
    application
}

group = "ai.sahaj.team2"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.junit.jupiter:junit-jupiter:5.8.2")
    implementation("org.json:json:20220320")
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    implementation("com.google.code.gson:gson:2.9.0")
    implementation("org.postgresql:postgresql:42.3.4")

    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.8.2")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
}

tasks.withType<Test> {
    useJUnitPlatform()
    testLogging {
        events(PASSED, FAILED, SKIPPED)
    }
}

application {
    mainClass.set("MainKt")
}