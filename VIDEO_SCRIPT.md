# AI Car Advisor Demo Video Script

Target length: 2-3 minutes.

## Before Recording

Open these tabs/windows:

- Live frontend URL
- Backend health URL: `https://ai-car-advisor-api.onrender.com`
- Backend cars API: `https://ai-car-advisor-api.onrender.com/api/cars`
- GitHub repo
- README
- Code editor with `RecommendationService.java` and `DataSeeder.java`

## Recording Flow

### 0:00-0:20 - Intro

"Hi, this is my CarDekho AI-Native take-home submission. I built AI Car Advisor, a full-stack MVP that helps confused car buyers go from unclear requirements to a ranked shortlist of cars."

Show:

- Live frontend
- App name and landing section

### 0:20-0:55 - User Journey

"The core product decision was to keep the flow simple. Instead of asking users to browse a large catalog, I ask for buying intent: budget range, fuel type, city or highway usage, passenger count, and the main priority."

Show:

- Fill/change budget
- Select fuel type
- Select usage
- Select passengers
- Select priority

### 0:55-1:30 - Recommendations

"When I submit the form, the React app calls the Spring Boot API. The backend scores cars by budget fit, fuel match, passenger fit, usage fit, and the user's selected priority. It returns the top 5 cars with a match score and a buyer-friendly explanation."

Show:

- Click recommendation button
- Point to top 5 cards
- Mention one recommendation explanation
- Show scores and feature tags

### 1:30-1:50 - Comparison

"I also added a comparison section for the top shortlisted cars. This helps the buyer compare price, fuel type, mileage, safety, seating, and key features without jumping between pages."

Show:

- Comparison table
- Highlight 2-3 metrics

### 1:50-2:20 - Backend And Deployment

"The backend is deployed separately on Render and connected to Neon Postgres. This is not a static frontend; the API persists seeded car data using JPA and computes recommendations server-side."

Show:

- `https://ai-car-advisor-api.onrender.com`
- `/api/cars`
- Optional: Render dashboard status

### 2:20-2:50 - Code And AI Usage

"The scoring logic lives in `RecommendationService`, while sample data is seeded in `DataSeeder`. I used AI assistance for scaffolding, UI iteration, Docker and deployment setup, and debugging. I manually reviewed the code, verified builds and tests, fixed a JPA lazy-loading issue, and handled deployment configuration like CORS and secrets."

Show:

- `RecommendationService.java`
- `DataSeeder.java`
- README AI Tool Usage section

### 2:50-3:05 - Close

"For the MVP I deliberately cut login, real inventory, ownership cost, and LLM-based reasoning so I could ship a working end-to-end product within the timebox. With another few hours I would add richer car data, saved shortlists, and deeper ownership-cost recommendations."

Show:

- README MVP Scope section
- End on live app

## Submission Checklist

- GitHub repo link
- Live frontend URL
- Backend URL
- Screen recording link
- README included
