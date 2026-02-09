# Hospital Management System - Spring Boot

A robust, production-ready backend system for managing hospital operations. Built with Spring Boot and PostgreSQL, this system features secure authentication, concurrency-safe appointment booking, and enterprise-grade transactional design.

## 📋 Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Installation & Setup](#installation--setup)
- [Configuration](#configuration)
- [API Documentation](#api-documentation)
- [Database Schema](#database-schema)
- [Key Features Explained](#key-features-explained)
- [Running the Application](#running-the-application)
- [Testing](#testing)
- [Contributing](#contributing)
- [License](#license)
- [Contact](#contact)

---

## Overview

The Hospital Management System is a comprehensive backend solution designed to streamline hospital operations. This enterprise-level application provides:

- **Patient Management**: Complete patient registration, profile management, and medical history tracking
- **Doctor & Staff Administration**: Manage doctor profiles, specializations, and availability
- **Appointment Scheduling**: Concurrency-safe booking system preventing double-bookings
- **Department Management**: Organize and manage hospital departments
- **Billing System**: Generate invoices and track payments
- **User Authentication**: Secure JWT-based authentication with role-based access control

This system prioritizes data integrity, concurrency safety, and API reliability with retry-safe idempotent operations and database-level constraints.

**Key Highlights:**
- Built with industry-leading Spring Boot framework
- PostgreSQL for robust data management
- Concurrency-safe operations at the database level
- Idempotent APIs for retry safety
- Comprehensive transaction management
- RESTful API design
- Production-ready security features

---

## Features

### Core Features

#### 1. **Secure Authentication & Authorization**
- JWT (JSON Web Token) based authentication
- Role-based access control (RBAC)
- User registration and login endpoints
- Token refresh mechanism
- Password encryption using BCrypt

#### 2. **Patient Management**
- Patient registration and profile creation
- Medical history tracking
- Contact information management
- Emergency contact details
- Health insurance information
- Appointment history

#### 3. **Doctor Management**
- Doctor profile creation and management
- Specialization tracking
- License number verification
- Department assignment
- Availability scheduling
- Consultation fee management

#### 4. **Appointment Booking System**
- **Concurrency-Safe**: Database-level UNIQUE constraints prevent race conditions
- **No Double-Booking**: Guaranteed single appointment per doctor per time slot
- **Real-time Availability**: Check doctor availability before booking
- **Appointment Status Tracking**: Scheduled, completed, cancelled, no-show
- **Cancellation Support**: Easy appointment cancellation with audit trail
- **Rescheduling**: Patients can reschedule appointments

#### 5. **Department Management**
- Create and manage hospital departments
- Department descriptions and contact information
- Assign doctors to departments
- Track department operations

#### 6. **Billing & Invoice System**
- Generate patient invoices
- Track billing status
- Payment recording
- Insurance claim management
- Billing history

### Technical Features

#### 1. **Idempotent APIs**
- Retry-safe operations
- Idempotency-Key support
- Duplicate request prevention
- Safe API re-execution

#### 2. **Transactional Design**
- ACID compliance
- Multi-step operation atomicity
- Rollback on failure
- Data consistency guarantees

#### 3. **Exception Handling**
- Comprehensive error handling
- Custom exception classes
- Proper HTTP status codes
- Detailed error messages

#### 4. **Concurrency Safety**
- Database-level constraints
- No race conditions
- Thread-safe operations
- Pessimistic locking where needed

#### 5. **Security Features**
- SQL injection prevention
- CORS configuration
- HTTPS support ready
- Input validation
- Output encoding

#### 6. **Performance Features**
- Query optimization
- Database indexing
- Caching mechanisms
- Pagination support
- Lazy loading

---

## Tech Stack

### Backend Framework
- **Spring Boot**: 3.1.0 or higher
- **Java**: JDK 17 or higher
- **Maven**: 3.6.0 or higher

### Database
- **PostgreSQL**: 12.0 or higher
- **JDBC Driver**: Latest version
- **Connection Pooling**: HikariCP

### ORM & Data Access
- **Spring Data JPA**: Latest
- **Hibernate**: Latest
- **Liquibase/Flyway**: Database migrations

### Security & Authentication
- **Spring Security**: Latest
- **JWT (jjwt)**: 0.11.5 or higher
- **BCrypt**: Spring Security implementation

### API & Documentation
- **Springdoc OpenAPI**: 1.7.0 or higher
- **Swagger UI**: Auto-generated documentation
- **Spring REST**: RESTful API support

### Testing
- **JUnit 5**: Latest
- **Mockito**: Mocking framework
- **Spring Boot Test**: Integration testing
- **TestContainers**: Database container testing

### Additional Libraries
- **Lombok**: Reduce boilerplate code
- **Validation API**: javax.validation
- **Log4j2/Logback**: Logging framework
- **Apache Commons**: Utility libraries

### Development Tools
- **IDE**: IntelliJ IDEA, Eclipse, or VS Code
- **Build Tool**: Maven Wrapper included
- **Version Control**: Git
- **CI/CD**: GitHub Actions (optional)

---

## Project Structure

Hospital-Management-System---SpringBoot/ │ ├── src/ │ ├── main/ │ │ ├── java/ │ │ │ └── com/ │ │ │ └── hospital/ │ │ │ ├── HospitalManagementSystemApplication.java # Main application class │ │ │ │ │ │ │ ├── config/ # Configuration classes │ │ │ │ ├── SecurityConfig.java # Spring Security configuration │ │ │ │ ├── JwtConfig.java # JWT configuration │ │ │ │ ├── WebConfig.java # Web MVC configuration │ │ │ │ └── DatabaseConfig.java # Database configuration │ │ │ │ │ │ │ ├── controller/ # REST Controllers │ │ │ │ ├── AuthController.java # Authentication endpoints │ │ │ │ ├── PatientController.java # Patient management endpoints │ │ │ │ ├── DoctorController.java # Doctor management endpoints │ │ │ │ ├── AppointmentController.java # Appointment booking endpoints │ │ │ │ ├── DepartmentController.java # Department management endpoints │ │ │ │ ├── BillingController.java # Billing endpoints │ │ │ │ └── StaffController.java # Staff management endpoints │ │ │ │ │ │ │ ├── service/ # Business Logic │ │ │ │ ├── impl/ │ │ │ │ │ ├── AuthServiceImpl.java │ │ │ │ │ ├── PatientServiceImpl.java │ │ │ │ │ ├── DoctorServiceImpl.java │ │ │ │ │ ├── AppointmentServiceImpl.java │ │ │ │ │ ├── DepartmentServiceImpl.java │ │ │ │ │ ├── BillingServiceImpl.java │ │ │ │ │ └── StaffServiceImpl.java │ │ │ │ └── Interface files (Services) │ │ │ │ │ │ │ ├── repository/ # Data Access Layer │ │ │ │ ├── UserRepository.java │ │ │ │ ├── PatientRepository.java │ │ │ │ ├── DoctorRepository.java │ │ │ │ ├── AppointmentRepository.java │ │ │ │ ├── DepartmentRepository.java │ │ │ │ ├── BillingRepository.java │ │ │ │ └── StaffRepository.java │ │ │ │ │ │ │ ├── entity/ # JPA Entities │ │ │ │ ├── User.java │ │ │ │ ├── Patient.java │ │ │ │ ├── Doctor.java │ │ │ │ ├── Appointment.java │ │ │ │ ├── Department.java │ │ │ │ ├── Billing.java │ │ │ │ ├── Staff.java │ │ │ │ └── BaseEntity.java # Abstract base class │ │ │ │ │ │ │ ├── dto/ # Data Transfer Objects │ │ │ │ ├── UserDTO.java │ │ │ │ ├── PatientDTO.java │ │ │ │ ├── DoctorDTO.java │ │ │ │ ├── AppointmentDTO.java │ │ │ │ ├── DepartmentDTO.java │ │ │ │ ├── BillingDTO.java │ │ │ │ └── StaffDTO.java │ │ │ │ │ │ │ ├── exception/ # Custom Exceptions │ │ │ │ ├── ResourceNotFoundException.java │ │ │ │ ├── DuplicateResourceException.java │ │ │ │ ├── AppointmentBookingException.java │ │ │ │ ├── UnauthorizedException.java │ │ │ │ ├── ValidationException.java │ │ │ │ └── GlobalExceptionHandler.java │ │ │ │ │ │ │ ├── security/ # Security Classes │ │ │ │ ├── JwtTokenProvider.java │ │ │ │ ├── JwtAuthenticationFilter.java │ │ │ │ ├── UserDetailsServiceImpl.java │ │ │ │ └── AuthenticationFailureHandler.java │ │ │ │ │ │ │ ├── util/ # Utility Classes │ │ │ │ ├── DateUtil.java │ │ │ │ ├── ValidationUtil.java │ │ │ │ ├── EncryptionUtil.java │ │ │ │ └── IdempotencyUtil.java │ │ │ │ │ │ │ └── mapper/ # Entity-DTO Mappers │ │ │ ├── PatientMapper.java │ │ │ ├── DoctorMapper.java │ │ │ └── AppointmentMapper.java │ │ │ │ │ └── resources/ │ │ ├── application.yml # Main application properties │ │ ├── application-dev.yml # Development environment │ │ ├── application-prod.yml # Production environment │ │ ├── application-test.yml # Test environment │ │ ├── logback-spring.xml # Logging configuration │ │ └── db/ │ │ ├── migration/ # Flyway migrations │ │ │ ├── V1__init_database.sql │ │ │ ├── V2__create_users_table.sql │ │ │ ├── V3__create_patients_table.sql │ │ │ ├── V4__create_doctors_table.sql │ │ │ ├── V5__create_appointments_table.sql │ │ │ ├── V6__create_departments_table.sql │ │ │ ├── V7__create_billing_table.sql │ │ │ └── V8__add_indexes.sql │ │ └── seed/ # Initial data │ │ └── seed-data.sql │ │ │ └── test/ │ ├── java/ │ │ └── com/ │ │ └── hospital/ │ │ ├── controller/ # Controller tests │ │ │ ├── AuthControllerTest.java │ │ │ ├── PatientControllerTest.java │ │ │ ├── DoctorControllerTest.java │ │ │ └── AppointmentControllerTest.java │ │ ├── service/ # Service tests │ │ │ ├── AuthServiceTest.java │ │ │ ├── PatientServiceTest.java │ │ │ ├── DoctorServiceTest.java │ │ │ └── AppointmentServiceTest.java │ │ └── repository/ # Repository tests │ │ ├── PatientRepositoryTest.java │ │ ├── DoctorRepositoryTest.java │ │ └── AppointmentRepositoryTest.java │ └── resources/ │ ├── application-test.yml # Test configuration │ └── test-data.sql # Test data │ ├── .mvn/ # Maven wrapper ├── mvnw # Maven wrapper (Unix) ├── mvnw.cmd # Maven wrapper (Windows) ├── pom.xml # Maven dependencies ├── .gitignore # Git ignore rules ├── .gitattributes # Git attributes ├── README.md


---

## Prerequisites

### System Requirements
- **Operating System**: Windows, macOS, or Linux
- **RAM**: Minimum 4GB (8GB recommended)
- **Disk Space**: Minimum 2GB free space
- **Internet Connection**: Required for downloading dependencies

### Required Software

#### 1. **Java Development Kit (JDK)**
- **Version**: 17 or higher
- **Verification**:
  ```bash
  java -version

Installation & Setup
  # Clone the repository
git clone https://github.com/MOHDJUNAID70/Hospital-Management-System---SpringBoot.git

# Navigate to the project directory
cd Hospital-Management-System---SpringBoot

API Documentation
Auto-Generated Swagger Documentation
Once the application is running, access the interactive API documentation:

Swagger UI: http://localhost:7060/api/swagger-ui.html
OpenAPI JSON: http://localhost:7060/api/v3/api-docs
OpenAPI YAML: http://localhost:7060/api/v3/api-docs.yaml

Authentication API

<img width="1919" height="914" alt="Screenshot 2026-02-09 224747" src="https://github.com/user-attachments/assets/4829971a-a677-4085-b53a-883574b4da7a" />
<img width="1917" height="909" alt="Screenshot 2026-02-09 224805" src="https://github.com/user-attachments/assets/96080dd1-99e5-4749-9cdc-14dfa8a69f87" />
<img width="1919" height="717" alt="Screenshot 2026-02-09 224839" src="https://github.com/user-attachments/assets/c2154b16-4ebf-40ff-b128-021f17afafa1" />

Key Features Explained
1. Concurrency-Safe Appointment Booking
Problem: Without proper constraints, two users can simultaneously book the same doctor at the same time slot.
Solution: Database-level UNIQUE constraint prevents race conditions:
How It Works:
When booking appointment, database enforces uniqueness at the transaction level
If concurrent requests try to book the same slot, only one succeeds
Second request receives a unique constraint violation
Application handles this gracefully with appropriate error message

2. Idempotent APIs
Problem: Network failures might cause client to retry requests, leading to duplicate operations.
Solution: Idempotency-Key based idempotent operations:
How It Works:
Client provides unique Idempotency-Key header
Server stores the key and response
If same key received again, returns cached response
No duplicate operations occur

Contact
Author Information
Name: MOHD JUNAID
GitHub: @MOHDJUNAID70
Email: mohdjunaidlpu@gmail.com
Support & Questions
GitHub Issues: Report Bugs or Request Features
Discussions: GitHub Discussions
