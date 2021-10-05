import com.github.johnlayton.DownloadGradle

plugins {
    id("base")
    id("jessopstreet")
    id("maven-publish")
}

repositories {
    mavenCentral()
}

val awssdk by configurations.creating
dependencies {
    awssdk("software.amazon.awssdk:codeartifact:2.17.46")
    awssdk("software.amazon.awssdk:sso:2.17.46")
}

group = "com.github.johnlayton"
version = "0.0.0-SNAPSHOT"

val download = tasks.register<DownloadGradle>("download") {
    gradleVersion.set(jessopstreet.gradleVersion)
}

val bundle = tasks.register<Zip>("bundle") {
    dependsOn(download)
    from(zipTree(download.get().destinationFile))
    into("gradle-7.2/init.d") {
        from("src/init.d")
    }
    into("gradle-7.2/init.d/lib") {
        from(awssdk)
    }
}

publishing {
    publications {
        create<MavenPublication>("gradle") {
            artifact(bundle)
        }
    }
}
