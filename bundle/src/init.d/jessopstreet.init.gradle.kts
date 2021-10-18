import com.github.johnlayton.S3Plugin
import com.github.johnlayton.CodeArtifactPlugin

initscript {
    dependencies {
        classpath(fileTree("${initscript.sourceFile?.parentFile}/lib") { include("*.jar") })
    }
}

logger.lifecycle("Jessop Street Gradle Distribution")

apply<WrapperPlugin>()

class WrapperPlugin : Plugin<Gradle> {
    override fun apply(gradle: Gradle) {
        gradle.rootProject {
            logger.lifecycle("Apply S3Plugin to ${name}")
            apply<S3Plugin>()
            logger.lifecycle("Apply CodeArtifactPlugin to ${name}")
            apply<CodeArtifactPlugin>()
        }
    }
}
