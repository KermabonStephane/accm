# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

### Added
- Root Maven multi-module parent POM (`com.accm:accm-backend-parent`) targeting Spring Boot 4.0.5 and Java 25
- `backend/comicbook` Maven module (`accm-comicbook`) — Spring Boot 4 REST API scaffold with `AccmApplication` entry point, PostgreSQL driver, Spring Data JPA, and Flyway
- `frontend/` — Angular 21 SPA with SSR, standalone components, strict mode, and SCSS
- `docker-compose.yml` — PostgreSQL 17 local instance with a named volume
- `doc/` directory for Architecture Decision Records and project documentation
- `README.md` project overview and getting-started guide
