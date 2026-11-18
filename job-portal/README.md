# Job Portal System

A full-stack web application developed using **Java, Spring Boot, React.js, and MySQL**. The Job Portal System connects recruiters and job seekers through a platform for publishing job opportunities, searching for jobs, and managing applications.

## 🚀 Key Features

### Authentication and Security
- User registration and login
- JWT-based authentication
- Spring Security integration
- Role-based access control
- Three user roles: Admin, Recruiter, and Candidate

### Recruiter
- Create and publish job postings
- Manage job listings
- View candidate applications

### Candidate
- Browse available jobs
- Search and filter job opportunities
- Apply for jobs
- Track application status
- Resume upload support

### Admin
- Administrative access based on user roles
- Platform management

## 🛠️ Technology Stack

| Technology | Purpose |
|---|---|
| Java | Backend programming |
| Spring Boot | Backend framework |
| Spring Security | Application security |
| JWT | Authentication |
| React.js | Frontend development |
| MySQL | Database |
| Maven | Build and dependency management |
| HTML, CSS, JavaScript | Frontend technologies |

## 📂 Project Structure

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
│   └── pom.xml
│
├── frontend/
│   ├── src/
│   ├── public/
│   └── package.json
│
├── .gitignore
└── README.md
```

## ⚙️ Installation and Setup

### 1. Clone the Repository

```bash
git clone https://github.com/moremayurbhoi-eng/Job-Portal-System.git
```

### 2. Navigate to the Project

```bash
cd Job-Portal-System
```

### 3. Configure MySQL Database

Create a database:

```sql
CREATE DATABASE job_portal;
```

Configure your database connection in:

`backend/src/main/resources/application.properties`

Example:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/job_portal
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

server.port=8080
```

Use your own database credentials. Do not upload passwords or secret keys to GitHub.

### 4. Run the Spring Boot Backend

Open a terminal:

```bash
cd backend
```

Run:

```bash
mvn spring-boot:run
```

Backend URL:

```text
http://localhost:8080
```

### 5. Run the React Frontend

Open another terminal:

```bash
cd frontend
```

Install dependencies:

```bash
npm install
```

Start the development server:

```bash
npm run dev
```

Open the local URL displayed in your terminal.

## 🔐 Security

- JWT token-based authentication
- Spring Security configuration
- Role-based authorization
- Protected API endpoints

## 🎯 Project Objective

The main objective of this project is to build a job recruitment platform that simplifies the hiring process.

Recruiters can publish job opportunities, while candidates can search for suitable positions and submit applications.

This project demonstrates practical knowledge of full-stack web development, REST APIs, database integration, authentication, and authorization.

## 👨‍💻 Developer

**Mayur More**

- GitHub: [moremayurbhoi-eng](https://github.com/moremayurbhoi-eng)
- Project Repository: [Job Portal System](https://github.com/moremayurbhoi-eng/Job-Portal-System)

---

If you find this project useful, consider giving it a ⭐ on GitHub.