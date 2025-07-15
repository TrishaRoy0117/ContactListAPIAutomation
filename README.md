Project Overview

This project automates API testing for the Contact List web application hosted at:

> [https://thinking-tester-contact-list.herokuapp.com](https://thinking-tester-contact-list.herokuapp.com)

It covers full end-to-end API workflows including user registration, login, authentication, contact creation, retrieval, update, and logout using dynamic token-based session handling.

---

## ✅ Test Cases Automated

| TC No. | Test Case Description           | Endpoint                   | Method |
|--------|----------------------------------|----------------------------|--------|
| TC01   | Register New User               | `/users`                   | POST   |
| TC02   | Get Logged-in User Profile      | `/users/me`                | GET    |
| TC03   | Update User Info                | `/users/me`                | PATCH  |
| TC04   | Login User                      | `/users/login`             | POST   |
| TC05   | Add New Contact                 | `/contacts`                | POST   |
| TC06   | Get All Contacts                | `/contacts`                | GET    |
| TC07   | Get Contact by ID               | `/contacts/{id}`           | GET    |
| TC08   | Update Full Contact             | `/contacts/{id}`           | PUT    |
| TC09   | Update Partial Contact          | `/contacts/{id}`           | PATCH  |
| TC10   | Logout User                     | `/users/logout`            | POST   |

---

## 🧱 Framework Structure

src/test/java/com/telecom/api/
├── base/
│ └── BaseTest.java # Shared base config and token mgmt
├── tests/
│ ├── UserTests.java # User registration, login, update
│ └── ContactTests.java # Contact create, get, update, logout
└── utils/
└── (Optional: PayloadGenerator.java)

