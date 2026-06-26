# 🎓 Student Management System REST API

A secure and scalable **Student Management System REST API** developed using **Spring Boot**. This project was built as part of a technical assessment to demonstrate backend development skills, including authentication, authorization, RESTful API design, database management, and clean architecture.

---

## 🚀 Features

### 🔐 Authentication & Security

* JWT Authentication
* Role-Based Authorization
* Admin Login
* Student Login
* Stateless Session Management using Spring Security
* BCrypt Password Encryption

### 👨‍💼 Admin Module

* Create Student
* Update Student
* Delete Student
* Get Student by ID
* Get All Students
* Search Students by Name
* Create Course
* Update Course
* Delete Course
* Get Course by ID
* Get All Courses
* Assign Course to Student
* View Students Assigned to a Course

### 👨‍🎓 Student Module

* View Logged-in Student Profile
* Update Student Profile
* View Assigned Courses
* View Topics of Assigned Courses
* Leave Assigned Course

### ✅ Additional Features

* Global Exception Handling
* Request Validation
* DTO & Mapper Pattern
* Swagger/OpenAPI Documentation
* Layered Architecture
* Unit Testing using JUnit & Mockito

---

# 🛠️ Tech Stack

| Technology        | Version           |
| ----------------- | ----------------- |
| Java              | 21                |
| Spring Boot       | 3.x               |
| Spring Security   | Latest            |
| Spring Data JPA   | Latest            |
| Hibernate         | Latest            |
| MySQL             | 8.x               |
| JWT               | JJWT              |
| Maven             | Latest            |
| Lombok            | Latest            |
| Swagger / OpenAPI | springdoc-openapi |
| JUnit 5           | Latest            |
| Mockito           | Latest            |

---

# 📁 Project Structure

```
src/main/java
│
├── config
├── controller
├── dto
│   ├── request
│   └── response
├── entity
├── enums
├── exception
├── mapper
├── repository
├── security
├── service
│   ├── AdminService
│   ├── StudentService
│   └── serviceImpl
└── StudentManagementApplication
```

---

# 🔐 Authentication

The application uses **JWT (JSON Web Token)** authentication.

### Public APIs

```
POST /api/admin/login
POST /api/student/login
```

After successful login, include the generated JWT token in every secured request.

```
Authorization: Bearer <JWT_TOKEN>
```

---

# 📚 REST APIs

## 👨‍💼 Admin APIs

| Method | Endpoint                                           | Description                     |
| ------ | -------------------------------------------------- | ------------------------------- |
| POST   | `/api/admin/login`                                 | Admin Login                     |
| POST   | `/api/admin/students`                              | Create Student                  |
| PUT    | `/api/admin/students/{id}`                         | Update Student                  |
| DELETE | `/api/admin/students/{id}`                         | Delete Student                  |
| GET    | `/api/admin/students/{id}`                         | Get Student by ID               |
| GET    | `/api/admin/students`                              | Get All Students                |
| GET    | `/api/admin/students/search?name=`                 | Search Students by Name         |
| POST   | `/api/admin/courses`                               | Create Course                   |
| PUT    | `/api/admin/courses/{id}`                          | Update Course                   |
| DELETE | `/api/admin/courses/{id}`                          | Delete Course                   |
| GET    | `/api/admin/courses/{id}`                          | Get Course by ID                |
| GET    | `/api/admin/courses`                               | Get All Courses                 |
| POST   | `/api/admin/courses/{courseId}/assign/{studentId}` | Assign Course to Student        |
| GET    | `/api/admin/courses/{courseId}/students`           | Get Students Assigned to Course |

---

## 👨‍🎓 Student APIs

| Method | Endpoint                          | Description                   |
| ------ | --------------------------------- | ----------------------------- |
| POST   | `/api/student/login`              | Student Login                 |
| GET    | `/api/student/profile`            | Get Logged-in Student Profile |
| PUT    | `/api/student/profile`            | Update Student Profile        |
| GET    | `/api/student/courses`            | View Assigned Courses         |
| GET    | `/api/student/topics`             | View Assigned Topics          |
| DELETE | `/api/student/courses/{courseId}` | Leave Assigned Course         |

---

# 📄 API Documentation

Swagger UI

```
http://localhost:8080/swagger-ui/index.html
```

OpenAPI Specification

```
http://localhost:8080/v3/api-docs
```

---

# ⚙️ Getting Started

## Clone Repository

```bash
git clone https://github.com/ankushkumarmali01/student.git
```


## Configure Database

Update your `application.properties`

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/student_management
spring.datasource.username=root
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update

jwt.secret=YOUR_SECRET_KEY
jwt.expiration=86400000
```

---

## Run Application

```bash
mvn clean install
```

```bash
mvn spring-boot:run
```

or run the `StudentManagementApplication` class from your IDE.

---

# 🗄️ Database Tables

The application automatically creates the following tables:

* admins
* students
* addresses
* courses
* topics
* student_course

---

# 📦 Request Validation

The project uses **Jakarta Validation** for validating request payloads.

Examples:

* `@NotBlank`
* `@NotNull`
* `@NotEmpty`
* `@Email`
* `@Valid`

---

# ⚠️ Exception Handling

A centralized `GlobalExceptionHandler` handles application exceptions.

Handled exceptions include:

* ResourceNotFoundException
* DuplicateResourceException
* BadRequestException
* UnauthorizedException

---

# 🧪 Unit Testing

Service layer unit testing is implemented using:

* JUnit 5
* Mockito

Covered scenarios include:

* Student Login
* Student Profile
* Course Operations
* Exception Handling

---

# 🏗️ Architecture

The project follows a layered architecture:

```
Controller
      │
Service
      │
Repository
      │
Database
```

Additional layers:

* DTO
* Mapper
* Security
* Exception Handling

---

# 👨‍💻 Author

**Ankush Kumar**

GitHub:
https://github.com/ankushkumarmali01


---

# 📄 License

This project was developed for a **technical assessment** and is intended for educational and demonstration purposes only.
