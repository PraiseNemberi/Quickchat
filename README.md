# QuickChat — Registration and Login

PROG5121 Programming 1A · Portfolio of Evidence Part 1  
Praise Ray Nemberi · ST10542243 · The Independent Institute of Education

## About

A Java console application that registers a user and logs them in. All
validation rules live in the `Login` class so that they can be tested
independently of the user interface, which is why the application logic
and the console prompts are kept in separate classes.

## Features

- Username validation — must contain an underscore and be no more than five characters
- Password complexity — at least eight characters, a capital letter, a number and a special character
- Cell phone validation — South African international format (+27 followed by nine digits)
- User registration with a specific message for each failed rule
- Login with a personalised welcome message, or an error if the details do not match

## Project structure

| File | Purpose |
|------|---------|
| `QuickChat.java` | Console interface — prompts the user and prints results |
| `Login.java` | All validation and registration logic |
| `LoginTest.java` | 14 JUnit 5 tests covering every method |

## Running it

Open the project in Apache NetBeans and press **F6**, or from a terminal:


## Testing

14 unit tests, written with JUnit 5 and run by Maven Surefire:


Every push to `main` triggers a GitHub Actions workflow that builds the
project on Java 21 and runs the full test suite.

## Built with

- Java 21
- Apache NetBeans 31
- Maven
- JUnit 5
- GitHub Actions

## References

Apache Software Foundation, 2026. *Maven Surefire Plugin*. [Online]  
Available at: https://maven.apache.org/surefire/maven-surefire-plugin/  
[Accessed 18 September 2026].

Farrell, J., 2023. *Java Programming*. 10th ed. Boston: Cengage Learning.

GitHub, 2026. *Building and testing Java with Maven*. [Online]  
Available at: https://docs.github.com/en/actions/automating-builds-and-tests/building-and-testing-java-with-maven  
[Accessed 18 September 2026].

JUnit Team, 2026. *JUnit 5 User Guide*. [Online]  
Available at: https://junit.org/junit5/docs/current/user-guide/  
[Accessed 18 September 2026].

Oracle, 2023. *Class Pattern*. [Online]  
Available at: https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/regex/Pattern.html  
[Accessed 13 September 2026].

