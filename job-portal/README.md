# Job Portal System

A full-stack Job Portal Web Application built using **Java, Spring Boot, React.js, and MySQL**. The platform connects recruiters with candidates and provides job posting, job searching, and application management functionality.

## 🚀 Features

### Authentication & Authorization
- User registration and login
- JWT-based authentication
- Role-based access control
- Three user roles: Admin, Recruiter, and Candidate

### Recruiter
- Post new job opportunities
- Manage job listings
- View candidate applications

### Candidate
- Browse available jobs
- Search and explore job opportunities
- Apply for jobs
- Track application status
- Resume upload support

### Admin
- Role-based administrative access
- Manage platform activities

## 🛠️ Technology Stack

| Technology | Purpose |
|---|---|
| Java | Backend programming |
| Spring Boot | Backend framework |
| Spring Security | Authentication and authorization |
| JWT | Token-based authentication |
| React.js | Frontend development |
| MySQL | Database |
| Maven | Dependency management |
| HTML, CSS, JavaScript | Web development |

## 📁 Project Structure

```text
Job-Portal-System/
│
├── backend/
│   ├── src/
│   │   └── main/
│   │       ├── java/com/jobportal/
│   │       │   ├── config/
│   │       │   ├── controller/
│   │       │   ├── dto/
│   │       │   ├── model/
│   │       │   ├── repository/
│   │       │   ├── security/
│   │       │   └── service/
│   │       └── resources/
│   │           └── application.properties
│   └── pom.xml
│
├── frontend/
│   ├── src/
│   ├── public/
│   └── package.json
│
└── README.md
```

## ⚙️ Installation and Setup

### 1. Clone the Repository

```bash