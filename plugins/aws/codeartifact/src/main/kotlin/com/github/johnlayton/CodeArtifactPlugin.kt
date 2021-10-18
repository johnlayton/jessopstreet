package com.github.johnlayton

import org.gradle.api.DefaultTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.TaskAction
import software.amazon.awssdk.auth.credentials.AwsCredentialsProviderChain
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider
import software.amazon.awssdk.services.codeartifact.CodeartifactClient
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.codeartifact.model.ListRepositoriesRequest

abstract class ListRepositories : DefaultTask() {

    @TaskAction
    fun action() {
        val client = CodeartifactClient.builder()
            .credentialsProvider(
                AwsCredentialsProviderChain.of(
                    ProfileCredentialsProvider.create(),
                    DefaultCredentialsProvider.create()
                )
            )
            .region(Region.of("eu-west-2"))
            .build()
        val response = client.listRepositories(
            ListRepositoriesRequest.builder()
                .maxResults(10)
                .build()
        )
        response.repositories().forEach { repository ->
            project.logger.lifecycle("Repository :: ${repository.name()}")
        }
    }
}

class CodeArtifactPlugin : Plugin<Project> {
    override fun apply(project: Project) {
//        project.extensions.create("s3", S3Extension::class.java)
        project.tasks.create("listRepositories", ListRepositories::class.java)
    }
}

