#!/usr/bin/env zsh

tree -L 3 ~/.m2/repository/com/github/johnlayton
tree -L 2 ~/.gradle/wrapper/dists

./gradlew pTML

rm -rf ~/.gradle/wrapper/dists/bundle-0.0.2-SNAPSHOT

tree -L 3 ~/.m2/repository/com/github/johnlayton
tree -L 2 ~/.gradle/wrapper/dists

./gradlew listBuckets
./gradlew listRepositories
