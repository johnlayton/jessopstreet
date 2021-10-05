# jessopstreet

Build the distribution and publish to maven local

```bash
./gradlew clean publishGradlePublicationToMavenLocal
```

Update the distribution details in target project

```properties
distributionBase=GRADLE_USER_HOMEs
distributionPath=wrapper/dists
#distributionUrl=https\://services.gradle.org/distributions/gradle-7.1-bin.zip
distributionUrl=...
zipStoreBase=GRADLE_USER_HOME
zipStorePath=wrapper/dists
```
