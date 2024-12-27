plugins {
    id("java")
    kotlin("jvm")
    `java-library`
    `maven-publish`
}

group = "io.sol"
version = "0.1.0"

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
    implementation("com.github.pengrad:java-telegram-bot-api:7.9.1")

    // https://mvnrepository.com/artifact/org.quartz-scheduler/quartz
    implementation("org.quartz-scheduler:quartz:2.3.2")
    // https://mvnrepository.com/artifact/at.favre.lib/bcrypt
    implementation("at.favre.lib:bcrypt:0.9.0")
    implementation(kotlin("stdlib-jdk8"))
}

tasks.test {
    useJUnitPlatform()
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