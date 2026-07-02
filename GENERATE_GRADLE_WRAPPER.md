# Gradle wrapper note

The repository contains the Gradle wrapper configuration and scripts. Before sharing the repository, the interviewer should generate and commit the wrapper JAR once from a machine with Gradle installed:

```bash
gradle wrapper --gradle-version 8.13
```

Alternatively, create a temporary Android Studio project using the same Gradle version and copy its generated `gradle/wrapper/gradle-wrapper.jar` into this repository.

After the JAR is present, verify the project with:

```bash
./gradlew test
./gradlew assembleDebug
```
