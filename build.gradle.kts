import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  kotlin("jvm") version "1.3.50"
}

group = "guru.drako.interpreters"
version = "1.0-SNAPSHOT"

repositories {
  mavenCentral()
}

object Version {
  const val JUNIT = "5.5.2"
}

dependencies {
  implementation(kotlin("stdlib-jdk8"))
  implementation(kotlin("reflect"))
  testImplementation(kotlin("test-junit5"))

  testImplementation("org.junit.jupiter:junit-jupiter-api:${Version.JUNIT}")
  testImplementation("org.junit.jupiter:junit-jupiter-params:${Version.JUNIT}")
  testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${Version.JUNIT}")
}

tasks {
  withType<Test> {
    useJUnitPlatform()
  }

  withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
  }

  "wrapper"(Wrapper::class) {
    gradleVersion = "5.6.4"
  }
}
