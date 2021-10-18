import com.github.johnlayton.DownloadGradle
import org.gradle.api.file.DuplicatesStrategy.WARN

plugins {
    id("base")
    id("gradle-bundle")
    id("maven-publish")
}

repositories {
    mavenCentral()
}

val awssdk by configurations.creating
val local by configurations.creating
dependencies {
    awssdk("software.amazon.awssdk:codeartifact:2.17.56")
    awssdk("software.amazon.awssdk:sso:2.17.56")

    local(project(path = ":plugins:aws:s3"))
    local(project(path = ":plugins:aws:codeartifact"))
}

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
        duplicatesStrategy = WARN
    }
    into("gradle-7.2/init.d/lib") {
        from(local)
        duplicatesStrategy = WARN
    }
}

publishing {
    publications {
        create<MavenPublication>("gradle") {
            artifact(bundle)
        }
    }
}


