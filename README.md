# Secure Message Emulator

A Java-based messaging application being developed incrementally as a hands-on software engineering and secure communications project.

The project began with core Java domain modeling and test-driven development, then expanded into layered application architecture, a Swing desktop interface, TCP client/server networking, and concurrent client handling.

The next phase will introduce TLS and PKI concepts to secure the currently unencrypted TCP communication.

## Current Implementation

### Core Application
- Java 21
- Maven build and dependency management
- JUnit 5 test-driven development
- Message domain model with validation and message state
- Repository abstraction and in-memory persistence
- Service layer
- Dependency injection and dependency inversion
- Mockito-based service testing

### Desktop UI
- Java Swing message composition interface
- Application service supplied to the UI through constructor injection

### Networking
- TCP client/server communication using `Socket` and `ServerSocket`
- Custom `MessageProtocol` for message serialization and deserialization
- Multiple messages transmitted over a persistent connection
- Persistent server accept loop
- Concurrent client handling
- `ExecutorService` with a fixed thread pool
- Try-with-resources for socket lifecycle management

## Architecture

The project is intentionally developed in layers:

```text
Swing UI
    |
    v
Message / Service Layer
    |
    v
MessageProtocol
    |
    v
TCP Client / Server
```

The current networking implementation uses plain TCP so that the underlying client/server and concurrency concepts can be developed and understood before adding transport security.

## Testing

The project uses JUnit 5 and follows a test-driven development approach where practical.

Tests currently cover areas including:

- Message validation
- Message state transitions
- Sent timestamps
- Repository behavior
- Service behavior and repository interaction
- Recipient and status lookup
- Message protocol serialization
- Message protocol deserialization
- Protocol round-trip behavior

Run the test suite with:

```powershell
mvn test
```

## Development Approach

This project is being built incrementally rather than as a completed tutorial application.

Major functionality is added in small steps, tested, and committed to Git so that the repository history reflects the evolution of the design.

Concepts explored so far include:

- Object-oriented Java
- Encapsulation
- Interfaces and polymorphism
- Repository and service patterns
- Dependency injection
- Dependency inversion
- Unit testing and test doubles
- TCP sockets
- Blocking I/O
- Streams and readers/writers
- Serialization and application protocols
- Persistent network connections
- Threads and concurrency
- Lambda expressions
- Executor services and thread pools

## Security Roadmap

The current TCP transport is **not encrypted**.

The next development milestone will introduce TLS and PKI concepts, including:

- Public/private key pairs
- X.509 certificates
- Certificate authorities and trust
- Java keystores and truststores
- TLS handshakes
- Server authentication
- Mutual TLS (mTLS)
- Java Secure Socket Extension (JSSE)

Future development is also planned to explore:

- Spring Boot REST services
- Persistent message storage
- Cucumber/Gherkin acceptance testing
- Continuous integration and automated code-quality checks

## Project Purpose

The Secure Message Emulator is a learning and portfolio project intended to build hands-on experience with Java software engineering, testing, networking, concurrency, and secure communications.

It is designed to demonstrate implemented project experience while continuing to develop deeper knowledge in these areas.
