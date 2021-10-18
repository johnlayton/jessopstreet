pluginManagement {
    repositories {
        maven("https://plugins.gradle.org/m2")
    }
}

rootProject.name = "jessopstreet"

include(
    "bundle",
    "plugins:aws:s3",
    "plugins:aws:codeartifact"
)
