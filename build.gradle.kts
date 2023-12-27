plugins {
    kotlin("jvm") version "1.9.0"
    application
    id("org.jetbrains.dokka") version "1.9.0"
}

group = "me.alex_s168"
version = "0.6"

repositories {
    mavenCentral()
    maven {
        name = "alex's repo"
        url = uri("http://207.180.202.42:8080/libs")
        isAllowInsecureProtocol = true
    }
}

dependencies {
    testImplementation(kotlin("test"))

    implementation("me.alex_s168:mathlib:0.4")
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