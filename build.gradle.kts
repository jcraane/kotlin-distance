plugins {
    kotlin("jvm") version "2.0.20"
}

group = "dev.jamiecraane.distance"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))

    testImplementation(libs.junit.api)
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}
