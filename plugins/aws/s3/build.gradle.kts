plugins {
    kotlin("jvm") version "1.5.10"
    id("java-gradle-plugin")
    id("maven-publish")
}

repositories {
    mavenCentral()
}

dependencies {
    compileOnly(gradleApi())
    implementation(kotlin("reflect"))
    implementation(kotlin("stdlib"))
    implementation("software.amazon.awssdk:s3:2.17.56")
}

java {
    targetCompatibility = JavaVersion.VERSION_11
    sourceCompatibility = JavaVersion.VERSION_11
}

gradlePlugin {
    plugins {
        register("aws-s3") {
            id = "aws-s3"
            implementationClass = "com.github.johnlayton.S3Plugin"
        }
    }
}
