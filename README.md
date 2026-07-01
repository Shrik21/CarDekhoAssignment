# AI Car Advisor

Production-ready MVP for the CarDekho AI-Native take-home assignment.

AI Car Advisor helps confused car buyers shortlist cars from a seeded database based on budget, fuel preference, usage pattern, passenger count, and priority.

## Tech Stack

- Frontend: React, TypeScript, Vite, Tailwind CSS
- Backend: Java 21, Spring Boot 3, Spring Web, Spring Data JPA
- Database: PostgreSQL
- Infra: Docker Compose

## Run With Docker

```bash
docker compose up --build
```

- Frontend: http://localhost:3000
- Backend: http://localhost:8080
- PostgreSQL: localhost:5432

The backend seeds sample cars on first startup.

## Run Locally

Start PostgreSQL first:

```bash
docker compose up postgres
```

Backend:

```bash
cd backend
mvn spring-boot:run
```

Frontend:

```bash
cd frontend
npm install
npm run dev
```

Frontend runs on http://localhost:5173.

## APIs

### GET `/api/cars`

Returns all seeded cars.

### GET `/api/cars/{id}`

Returns one car by ID.

### POST `/api/recommendations`

Request:

```json
{
  "minBudget": 700000,
  "maxBudget": 1600000,
  "fuelType": "PETROL",
  "usageType": "MIXED",
  "passengers": 5,
  "priority": "SAFETY"
}
```

Response: top 5 cars with car details, score, and buyer-friendly explanation.

## Recommendation Logic

The backend uses a deterministic weighted scoring algorithm:

- Budget match: 30 points
- Fuel match: 20 points
- Passenger fit: 10 points
- Usage fit: 10 points
- User priority: 30 points

This keeps the MVP simple, explainable, and testable. The "AI explanation" is generated from the same scoring context so the reason shown to the user matches the actual ranking behavior.

## Architecture Decisions

- Simple monorepo: `backend/`, `frontend/`, and top-level Docker Compose.
- JPA entity stores structured car attributes plus a feature list using `@ElementCollection`.
- DTOs keep API responses separate from persistence objects.
- `RecommendationService` owns all scoring logic so it is easy to test or replace later with ML/LLM ranking.
- PostgreSQL is the production database; H2 is only used for focused backend tests.
- The frontend uses a single responsive flow: landing, requirements form, recommendations, and comparison.

## Folder Structure

```text
backend/
  src/main/java/com/cardekho/advisor/
    car/
    common/
    config/
    recommendation/
frontend/
  src/
    api.ts
    App.tsx
    types.ts
docker-compose.yml
README.md
```

## Useful Commands

```bash
cd backend && mvn test
cd frontend && npm run build
```

## MVP Scope

The app prioritizes working features over complex personalization. In a next iteration, the recommendation engine could add user location, ownership cost, service network, real inventory, and an LLM-generated explanation layer.
