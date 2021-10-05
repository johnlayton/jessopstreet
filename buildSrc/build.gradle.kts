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
        register("jessopstreet") {
            id = "jessopstreet"
            implementationClass = "com.github.johnlayton.JessopStreetPlugin"
        }
    }
}
