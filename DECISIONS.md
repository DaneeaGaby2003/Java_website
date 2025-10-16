# Decisions & Rationale

- **Tech stack:** Spring Boot 3.0.9 + Java 17 to meet requirements and ensure modern baseline.
- **Persistence:** JPA/Hibernate with H2 (dev) and PostgreSQL (prod). Rationale: easy local dev, production-ready RDBMS.
- **Data model:** `Order` ↔ `OrderItem` (1‑to‑many) with `OrderStatus` enum. Keeps items normalized and supports totals.
- **Validation & errors:** Bean Validation + `@ControllerAdvice` to return friendly error payloads.
- **OpenAPI:** springdoc starter for quick interactive docs (`/swagger-ui.html`).

## Changes Log
- 2025-10-15: Initial scaffold, CRUD endpoints, profiles, Postman, scripts, documentation.
