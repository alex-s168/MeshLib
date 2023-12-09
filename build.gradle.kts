plugins {
    kotlin("jvm") version "1.9.0"
    application
    id("org.jetbrains.dokka") version "1.9.0"
}

group = "me.alex_s168"
version = "0.1h1"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}

application {
    mainClass.set("TestKt")
}