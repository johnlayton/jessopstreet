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
    implementation("software.amazon.awssdk:codeartifact:2.17.56")
    implementation("software.amazon.awssdk:sso:2.17.56")
}

java {
    targetCompatibility = JavaVersion.VERSION_11
    sourceCompatibility = JavaVersion.VERSION_11
}

gradlePlugin {
    plugins {
        register("aws-codeartifact") {
            id = "aws-codeartifact"
            implementationClass = "com.github.johnlayton.CodeArtifactPlugin"
        }
    }
}
