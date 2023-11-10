# TODO APP

Just Another TODO app which is built using Spring Boot, MySQL.
POC on different concepts.

## Tech stack
- Java 17
- Gradle 7.4


## Build Project
If you are using the IntelliJ IDE, IntelliJ itself will build the project.

Or else run the below command for building.
```
./gradlew build
```

## Run Project
This is built using the IntelliJ IDE. `TodoAppApplication`
configuration will be available under Edit Configurations. Use that and run.

Or refer `TodoAppApplication.run.xml` file for running.

## Generate JWT
All APIs needs a JWT for accessing. So register or login using the
`v0/auth/register` OR `v0/auth/login` APIs for JWT generation.

