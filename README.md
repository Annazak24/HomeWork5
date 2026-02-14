# Homework 5 – Stub & API Testing (Spring + WireMock + Selenoid)

## 📌 Project Description
This project implements Stub server and API testing according to Homework #5 requirements.

This project demonstrates a complete test automation setup including:

-WireMock Stub Server
-API Helpers (HTTP + SOAP)
-Tests written using Spring (RestTemplate)
-JSON schema validation
-Cucumber tests
-UI test with Selenium
-Selenoid integration (Docker)
-Docker-based environment

---
## 🔹 Stub Endpoints

The WireMock server provides the following endpoints:

- `GET /user/get/all`
- `GET /cource/get/all`
- `GET /user/get/{id}`

All responses are validated with JSON Schema.

---

## 🔹 Implemented Tests

### ✅ Stub Server Tests  
Validates stub responses and status codes.

### ✅ JSON Schema Tests  
Ensures API responses match expected schema.

### ✅ HTTP Helper Tests  
Custom HTTP client logic validation.

### ✅ SOAP Helper Tests  
SOAP request/response validation.

### ✅ Cucumber Tests  
BDD scenarios executed via JUnit Platform.

### ✅ UI Test  
Frontend validation using Selenium.

UI tests support:
- Local execution
- Remote execution via Selenoid

---

## 🛠 Tech Stack

- Java 17
- Maven
- WireMock 3
- Rest-Assured
- Selenium 4
- Cucumber 7
- JUnit 5
- Selenoid
- Docker / Docker Compose

---

## 🚀 How to Run

### 1️⃣ Start Selenoid

```bash
docker-compose up -d

2️⃣ Run All Tests
mvn clean test
This will execute:
Stub tests
Schema validation
Helpers tests
Cucumber tests
UI tests (remote)

🔄 Execution Mode
Default mode: Remote (Selenoid)
To run locally:
mvn clean test -Drun.type=local

🧩 Architecture Highlights

Clean test separation
Remote browser execution
Docker-based reproducible environment
Fully runnable with two commands


