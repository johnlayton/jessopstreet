plugins {
    id("fr.brouillard.oss.gradle.jgitver") version "0.10.0-rc03"
}

subprojects {
    apply(plugin = "fr.brouillard.oss.gradle.jgitver")
    group = "com.github.johnlayton"
    jgitver {
        useSnapshot = true
        useDistance = false
        nonQualifierBranches = "main"
    }
}
