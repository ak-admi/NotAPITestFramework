# REST Assured API Testing Framework
### Java | Rest Assured | TestNG | Maven

![Java](https://img.shields.io/badge/Java-11+-orange)
![Rest Assured](https://img.shields.io/badge/RestAssured-5.4.0-green)
![TestNG](https://img.shields.io/badge/TestNG-7.9.0-blue)
![Maven](https://img.shields.io/badge/Maven-3.x-red)
![Status](https://img.shields.io/badge/Status-In%20Progress-yellow)

---

## 📖 About

This is a hands-on API test automation framework built from scratch while learning REST Assured.  
The framework is built step by step to understand each concept deeply rather than using a pre-built template.

**API Under Test:** [JSONPlaceholder](https://jsonplaceholder.typicode.com) — a free public REST API for practice.

---

## 🏗️ Project Structure

```
src/
└── test/
    └── java/
        ├── base/
        │   └── BaseTest.java            # Common setup - Base URI, RequestSpec
        ├── models/
        │   └── PostRequest.java         # POJO model for request body
        ├── tests/
        │   ├── GetRequestTest.java      # GET request tests
        │   ├── CrudOperationsTest.java  # POST, PUT, PATCH, DELETE tests
        │   └── SteppingStone.java       # Scratch file - first raw tests
        └── utils/
            └── (coming soon)
```

---

## 🛠️ Tech Stack

| Tool | Purpose | Version |
|------|---------|---------|
| Java | Programming language | 11+ |
| Rest Assured | API testing library | 5.4.0 |
| TestNG | Test framework | 7.9.0 |
| Jackson | JSON serialization/deserialization | 2.16.1 |
| Maven | Build and dependency management | 3.x |
| IntelliJ IDEA | IDE | 2025.x |

---

## ✅ Test Coverage

### GET Tests (`GetRequestTest.java`)
- Verify status code 200 on list endpoint
- Verify response body fields using Hamcrest matchers
- Extract values from response using JsonPath
- Verify 404 for non-existent resource

### CRUD Tests (`CrudOperationsTest.java`)
- POST — Create resource using HashMap body
- PUT — Full update (replace entire resource)
- PATCH — Partial update (only changed fields)
- DELETE — Remove resource

---

## 🚀 How to Run

### Prerequisites
- Java JDK 11+
- Maven 3.x
- IntelliJ IDEA

### Clone the repo
```bash
git clone <your-repo-url>
cd NotAPITestFramework
```

### Run all tests
```bash
mvn test
```

### Run a specific test class
```bash
mvn test -Dtest=GetRequestTest
```

### Run from IntelliJ
Right-click any test class or method → **Run**

---

## 🧠 Key Concepts Covered

**BDD Syntax (given / when / then)**
```java
given()               // Setup  - headers, body, params
.when()               // Action - GET, POST, PUT, DELETE
    .get("/posts")
.then()               // Assert - status code, body, headers
    .statusCode(200);
```

**Request Specification (BaseTest)**  
Common settings like base URL and content type are defined once in `BaseTest` and inherited by all test classes — no repetition.

**HashMap vs POJO for request body**  
- HashMap → quick and simple, no type safety  
- POJO → clean, type-safe, IDE autocomplete, preferred in large projects

**PUT vs PATCH**  
- PUT replaces the entire resource — all fields must be sent  
- PATCH partially updates — only send the fields you want to change

---

## 📦 Dependencies (`pom.xml`)

```xml
<dependencies>
    <dependency>
        <groupId>io.rest-assured</groupId>
        <artifactId>rest-assured</artifactId>
        <version>5.4.0</version>
        <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>org.testng</groupId>
        <artifactId>testng</artifactId>
        <version>7.9.0</version>
        <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-databind</artifactId>
        <version>2.16.1</version>
    </dependency>
</dependencies>
```

---

## 🗺️ Roadmap

- [x] Project setup with Maven
- [x] BaseTest with RequestSpecification
- [x] GET request tests
- [x] CRUD operations (POST, PUT, PATCH, DELETE)
- [ ] POJO based request/response
- [ ] Utility/Helper classes
- [ ] testng.xml suite configuration
- [ ] Test reporting (ExtentReports)
- [ ] Data driven testing

---

## 👨‍💻 Author

Built while learning API test automation for a QA Engineer role.  
Framework is intentionally built step by step — each commit reflects a new concept learned.
