# QuickChat

PROG5121 Programming 1A · Portfolio of Evidence  
Praise Ray Nemberi · ST10542243 · The Independent Institute of Education

## About

A Java console chat application. Users register and log in, send, store or
disregard messages, and then search, delete and report on their messages
using arrays. All logic lives in testable classes, separate from the console
prompts.

## Features

**Part 1 — Registration and login**
- Username, password complexity and South African cell number (regex) validation
- Login with a personalised welcome message

**Part 2 — Sending messages**
- Numeric menu that runs until the user quits
- Ten-digit message IDs, message hashes and a 250-character limit
- Send, disregard or store each message; stored messages are saved to `messages.json`

**Part 3 — Stored messages and reports**
- Arrays for sent, disregarded and stored messages, message hashes and message IDs
- Stored messages are read back from `messages.json` into an array at start-up
- Display the sender and recipient of all stored messages
- Display the longest stored message
- Search by message ID or by recipient
- Delete a message using its message hash
- Full report of all sent and stored messages

## Project structure

| File | Purpose |
|------|---------|
| `QuickChat.java` | Console interface — menus, prompts and output |
| `Login.java` | Registration and login validation |
| `Message.java` | A single message: ID, hash, length checks and JSON storage |
| `MessageManager.java` | Message arrays, searches, delete and report |
| `LoginTest.java` | 14 JUnit 5 tests |
| `MessageTest.java` | 18 JUnit 5 tests |
| `MessageManagerTest.java` | 15 JUnit 5 tests using the Part 3 test data |

## Running it

Open the project in Apache NetBeans and press **F6**, or from a terminal:

```
mvn compile exec:java
```

## Testing

47 unit tests, written with JUnit 5 and run by Maven Surefire:

```
mvn test
```

Every push to `main` and `part-3` triggers a GitHub Actions workflow that
builds the project on Java 21 and runs the full test suite.

## Built with

- Java 21
- Apache NetBeans
- Maven
- JUnit 5
- Gson
- GitHub Actions

## References

Apache Software Foundation, 2026. *Maven Surefire Plugin*. [Online]  
Available at: https://maven.apache.org/surefire/maven-surefire-plugin/  
[Accessed 18 September 2026].

Farrell, J., 2023. *Java Programming*. 10th ed. Boston: Cengage Learning.

GitHub, 2026. *Building and testing Java with Maven*. [Online]  
Available at: https://docs.github.com/en/actions/automating-builds-and-tests/building-and-testing-java-with-maven  
[Accessed 18 September 2026].

Google, 2026. *Gson User Guide*. [Online]  
Available at: https://github.com/google/gson/blob/main/UserGuide.md  
[Accessed 4 October 2026].

JUnit Team, 2026. *JUnit 5 User Guide*. [Online]  
Available at: https://junit.org/junit5/docs/current/user-guide/  
[Accessed 18 September 2026].

Oracle, 2023. *Class Pattern*. [Online]  
Available at: https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/regex/Pattern.html  
[Accessed 13 September 2026].