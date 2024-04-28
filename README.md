# TODO APP

Just Another TODO app which is built using Spring Boot, MySQL.
POC on different concepts.

## Concepts Covered
- Restful APIs
- Spring data JPA
- Modules. - Have split the project into multiple modules.
- Filters. - Custom filters for different purposes. eg : `LogFilter` for logging necessary details of API calls, `JwtAuthenticationFilter` for authentication.
- Actuator - For monitoring
- Spring Security - Here have covered login using username password and generate JWT and use it in different APIs for authentication. This can be seen in `user` module.

## Purpose
Just like the name suggests, simple app for managing tasks.

### Future Plan
May be make it like jira/notion :)

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

