import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.30"
    application
}

group = "com.github.bluemasters"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

kotlin {
    sourceSets {
        val main by getting {
            dependencies {
                implementation("com.github.ajalt:clikt:2.8.0")
                implementation("org.slf4j:slf4j-api:1.7.30")
                implementation("ch.qos.logback:logback-classic:1.2.3")
                implementation("ch.qos.logback:logback-core:1.2.3")
            }
        }
        val test by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation("org.assertj:assertj-core:3.19.0")
                implementation("org.junit.jupiter:junit-jupiter-api:5.6.2")
            }
        }
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}

application {
    mainClass.set("PizzaKt")
    applicationName = "pizza"
}
