# skeleton-api

## Dev environment

### Build

```bash
./gradlew clean build
```

### Run

```bash
./gradlew bootRun
```

### Intellij hot reload
In order for Intellij to allow hot reloading of the application, the following steps need to be executed:

1. Go to _Preferences_ -> _Build, Execution & Deployment_ -> _Compiler_
2. Check _Make project automatically_ 
3. Click OK
4. Do _CMD + Shift + A_
5. Search for _Registry_
6. Check _compiler.automake.allow.when.app.running_
7. Click Close

## Code Coverage

Jacoco is being used to ensure minimum code coverage.
Reports can be found at:
./build/reports

## Linting

Install the SonarLint plugin on Intellij and connect to the SonarCloud instance.

OR

```
./gradlew sonarqube
```
And check results at: https://sonarcloud.io/dashboard?id=com.andmicroservice%3Askeleton-api

## Actuator endpoints

See https://docs.spring.io/spring-boot/docs/current/reference/html/production-ready-endpoints.html