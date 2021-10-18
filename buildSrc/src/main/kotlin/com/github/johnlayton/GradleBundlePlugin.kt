package com.github.johnlayton

import org.gradle.api.DefaultTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import org.gradle.kotlin.dsl.create
import java.io.File
import java.io.FileOutputStream

open class JessopStreetExtension(
    var gradleVersion: String = "7.2",
    var awsVersion: String = "2.17.31"
)

abstract class DownloadGradle : DefaultTask() {

    @get:Input
    abstract val gradleVersion: Property<String>

    @get:OutputFile
    abstract val destinationFile: Property<File>

    init {
        gradleVersion.set("7.2")
        destinationFile.set(File("${project.buildDir}/temp/gradle-${gradleVersion.get()}-bin.zip"))
    }

    @TaskAction
    fun action() {
        project.mkdir(destinationFile.get().parentFile)
        project.uri("https://services.gradle.org/distributions/gradle-${gradleVersion.get()}-bin.zip")
            .toURL()
            .openStream().use { it.copyTo(FileOutputStream(destinationFile.get())) }
    }
}

class GradleBundlePlugin : Plugin<Project> {
    override fun apply(project: Project): Unit = project.run {
        project.extensions.create<JessopStreetExtension>("jessopstreet")
    }
}

