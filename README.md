# BFHL REST API — Bajaj Finserv Health Ltd Round 27 June 2026

[![Java](https://img.shields.io/badge/Java-17-orange?logo=openjdk)](https://openjdk.org/projects/jdk/17/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.5-6db33f?logo=spring)](https://spring.io/projects/spring-boot)
[![Build](https://img.shields.io/badge/Build-Maven-red?logo=apachemaven)](https://maven.apache.org/)
[![License](https://img.shields.io/badge/License-MIT-blue)](LICENSE)

> A production-ready Spring Boot REST API that processes mixed arrays of strings and categorizes them into numbers, alphabets, and special characters with advanced string manipulation.

---

## 📋 Table of Contents
- [Features](#features)
- [Project Structure](#project-structure)
- [API Documentation](#api-documentation)
- [Quick Start](#quick-start)
- [Running Tests](#running-tests)
- [Deployment](#deployment)
- [Examples](#examples)

---

## ✨ Features

- ✅ **POST /bfhl** endpoint with full request/response validation
- ✅ Categorizes input into: odd numbers, even numbers, alphabets, special characters
- ✅ Computes numerical sum (returned as string)
- ✅ Builds alternating-caps reversed concatenation string
- ✅ Request & Response DTOs with Jackson annotations
- ✅ Service Interface + Implementation pattern
- ✅ Global Exception Handler with structured error responses
- ✅ 12 unit tests + 7 integration tests (MockMvc)
- ✅ Docker + Railway deployment ready

---

## 🗂 Project Structure

```
src/
├── main/java/com/bfhl/
│   ├── BfhlApplication.java               # Spring Boot entry point
│   ├── controller/
│   │   └── BfhlController.java            # POST /bfhl endpoint
│   ├── dto/
│   │   ├── BfhlRequest.java               # Input DTO
│   │   ├── BfhlResponse.java              # Output DTO
│   │   └── ErrorResponse.java             # Error payload DTO
│   ├── exception/
│   │   └── GlobalExceptionHandler.java    # @RestControllerAdvice handler
│   └── service/
│       ├── BfhlService.java               # Service interface
│       └── BfhlServiceImpl.java           # Business logic implementation
└── test/java/com/bfhl/
    ├── BfhlServiceImplTest.java            # Unit tests (12 cases)
    └── BfhlControllerIntegrationTest.java  # Integration tests (7 cases)
```

---

## 📖 API Documentation

### POST `/bfhl`

**Request Body**
```json
{
  "data": ["a", "1", "334", "4", "R", "$"]
}
```

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| data  | array of strings | ✅ Yes | Mixed array of alphanumeric and special characters |

**Success Response (HTTP 200)**
```json
{
  "is_success": true,
  "user_id": "john_doe_17091999",
  "email": "john@xyz.com",
  "roll_number": "ABCD123",
  "odd_numbers": ["1"],
  "even_numbers": ["334", "4"],
  "alphabets": ["A", "R"],
  "special_characters": ["$"],
  "sum": "339",
  "concat_string": "Ra"
}
```

**Error Response (HTTP 400)**
```json
{
  "is_success": false,
  "error": "VALIDATION_ERROR",
  "message": "data: The 'data' field must not be null"
}
```

---

## 🚀 Quick Start

### Prerequisites
- Java 17+
- Apache Maven 3.9+

### Run Locally
```bash
# 1. Clone / navigate to project
cd "E:\Project Java"

# 2. Build
mvn clean package

# 3. Run
java -jar target/bfhl-api-1.0.0.jar
```

The server starts at `http://localhost:8080`

### Test with cURL
```bash
curl -X POST http://localhost:8080/bfhl \
  -H "Content-Type: application/json" \
  -d '{"data": ["a","1","334","4","R","$"]}'
```

---

## 🧪 Running Tests

```bash
# Run all tests
mvn test

# Run with detailed output
mvn test -Dsurefire.useFile=false
```

**Test Coverage:**
- `BfhlServiceImplTest` — 12 unit tests (all 3 spec examples + 9 edge cases)
- `BfhlControllerIntegrationTest` — 7 integration tests (MockMvc end-to-end)

---

## 🐳 Deployment

### Deploy on Railway

1. Push code to GitHub
2. Go to [railway.app](https://railway.app) → **New Project** → **Deploy from GitHub repo**
3. Railway auto-detects the `Dockerfile` and deploys
4. Your API will be live at: `https://your-app.up.railway.app/bfhl`

### Deploy on Render

1. Go to [render.com](https://render.com) → **New Web Service**
2. Connect your GitHub repo
3. Set **Build Command**: `mvn clean package -DskipTests`
4. Set **Start Command**: `java -jar target/bfhl-api-1.0.0.jar`
5. Set **Environment**: Java 17

### Using Docker
```bash
docker build -t bfhl-api .
docker run -p 8080:8080 bfhl-api
```

---

## 📊 Logic — concat_string Explained

The `concat_string` is built from **all individual alphabetical characters** in the input (in order of appearance), then:

1. **Reversed** (character by character, including multi-char elements like "ABCD")
2. **Alternating caps** applied: index 0 → UPPER, index 1 → lower, index 2 → UPPER, ...

**Example C**: `["A", "ABCD", "DOE"]`
- All chars in order: `A, A, B, C, D, D, O, E`
- Reversed: `E, O, D, D, C, B, A, A`
- Alternating caps: `E, o, D, d, C, b, A, a`
- Result: `"EoDdCbAa"` ✅

---

## ⚙️ Configuration

Edit `src/main/resources/application.properties`:

```properties
bfhl.user.id=your_name_ddmmyyyy
bfhl.user.email=your@email.com
bfhl.user.roll-number=YOURROLL123
```

---

## 👤 Author

Built for BFHL Round — 27 June 2026
