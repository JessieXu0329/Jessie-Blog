# Jessie Blog

Personal blog with a cyber aesthetic. Deep navy background, cyan neon glow, digital archive feeling.

**Stack:** Spring Boot 3.3 + Vue 3 + Vite + TailwindCSS + MySQL

[![Tech](https://img.shields.io/badge/Java-21-orange)](https://adoptium.net/)
[![Frontend](https://img.shields.io/badge/Vue-3.4-emerald)](https://vuejs.org/)
[![CSS](https://img.shields.io/badge/Tailwind-3.4-sky)](https://tailwindcss.com/)

---

## Pages

| Route | Section | Description |
|-------|---------|-------------|
| `/about` | ABOUT | Identity, bio, skills, contact |
| `/tech` | TECH | Tech stack, projects, timeline |
| `/essays` | ESSAYS | Markdown articles |
| `/sports` | SPORTS | Physical memory photo archive |
| `/media` | MEDIA | Music shelf + movie vault |
| `/admin` | ADMIN | Dashboard with sidebar (upload, delete, manage) |

## Local Development

### Prerequisites

- Java 21+
- Node.js 18+
- MySQL 8+

### Setup

```bash
# 1. Create database
mysql -u root -p -e "CREATE DATABASE IF NOT EXISTS jessie_blog;"

# 2. Backend
cd jessie-blog
# Edit src/main/resources/application.yml — set your MySQL password & GitHub token
mvn spring-boot:run

# 3. Frontend
cd frontend
npm install
npx vite
```

Open `http://localhost:5173`.

**Admin:** `http://localhost:5173/admin` — password `Jessiexu123456`, access token `jessie-blog-secret-2026`.

## Deployment

### Frontend → Vercel

1. Import `JessieXu0329/Jessie-Blog` on [vercel.com/import](https://vercel.com/import)
2. Build & output pre-configured in `vercel.json`
3. Add env var: `VITE_API_BASE` = your backend URL

### Backend → Railway

1. Import repo on [railway.app](https://railway.app)
2. Add MySQL database service
3. Set env vars: `GITHUB_TOKEN`, `ADMIN_TOKEN`, `SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME`, `SPRING_DATASOURCE_PASSWORD`

### Image Storage

Images are uploaded to `JessieXu0329/picsforJessie-sBlog` via GitHub API and served through jsDelivr CDN:

```
https://cdn.jsdelivr.net/gh/JessieXu0329/picsforJessie-sBlog@main/{section}/{date}/{uuid}.jpg
```

## Project Structure

```
jessie-blog/
├── src/main/java/com/jessieblog/
│   ├── config/          # Security, CORS, token filter
│   ├── controller/      # REST endpoints
│   ├── dto/             # Request / response objects
│   ├── entity/          # JPA entities
│   ├── repository/      # Spring Data repositories
│   └── service/         # Business logic + GitHub upload
├── frontend/
│   ├── src/
│   │   ├── api/         # Axios HTTP layer
│   │   ├── components/  # Vue components (30+)
│   │   ├── composables/ # Auth helpers
│   │   ├── router/      # Vue Router
│   │   └── views/       # Page components
│   └── vite.config.js   # Dev proxy to backend
├── vercel.json
└── pom.xml
```

## License

MIT — built with vibe coding, caffeine, and curiosity.
