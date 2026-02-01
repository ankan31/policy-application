# Policy Application

A Spring Boot REST API for managing insurance policies and agents with JWT-based authentication.

## Project Overview

This application provides a secure API for insurance policy management. It supports:
- **User authentication** via JWT tokens
- **Role-based access control** (Admin, Agent, Policy Holder)
- **Agent management** - view agent details
- **Policy management** - list and retrieve policy information

---

## Tech Stack

| Technology | Version |
|------------|---------|
| Java | 21 |
| Spring Boot | 3.5.10 |
| Spring Security | JWT Authentication |
| Spring Data JPA | Hibernate ORM |
| MySQL | Runtime database |
| Maven | Build tool |
| Lombok | 1.18.30 |
| JJWT | 0.12.5 |

---

## Prerequisites

- **Java 21** or higher
- **Maven 3.6+**
- **MySQL** database server
- (Optional) **Postman** for API testing

---

## Setup & Configuration

### 1. Clone the Repository

```bash
git clone <repository-url>
cd policy-application
```

### 2. Configure Environment Variables

The application uses environment variables for database configuration. Set the following:

| Variable | Description | Example |
|----------|-------------|---------|
| `MYSQLHOST` | MySQL server hostname | `localhost` |
| `MYSQLPORT` | MySQL server port | `3306` |
| `MYSQLDATABASE` | Database name | `policydb` |
| `MYSQLUSER` | Database username | `root` |
| `MYSQLPASSWORD` | Database password | `yourpassword` |

**Linux/macOS:**
```bash
export MYSQLHOST=localhost
export MYSQLPORT=3306
export MYSQLDATABASE=policydb
export MYSQLUSER=root
export MYSQLPASSWORD=yourpassword
```

**Windows (PowerShell):**
```powershell
$env:MYSQLHOST="localhost"
$env:MYSQLPORT="3306"
$env:MYSQLDATABASE="policydb"
$env:MYSQLUSER="root"
$env:MYSQLPASSWORD="yourpassword"
```

### 3. Create the Database

```sql
CREATE DATABASE policydb;
```

The application uses `spring.jpa.hibernate.ddl-auto=update`, so tables will be created automatically on startup.

---

## Running the Application

### Using Maven

```bash
# Build the project
mvn clean install

# Run the application
mvn spring-boot:run
```

### Using JAR

```bash
mvn clean package
java -jar target/agent-0.0.1-SNAPSHOT.jar
```

The application starts at **`http://localhost:8080`** by default.

---

## Using the Postman Collection

### Collection Location

The Postman collection file is located at:
```
postman_collection.json
```
in the project root directory.

### Importing the Collection

1. Open **Postman**
2. Click **Import** (top-left corner)
3. Drag and drop `postman_collection.json` or click **Upload Files**
4. The collection **"Policy Application API"** will appear in your sidebar

### Setting Up Variables

The collection uses two variables:

| Variable | Description | Example Value |
|----------|-------------|---------------|
| `{{baseUrl}}` | API base URL | `http://localhost:8080` |
| `{{token}}` | JWT authentication token | *(obtained from /auth)* |

**To set variables:**
1. Click on the collection name → **Variables** tab
2. Add `baseUrl` with value `http://localhost:8080`
3. Add `token` (leave initial value empty)

### Authentication Workflow

1. **Send the authentication request:**
   - Open `Authentication` → `POST /auth`
   - Update the request body with valid credentials:
     ```json
     {
       "email": "user@example.com",
       "password": "yourpassword"
     }
     ```
   - Click **Send**

2. **Copy the token from response:**
   ```json
   {
     "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
     "expiry": "2024-02-01T12:00:00"
   }
   ```

3. **Set the `{{token}}` variable:**
   - Go to collection **Variables**
   - Paste the token value into the `token` variable
   - Click **Save**

4. **Use protected endpoints:**
   - All other requests automatically include `Authorization: Bearer {{token}}`

---

## Common Endpoints Summary

| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| `POST` | `/auth` | Authenticate and get JWT token | ❌ |
| `GET` | `/agent/` | Get agent details (admin: all, others: own) | ✅ |
| `GET` | `/policy/` | List policies for authenticated user | ✅ |
| `GET` | `/policy/{policyNumber}` | Get specific policy details | ✅ |

### User Roles

- **ROLE_ADMIN** - Full access to all agents and policies
- **ROLE_AGENT** - Access to own agent info and assigned policies
- **ROLE_USER** - Access to own policy information

---

## Testing

### Running Unit Tests

```bash
mvn test
```

### Running All Tests (including integration)

```bash
mvn verify
```

### Test Location

Test files are located in:
```
src/test/java/com/ankan/insurance/agent/
```

### Manual API Testing

Use the provided Postman collection to manually test all endpoints. Ensure:
1. The application is running
2. Database is configured and accessible
3. Valid user credentials exist in the database

---

## Troubleshooting

| Issue | Solution |
|-------|----------|
| `401 Unauthorized` | Token expired or invalid. Re-authenticate via `/auth` |
| `403 Forbidden` | User lacks permission for the requested resource |
| `Connection refused` | Ensure app is running on correct port |
| `Database connection failed` | Verify MySQL is running and env variables are set |

---

## License

This project is for demonstration purposes.
