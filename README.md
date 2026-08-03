# ConectaEmpleo

A web platform that connects employers and job seekers, facilitating the publication of job offers, job applications, training courses, and work contracts. ConectaEmpleo is developed in alignment with the United Nations Sustainable Development Goal 1 (SDG 1: No Poverty), promoting access to employment and improving labor opportunities for underserved communities.

## The Problem It Solves

Access to employment is one of the most effective ways to break the cycle of poverty. However, job seekers often face barriers such as scattered job listings, lack of visibility for their skills, and limited access to training opportunities. On the other side, employers struggle to find qualified candidates efficiently.

ConectaEmpleo addresses these issues by providing a single digital platform that:

- Connects job seekers and employers directly through a structured job marketplace.
- Lets workers build a profile that reflects their experience, location, and average rating, giving them visibility in the labor market.
- Provides a training module where users can publish and enroll in courses that improve employability.
- Manages the full job lifecycle, from application to contract creation and mutual ratings.
- Generates aggregate statistics that can inform public policy against poverty.

Developed by Mateo Cobo.

## Table of Contents

- [Features](#features)
- [Technologies](#technologies)
- [Project Structure](#project-structure)
- [Getting Started](#getting-started)
- [Running the Tests](#running-the-tests)
- [Contributing](#contributing)
- [Roadmap](#roadmap)
- [License](#license)

## Features

- **User Management**: Registration and login with user profiles based on four roles: worker (`TRABAJADOR`), employer (`SOLICITANTE`), trainer (`CAPACITADOR`), and administrator (`ADMIN`).
- **Job Postings**: Create and list job offers, with search and filtering by category or location.
- **Job Applications**: Workers can apply to job offers; employers can accept applications and generate an associated contract.
- **Training Courses**: Publication and enrollment in training courses to strengthen employability skills.
- **Contracts**: Contract generation from accepted applications, supporting mutual ratings and comments.
- **Ratings**: Rating system (1-5) that updates a user's average score after finishing a job or contract.
- **Reports**: A statistics dashboard showing system-wide metrics such as the number of users, jobs, applications, training courses, and enrollments.
- **Notifications**: Data model designed for future notification features.

## Technologies

- **Backend**: Java 17, Spring Boot 3.5.3
- **Web Framework**: Spring Web (Spring MVC), Thymeleaf templates, HTML, CSS
- **Persistence**: Spring Data JPA, Hibernate ORM
- **Database**: PostgreSQL
- **Build Tool**: Maven (with Maven Wrapper included)
- **Testing**: JUnit 5 (Spring Boot Starter Test)
- **Architecture**: Model-View-Controller (MVC) layered over services and repositories

## Project Structure

```
ConectaEmpleo/
├── src/
│   ├── main/
│   │   ├── java/Grupo12/ConectaEmpleo/
│   │   │   ├── Controller/      # HTTP request handlers (web layer)
│   │   │   ├── Service/         # Business logic layer
│   │   │   ├── Repository/      # Spring Data JPA repositories
│   │   │   └── Model/           # JPA entities and enums
│   │   └── resources/
│   │       ├── static/css/      # Stylesheets
│   │       └── templates/       # Thymeleaf HTML templates
│   └── test/java/Grupo12/ConectaEmpleo/  # Unit tests
├── pom.xml                      # Maven build configuration
├── mvnw / mvnw.cmd              # Maven Wrapper scripts
└── README.md
```

### Domain Model

The application is built around the following core entities:

- **Usuario** (User): Profile data, role, location, experience, and average rating.
- **Trabajo** (Job): Job offer with title, description, category, location, and state (`ACTIVO`/`FINALIZADO`).
- **Postulacion** (Application): A worker's application to a job, with state (`PENDIENTE`/`ACEPTADO`/`RECHAZADO`).
- **Capacitacion** (Training Course): Training offer with title, description, category, and date.
- **ParticipanteCapacitacion** (Training Enrollment): Enrollment of a user in a course, with certificate and grade fields.
- **Contrato** (Contract): Work contract linked to an accepted application, holding ratings and comments.
- **Notificacion** (Notification): Notification records associated with a user.

## Getting Started

### Prerequisites

- JDK 17 or higher
- Maven 3.x (or use the included Maven Wrapper `./mvnw`)
- PostgreSQL 12 or higher

### Database Setup

1. Ensure PostgreSQL is running on `localhost:5432`.
2. Create a database named `db_conecta_empleo`.

```sql
CREATE DATABASE db_conecta_empleo;
```

3. Configure the database connection in `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/db_conecta_empleo
spring.datasource.username=postgres
spring.datasource.password=admin
spring.jpa.hibernate.ddl-auto=update
```

> Note: Adjust the username and password to match your local PostgreSQL installation. The `spring.jpa.hibernate.ddl-auto=update` setting lets Hibernate create and update the schema automatically.

### Installation Steps

1. Clone the repository:

```bash
git clone https://github.com/<your-username>/ConectaEmpleo.git
cd ConectaEmpleo
```

2. Build the project:

```bash
mvn clean package
```

Or with the Maven Wrapper:

```bash
./mvnw clean package
```

3. Run the application:

```bash
mvn spring-boot:run
```

4. Open your browser and go to `http://localhost:8080`.

## Running the Tests

Execute the unit test suite with:

```bash
mvn test
```

Or using the Maven Wrapper:

```bash
./mvnw test
```

## Contributing

Contributions are welcome. Please follow the workflow below.

### Reporting Issues

If you find a bug or have a feature request, open an issue describing the problem, the steps to reproduce it, and the expected behavior.

### Development Workflow

1. **Fork the repository** and clone your fork locally.
2. **Create a feature branch** from `main`:

```bash
git checkout -b feature/your-feature-name
```

3. **Make your changes**, following the existing code style and conventions:
   - Keep the layered architecture: Controllers handle HTTP, Services contain business logic, Repositories access data.
   - Use the existing MVC naming conventions for classes and templates.
   - Add or update unit tests in `src/test/java` for any business logic you change.
4. **Run the tests** to make sure everything passes:

```bash
mvn test
```

5. **Commit your changes** with a clear, descriptive message.
6. **Push** your branch and open a **pull request** against `main`. Describe the changes you made and reference any related issues.

### Code of Conduct

Be respectful and constructive. This project is meant to be a collaborative space where anyone can contribute to improving employment access.

## Roadmap

- Real-time notifications for applications, contracts, and ratings.
- Advanced search with matching algorithms.
- Mobile application version.
- Integration with external APIs for credential verification.
- Frontend modernization with modern frameworks such as React.
- Machine learning analysis for employability predictions.

## License

Copyright © 2024 Mateo Cobo. All rights reserved.

This project is the exclusive property of Mateo Cobo and may not be copied, distributed, modified, or used in any way without the express written permission of the copyright holder. See the [LICENSE](LICENSE) file for details.
