# Task Management API

A RESTful API for task management built with **Java 21** and **Spring Boot 3.5**. The project provides JWT-based authentication, Spring Security authorization, and database migration with Flyway.

## Tech Stack

| Layer      | Technology                         |
| ---------- | ---------------------------------- |
| Language   | Java 21                            |
| Framework  | Spring Boot 3.5                    |
| Security   | Spring Security, JWT (jjwt 0.12.6) |
| ORM        | Spring Data JPA / Hibernate        |
| Database   | PostgreSQL                         |
| Migration  | Flyway                             |
| Build Tool | Maven                              |
| Utilities  | Lombok                             |

## Features

* JWT authentication for user registration and login
* Task CRUD operations
* User isolation so each user can only access their own tasks
* Layered architecture following Controller → Service → Repository separation
* Flyway-based database migration
* Postman collection for API testing

## Project Structure

```text
src/
├── main/
│   ├── java/com/taskmanagement/
│   │   ├── config/          # Security config, JWT filter
│   │   ├── controller/      # REST endpoints
│   │   ├── service/         # Business logic
│   │   ├── repository/      # JPA repositories
│   │   ├── entity/          # JPA entities
│   │   ├── dto/             # Request / Response DTOs
│   │   └── exception/       # Global exception handling
│   └── resources/
│       ├── application.properties
│       └── db/migration/    # Flyway SQL scripts
└── test/
```

## Getting Started

### Prerequisites

* Java 21+
* PostgreSQL
* Maven 3.8+

### 1. Clone the repository

```bash
git clone https://github.com/NguyenLeDuyy/Task-Management-API.git
cd Task-Management-API
```

### 2. Configure the database

Create a PostgreSQL database:

```sql
CREATE DATABASE task_management;
```

Update `src/main/resources/application.properties` with your database and JWT configuration:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/task_management
spring.datasource.username=your_username
spring.datasource.password=your_password

app.jwt.secret=your_jwt_secret_key
app.jwt.expiration-ms=86400000
```

### 3. Run the application

```bash
./mvnw spring-boot:run
```

The API will be available at `http://localhost:8080`.

Flyway will automatically apply database migrations on startup.

## API Endpoints

### Auth

| Method | Endpoint             | Description                   |
| ------ | -------------------- | ----------------------------- |
| POST   | `/api/auth/register` | Register a new user           |
| POST   | `/api/auth/login`    | Login and receive a JWT token |

### Tasks

> All task endpoints require the header `Authorization: Bearer <token>`.

| Method | Endpoint          | Description                        |
| ------ | ----------------- | ---------------------------------- |
| GET    | `/api/tasks`      | Get all tasks for the current user |
| GET    | `/api/tasks/{id}` | Get task by ID                     |
| POST   | `/api/tasks`      | Create a new task                  |
| PUT    | `/api/tasks/{id}` | Update a task                      |
| DELETE | `/api/tasks/{id}` | Delete a task                      |

## Example Request

Create a task:

```bash
curl -X POST http://localhost:8080/api/tasks \
  -H "Authorization: Bearer <your_token>" \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Review pull request",
    "description": "Review and merge feature/auth branch",
    "status": "TODO"
  }'
```

## Example Response

```json
{
  "id": 1,
  "title": "Review pull request",
  "description": "Review and merge feature/auth branch",
  "status": "TODO",
  "createdAt": "2026-05-31T10:00:00"
}
```

## Testing with Postman

A Postman collection is included in the `/postman` directory.

It can be used to test:

* Register and login endpoints
* JWT authorization flow
* Task CRUD endpoints
* Access control for user-owned tasks

## Database Migrations

Database migrations are managed by **Flyway** and run automatically when the application starts.

Migration scripts are located in:

```text
src/main/resources/db/migration/
```

## Notes

* Make sure PostgreSQL is running before starting the application.
* Ensure the JWT secret is set correctly in `application.properties`.
* Use a valid access token when calling protected task endpoints.

## Author

**Nguyen Le Duy**
GitHub: [NguyenLeDuyy](https://github.com/NguyenLeDuyy)
