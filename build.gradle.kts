import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "7.1.2"
    kotlin("jvm")
    `java-library`
    `maven-publish`
}

group = "io.sol"
version = "0.1.3"

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("org.projectlombok:lombok:1.18.34")
    annotationProcessor("org.projectlombok:lombok:1.18.34")

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    implementation("com.google.guava:guava:33.3.0-jre")
    implementation("com.google.code.gson:gson:2.8.9")

    implementation("org.sol4k:sol4k:0.5.1")

}


tasks.test {
    useJUnitPlatform()
}

tasks.jar {
    manifest.attributes["Class-Path"] = configurations
        .runtimeClasspath
        .get()
        .joinToString(separator = " ") { file ->
            "libs/${file.name}"
        }
}


java {
    withSourcesJar()
}

kotlin {
    jvmToolchain(21)
}

publishing {
    publications {
        create<MavenPublication>("sol-rpc") {
            from(components["java"])
        }
    }


    repositories {
        maven {
            name = "SolRPC"
            url = uri(layout.buildDirectory.dir("repo"))
        }
    }
}