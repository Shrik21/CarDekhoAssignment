# AI Car Advisor

Production-ready MVP for the CarDekho AI-Native take-home assignment.

AI Car Advisor helps confused car buyers shortlist cars from a seeded database based on budget, fuel preference, usage pattern, passenger count, and priority.

## Live URLs

- Frontend: https://car-dekho-assignment-rho.vercel.app
- Backend: https://ai-car-advisor-api.onrender.com
- Cars API: https://ai-car-advisor-api.onrender.com/api/cars

## What I Built And Why

I built a focused recommendation flow for buyers who know their needs but do not know which cars to shortlist. The user enters budget, fuel preference, usage type, passenger count, and priority. The backend ranks cars with an explainable score and returns the top 5 with buyer-friendly reasons.

I chose this scope because it directly addresses the brief: moving a confused buyer from "too many options" to a confident shortlist. I prioritized an end-to-end working product over a larger but unfinished feature set.

## Tech Stack

- Frontend: React, TypeScript, Vite, Tailwind CSS
- Backend: Java 21, Spring Boot 3, Spring Web, Spring Data JPA
- Database: PostgreSQL
- Infra: Docker Compose

I picked React + TypeScript for a quick, reliable interactive UI and Spring Boot + JPA because the assignment asks for a real backend with persistence and non-trivial computation. PostgreSQL keeps the database path production-like while Docker Compose and env vars make local/cloud setup simple.

## Run With Docker

```bash
docker compose up --build
```

- Frontend: http://localhost:3000
- Backend: http://localhost:8080
- PostgreSQL: localhost:5432

The backend seeds sample cars on first startup.

## Free Deployment

Recommended free MVP setup:

- Database: Neon Postgres free tier
- Backend: Render free web service
- Frontend: Vercel Hobby

### 1. Push Code To GitHub

This repo is ready to deploy from GitHub. Push the latest commits:

```bash
git push origin main
```

### 2. Create Neon Postgres

1. Go to https://neon.com and create a free project.
2. Copy the connection details.
3. Use this JDBC format for the backend:

```text
jdbc:postgresql://HOST/DATABASE?sslmode=require
```

Set these values later on Render:

```text
SPRING_DATASOURCE_URL=jdbc:postgresql://HOST/DATABASE?sslmode=require
SPRING_DATASOURCE_USERNAME=USER
SPRING_DATASOURCE_PASSWORD=PASSWORD
```

### 3. Deploy Backend On Render

1. Go to https://render.com.
2. Create a new Web Service from this GitHub repo.
3. Select the `ai-car-advisor-api` Blueprint if Render detects `render.yaml`, or use:
   - Runtime: Docker
   - Docker context: `./backend`
   - Dockerfile path: `./backend/Dockerfile`
4. Add environment variables:

```text
SPRING_DATASOURCE_URL=jdbc:postgresql://HOST/DATABASE?sslmode=require
SPRING_DATASOURCE_USERNAME=USER
SPRING_DATASOURCE_PASSWORD=PASSWORD
CORS_ALLOWED_ORIGINS=https://your-vercel-app.vercel.app
```

After deploy, test:

```text
https://your-render-service.onrender.com/api/cars
```

### 4. Deploy Frontend On Vercel

1. Go to https://vercel.com.
2. Import this GitHub repo.
3. Set Root Directory to `frontend`.
4. Set build settings:
   - Build command: `npm run build`
   - Output directory: `dist`
5. Add environment variable:

```text
VITE_API_BASE_URL=https://your-render-service.onrender.com
```

Redeploy the frontend after setting the backend URL.

### 5. Update CORS

After Vercel gives the final frontend URL, update Render:

```text
CORS_ALLOWED_ORIGINS=https://your-vercel-app.vercel.app
```

Then redeploy the backend.

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

The app prioritizes working features over complex personalization.

Deliberately cut from this 2-3 hour MVP:

- Real inventory integrations
- User login and saved shortlists
- Advanced LLM-based reasoning
- Ownership cost, service network, and resale value
- Admin tooling for managing car data

## AI Tool Usage

I used AI assistance for fast scaffolding, implementation planning, UI iteration, Docker/deployment setup, and debugging. The most useful parts were generating the Spring Boot/React structure quickly, reviewing API boundaries, and catching deployment issues like CORS and Render port configuration.

I manually reviewed the generated code, ran tests/builds, verified API responses, fixed JPA lazy-loading behavior, and made product scoping decisions. The tool got in the way around environment-specific issues such as local PostgreSQL credentials and deployment secrets, which required manual judgment and safe handling.

## If I Had Another 4 Hours

I would add:

- A richer dataset with ownership cost and body type
- Better recommendation explanations with pros/cons per car
- Filters for transmission, body style, and brand preference
- A persistent comparison shortlist
- Basic analytics for popular buyer intents
- More API tests around scoring edge cases
