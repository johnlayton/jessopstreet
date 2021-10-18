plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(gradleApi())
}

gradlePlugin {
    plugins {
        register("gradle-bundle") {
            id = "gradle-bundle"
            implementationClass = "com.github.johnlayton.GradleBundlePlugin"
        }
    }
}
