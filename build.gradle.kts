import com.github.johnlayton.DownloadGradle

plugins {
    id("base")
    id("jessopstreet")
    id("maven-publish")

    id("fr.brouillard.oss.gradle.jgitver") version "0.10.0-rc03"
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

jgitver {
    useSnapshot = true
    useDistance = false
    nonQualifierBranches = "main"
}
