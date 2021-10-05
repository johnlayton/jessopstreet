import software.amazon.awssdk.auth.credentials.AwsCredentialsProviderChain
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.codeartifact.CodeartifactClient
import software.amazon.awssdk.services.codeartifact.model.GetAuthorizationTokenRequest

initscript {
    dependencies {
        classpath(fileTree("${initscript?.sourceFile?.parentFile}/lib") { include("*.jar") })
    }
}

logger.lifecycle("Load the CodeArtifact init script")

fun getToken(domain: String, owner: String, region: String): String {
    return System.getenv("CODEARTIFACT_AUTH_TOKEN")?.also {
        logger.info("Use environment authorization token")
    } ?: run {
        logger.info("Use AWS SDK to get authorization token")
        val client = CodeartifactClient.builder()
            .credentialsProvider(
                AwsCredentialsProviderChain.of(
                    ProfileCredentialsProvider.create(),
                    DefaultCredentialsProvider.create()
                )
            )
            .region(Region.of(region))
            .build()

        val response = client.getAuthorizationToken(
            GetAuthorizationTokenRequest.builder()
                .domain(domain)
                .domainOwner(owner)
                .build()
        )

        return response.authorizationToken()
    }
}

fun applyCredentials(repository: MavenArtifactRepository) {
    val pattern = "([a-zA-Z0-9\\-]+)-([0-9]+).d.codeartifact.([a-zA-Z0-9\\-]*).amazonaws.com".toRegex()
    pattern.find(repository.url.host)?.let { result ->
        logger.info("Add credentials to repository ${repository.url}")
        val (domain, owner, region) = result.destructured
        val token = getToken(domain, owner, region)
        repository.credentials {
            username = "aws"
            password = token
        }
    }
}

allprojects {
    repositories {
        all {
            if (this is MavenArtifactRepository) {
                applyCredentials(this);
            }
        }
    }

    afterEvaluate {
        if (plugins.hasPlugin("maven-publish")) {
            val publishing: PublishingExtension = project.getExtensions().getByType(PublishingExtension::class.java)
            publishing.repositories {
                all {
                    if (this is MavenArtifactRepository) {
                        applyCredentials(this);
                    }
                }
            }
        }
    }
}

settingsEvaluated {
    pluginManagement {
        repositories {
            all {
                if (this is MavenArtifactRepository) {
                    applyCredentials(this);
                }
            }
        }
    }
}

