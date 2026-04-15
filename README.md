# ACCM — Application Comicbook Collection Management

A monorepo for managing a personal comicbook collection across multiple platforms.

## Structure

```
accm/
├── backend/                  # Maven multi-module backend (Spring Boot 4)
│   ├── pom.xml               # Backend parent POM
│   └── comicbook/            # Comicbook collection REST API
│       └── src/
├── frontend/                 # Angular 21 SPA (SSR)
├── doc/                      # Architecture Decision Records and project documentation
├── docker-compose.yml        # Local infrastructure (PostgreSQL)
└── CHANGELOG.md
```

## Prerequisites

- Java 25
- Maven 3.9+
- Node.js 22+ / npm
- Docker & Docker Compose

## Getting started

### 1. Start the database

```bash
docker compose up -d
```

### 2. Run the backend

```bash
cd backend
mvn spring-boot:run -pl comicbook
```

The API is available at `http://localhost:8080`.

### 3. Run the frontend

```bash
cd frontend
npm install
npm start
```

The app is available at `http://localhost:4200`.

## Architecture

See [`doc/`](doc/) for Architecture Decision Records (ADRs) and technical documentation.

## Tech stack

| Layer    | Technology                                  |
|----------|---------------------------------------------|
| Backend  | Spring Boot 4, Java 25, PostgreSQL, Flyway  |
| Frontend | Angular 21, SSR, Signals, SCSS              |
| Testing  | Spock Framework, Testcontainers             |
| Infra    | Docker Compose, PostgreSQL 17               |
