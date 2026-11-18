# Harbor Jobs: full stack job portal

React (Vite) + Spring Boot 3 (Java 17) + Spring Security (JWT) + JPA.

## Features
- Candidates: search and filter jobs (title/company, location, type), apply with a cover letter and resume link, track application status
- Employers: post, edit, close and delete jobs; review applicants and move them through APPLIED, REVIEWING, SHORTLISTED, REJECTED, HIRED
- Stateless JWT auth, BCrypt passwords, role-based access (CANDIDATE / EMPLOYER), ownership checks on every employer action
- Seeded demo data on first run

## Run it
Backend (needs Java 17 and Maven):
    cd backend
    mvn spring-boot:run          # http://localhost:8080

Frontend (needs Node 18+):
    cd frontend
    npm install
    npm run dev                  # http://localhost:5173 (proxies /api to :8080)

Demo logins (password `password123`): employer@demo.com, candidate@demo.com

## Use MySQL instead of H2
Create a database `jobportal`, then override in application.properties (or env vars):
    spring.datasource.url=jdbc:mysql://localhost:3306/jobportal
    spring.datasource.username=root
    spring.datasource.password=yourpassword
Set a real `JWT_SECRET` (32+ characters) in production.

## API
| Method | Path | Access |
|---|---|---|
| POST | /api/auth/register, /api/auth/login | public |
| GET | /api/auth/me | signed in |
| GET | /api/jobs?q=&location=&type=&page=&size= | public |
| GET | /api/jobs/{id} | public |
| GET | /api/jobs/mine | employer |
| POST / PUT / DELETE | /api/jobs, /api/jobs/{id} | employer (owner) |
| POST | /api/jobs/{id}/apply | candidate |
| GET | /api/jobs/{id}/applications | employer (owner) |
| GET | /api/applications/mine | candidate |
| PATCH | /api/applications/{id}/status | employer (owner) |
