RootTrace – Runtime Error Monitoring System (Spring Boot MVP)
Overview

RootTrace is a backend runtime error monitoring system built using Spring Boot.
It is designed to capture runtime exceptions, identify their root causes, and provide structured debugging insights to developers.

In modern backend applications, runtime failures such as NullPointerException, ArithmeticException, database connection failures, and API errors are often buried inside large logs. RootTrace aims to simplify debugging by capturing errors centrally and preparing them for analysis.

This repository contains the Minimum Viable Product (MVP) implementation that demonstrates how runtime errors can be generated, captured, and structured for monitoring.

Problem Statement

Backend systems frequently encounter runtime errors that are difficult to debug because:

Logs are scattered across multiple files

Stack traces are long and complex

The same error occurs repeatedly

Developers must manually identify the root cause

RootTrace addresses these challenges by building a centralized error monitoring system.

Key Features (MVP)
Runtime Error Generation

The project includes demo endpoints that intentionally generate runtime errors to test the monitoring system.

Global Error Capture

All runtime exceptions are intercepted using Spring Boot's global exception handling.

Structured Error Logging

Captured errors include:

Exception type

Error message

Timestamp

Centralized logging

REST API Testing

Endpoints allow developers to simulate real backend failures.

Architecture Overview

The system currently follows a simple layered architecture.

Client Request
      |
      v
DemoController
      |
      v
Runtime Exception
      |
      v
GlobalExceptionHandler
      |
      v
Structured Error Logging

Future versions will extend this architecture with error storage, grouping, and analytics.

Error Flow (How RootTrace Works)

A request hits an endpoint.

The controller intentionally triggers an exception.

The exception propagates through the Spring Boot runtime.

GlobalExceptionHandler intercepts the error.

Error information is logged in a structured format.

Example flow:

Request → Controller → Exception → GlobalExceptionHandler → Log Output
API Endpoints
Application Health Check
GET /

Response

RootTrace is running!
Generate Null Pointer Error
GET /null-error

Simulates a NullPointerException.

Example:

String value = null;
return value.toString();
Generate Arithmetic Error
GET /divide-error

Simulates an ArithmeticException.

Example:

return 10 / 0;
Project Structure
roottrace
│
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com.bhargav.roottrace
│   │   │
│   │   │   ├── RoottraceApplication.java
│   │   │
│   │   │   ├── controller
│   │   │   │   └── DemoController.java
│   │   │
│   │   │   └── exception
│   │   │       └── GlobalExceptionHandler.java
│   │   │
│   │   └── resources
│   │       └── application.properties
│   │
│   └── test
│       └── java
│           └── com.bhargav.roottrace
│               └── RoottraceApplicationTests.java
│
├── pom.xml
└── README.md
Technology Stack
Technology	Purpose
Java	Core programming language
Spring Boot	Backend framework
Maven	Dependency management
REST API	Communication layer
How to Run the Project
Clone Repository
git clone https://github.com/your-username/roottrace.git
Navigate to Project
cd roottrace
Run Application
mvn spring-boot:run

Or run the main class:

RoottraceApplication.java
Testing the Application

Once the application starts, open a browser.

http://localhost:8081

Test endpoints:

http://localhost:8081/
http://localhost:8081/null-error
http://localhost:8081/divide-error
Example Error Output

When an error occurs, the system logs structured information:

===== ROOTTRACE ERROR CAPTURED =====
Exception Type: java.lang.NullPointerException
Message: null
Time: 2026-02-22T19:20:30
Future Roadmap

The current version is a basic MVP. Planned improvements include:

ErrorEvent Model

Structured error objects for storing runtime errors.

Error Grouping

Combine duplicate errors into a single issue.

Root Cause Analysis

Identify the exact class, method, and line number causing the failure.

Error Storage

Persist errors in a database.

Error Dashboard

Visual UI to track and analyze application failures.

Production Integration

SDK for integrating RootTrace into real Spring Boot applications.

Project Vision

The long-term goal of RootTrace is to become a production error monitoring platform for Java Spring Boot applications, helping developers reduce debugging time and improve system reliability.

Author

Bhargav Vyas

License

This project is currently an experimental MVP for runtime error monitoring research.
