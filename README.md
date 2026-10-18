# Playwright Java Framework – E2E + API Automation

A clean, production-ready **Playwright automation framework** built with **Java + TestNG + Maven + Page Object Model**.

This project contains:

1. **UI End-to-End Test** → Complete purchase flow on SauceDemo
2. **API Tests** → Using Playwright’s built-in API testing (`APIRequestContext`)

---

## Problem Statement

### UI Part
Automate the critical business flow of an e-commerce application (SauceDemo):
1. Login
2. Add product to cart
3. Checkout
4. Place order
5. Verify confirmation

### API Part
Demonstrate Playwright’s powerful built-in API testing:
- GET list of users
- GET single user
- POST (Create user)
- PUT (Update user)

---

## Tech Stack

| Technology       | Purpose                              |
|------------------|--------------------------------------|
| Java 17          | Programming language                 |
| Playwright       | UI + API automation                  |
| TestNG           | Test framework & assertions          |
| Maven            | Build & dependency management        |
| Page Object Model| Design pattern                       |

---

## Project Architecture

```
PlaywrightJavaFramework/
├── pom.xml
├── testng.xml
├── README.md
├── src/
│   ├── main/java/com/automation/
│   │   ├── base/BaseTest.java
│   │   ├── pages/                  → LoginPage, InventoryPage, CartPage, etc.
│   │   └── utils/
│   │       ├── BrowserFactory.java
│   │       └── ConfigReader.java
│   └── test/
│       ├── java/com/automation/tests/
│       │   ├── PurchaseFlowTest.java   → UI E2E test
│       │   └── ApiTest.java            → API tests (Playwright built-in)
│       └── resources/
│           └── config.properties
```

---

## How to Run

### Prerequisites
- JDK 17+
- IntelliJ IDEA
- Internet connection

### Steps

1. Open the project in IntelliJ
2. Wait for Maven import
3. Install browsers (one-time):
   ```bash
   mvn exec:java -e -Dexec.mainClass=com.microsoft.playwright.CLI -Dexec.args="install"
   ```
4. Run tests:
   - Right-click on `PurchaseFlowTest.java` → Run (UI test)
   - Right-click on `ApiTest.java` → Run (API tests)
   - Or run everything: `mvn clean test`

---

## API Testing with Playwright (Important)

Playwright has **built-in API testing** using `APIRequestContext`.

**Advantages:**
- No need for Rest Assured or extra libraries
- Same tool for UI + API
- Easy to mix UI and API in one test
- Fast and reliable

**Example from this project:**

```java
APIRequestContext apiContext = playwright.request().newContext(
    new APIRequest.NewContextOptions().setBaseURL("https://reqres.in")
);

APIResponse response = apiContext.get("/api/users/2");
Assert.assertEquals(response.status(), 200);
```

---

## Configuration

Edit `src/test/resources/config.properties`:

```properties
browser=chromium
headless=false
timeout=30000
username=standard_user
password=secret_sauce
```

---

## How to Push to GitHub

```bash
git init
git add .
git commit -m "Playwright Java Framework - UI + API Testing"
git branch -M main
git remote add origin https://github.com/YOUR_USERNAME/PlaywrightJavaFramework.git
git push -u origin main
```

---

Built while learning **Rahul Shetty Playwright with Java**.
