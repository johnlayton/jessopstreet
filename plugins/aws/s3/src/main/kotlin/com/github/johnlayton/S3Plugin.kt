package com.github.johnlayton

import org.gradle.api.DefaultTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.TaskAction
import software.amazon.awssdk.auth.credentials.AwsCredentialsProviderChain
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client

open class S3Extension(
)

abstract class ListBuckets : DefaultTask() {

    @TaskAction
    fun action() {
        val client = S3Client.builder()
            .credentialsProvider(
                AwsCredentialsProviderChain.of(
                    ProfileCredentialsProvider.create(),
                    DefaultCredentialsProvider.create()
                )
            )
            .region(Region.of("eu-west-2"))
            .build()
        val response = client.listBuckets()
        response.buckets().forEach { bucket ->
            project.logger.lifecycle("Bucket :: ${bucket.name()}")
        }
    }
}

class S3Plugin : Plugin<Project> {
    override fun apply(project: Project) {
//        project.extensions.create("s3", S3Extension::class.java)
        project.tasks.create("listBuckets", ListBuckets::class.java)
    }
}

