# Backend Service

A Spring Boot backend service built with Java 17 and modern Spring technologies.

## Prerequisites

Before running this application, make sure you have the following installed:

- **JDK 17+** - [Download and install JDK](https://adoptium.net/)
- **Maven 3.5+** - [Download and install Maven](https://maven.apache.org/download.cgi)
- **IntelliJ IDEA** - [Download and install IntelliJ](https://www.jetbrains.com/idea/)
- **Docker** - [Download and install Docker](https://www.docker.com/get-started)

## Technical Stack

- **Java 17**
- **Maven 3.5+**
- **Spring Boot 3.3.4**
- **Spring Data Validation**
- **Spring Data JPA**
- **PostgreSQL/MySQL** (optional)
- **Lombok**
- **Spring Boot DevTools**
- **Docker**
- **Docker Compose**

## Build & Run Application

### Option 1: Run with Maven Wrapper

Navigate to the `backend-service` folder and run:

```bash
./mvnw spring-boot:run
```

### Option 2: Run with Docker

1. **Build the application:**
   ```bash
   mvn clean install -P dev
   ```

2. **Build Docker image:**
   ```bash
   docker build -t backend-service:latest .
   ```

3. **Run Docker container:**
   ```bash
   docker run -it -p 8080:8080 --name backend-service backend-service:latest
   ```

## Testing

### Health Check

Test the application health endpoint using cURL:

```bash
curl --location 'http://localhost:8080/actuator/health'
```

**Expected Response:**
```json
{
  "status": "UP"
}
```

### API Testing

Once the application is running, you can access the backend service at:
- **Base URL:** `http://localhost:8080`
- **Health Endpoint:** `http://localhost:8080/actuator/health`

Use tools like Postman, Insomnia, or cURL to test your APIs.

## Project Structure

```
backend-service/
├── src/
│   ├── main/
│   │   ├── java/
│   │   └── resources/
│   └── test/
├── target/
├── Dockerfile
├── docker-compose.yml
├── mvnw
├── mvnw.cmd
├── pom.xml
└── README.md
```

## Development

### Running in Development Mode

The application includes Spring Boot DevTools for hot reloading during development. Simply run:

```bash
./mvnw spring-boot:run
```

### Database Configuration

The application supports both PostgreSQL and MySQL databases. Configure your database connection in `application.properties` or `application.yml`.

## Docker Support

### Using Docker Compose

If you have a `docker-compose.yml` file, you can run the entire stack with:

```bash
docker-compose up -d
```

### Stopping the Application

- **Maven:** Press `Ctrl+C` in the terminal
- **Docker:** 
  ```bash
  docker stop backend-service
  docker rm backend-service
  ```

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test your changes
5. Submit a pull request

## License

This project is licensed under the MIT License - see the LICENSE file for details.
```

