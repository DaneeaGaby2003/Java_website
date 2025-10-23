# Orders API (Spring Boot 3.0, Java 17)

A simple Orders REST API using Spring Boot **3.0.9** and Java **17**.

- **Dev profile:** H2 in‑memory DB, H2 console at `/h2-console`
- **Prod profile:** PostgreSQL via environment variables
- **Docs:** OpenAPI UI at `/swagger-ui.html`

## Requirements
- Java 17+
- Maven 3.8+

## Run (dev / H2)
```bash
mvn spring-boot:run
```
The app runs on <http://localhost:8080>.

## Run (prod / PostgreSQL)
Set env vars and run:
```bash
export SPRING_PROFILES_ACTIVE=prod
export DB_URL=jdbc:postgresql://localhost:5432/ordersdb
export DB_USER=postgres
export DB_PASSWORD=postgres
mvn -DskipTests spring-boot:run
```

## API (key endpoints)
- `POST /api/v1/orders`
- `GET /api/v1/orders`
- `GET /api/v1/orders/{id}`
- `PUT /api/v1/orders/{id}`
- `DELETE /api/v1/orders/{id}`

See the included **Postman collection** in `/postman/Orders API.postman_collection.json`.

## Startup scripts
- Unix: `scripts/start.sh`
- Windows: `scripts/start.bat`

Each script accepts optional env vars for prod profile.

## Project Structure
```
src/main/java/com/example/orders
  ├── OrdersApiApplication.java
  ├── domain/
  ├── repository/
  ├── service/
  └── web/
src/main/resources/application.yml
postman/Orders API.postman_collection.json
scripts/start.sh, start.bat
DECISIONS.md
```

## Access for Digital NAO
Add collaborators on GitHub (`Settings` → `Collaborators`) or create a private link with read access for the **Digital NAO** team account(s).

## License
ISC
## Sprint 2 — Environment Configuration and Security

This sprint adds multiple environment profiles and secure variable management
for the Order Management System built with Spring Boot 3.0.

### 📁 Profiles included
- **application-dev.yml** — local development
- **application-test.yml** — testing
- **application-prod.yml** — production
- **application.yml** — base configuration

Each profile defines its own database, pagination, and security parameters.

### ⚙️ Environment Variables
All variables are listed in `.env.example`.
Copy it to `.env` (never commit `.env`) and edit as needed:
```bash
cp .env.example .env
