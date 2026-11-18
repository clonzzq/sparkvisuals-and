plugins {
    kotlin("jvm") version "2.0.21"
}

group = "com.sparkvisuals"
version = "0.1.0"

kotlin { jvmToolchain(17) }

tasks.test {
    useJUnitPlatform()
}

dependencies {
    testImplementation(kotlin("test"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.11.3")
}
