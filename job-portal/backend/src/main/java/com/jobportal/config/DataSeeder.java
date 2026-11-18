package com.jobportal.config;

import com.jobportal.model.*;
import com.jobportal.repository.JobRepository;
import com.jobportal.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

/** Seeds demo accounts and jobs on first run. Password for both: password123 */
@Component
public class DataSeeder implements CommandLineRunner {
    private final UserRepository users;
    private final JobRepository jobs;
    private final PasswordEncoder encoder;

    public DataSeeder(UserRepository users, JobRepository jobs, PasswordEncoder encoder) {
        this.users = users;
        this.jobs = jobs;
        this.encoder = encoder;
    }

    @Override
    public void run(String... args) {
        if (users.count() > 0) return;

        User employer = users.save(newUser("Priya Nair", "employer@demo.com", Role.EMPLOYER, "Harbor Labs"));
        users.save(newUser("Sam Carter", "candidate@demo.com", Role.CANDIDATE, null));

        seed(employer, "Senior Java Engineer", "Harbor Labs", "Bengaluru, India", JobType.FULL_TIME,
             "Build and scale the payments APIs used by thousands of merchants. You will own services end to end, from design review to on-call.",
             "5+ years with Java and Spring Boot\nSolid SQL and schema design\nExperience running services in production", 3_000_000, 4_500_000, 1);
        seed(employer, "Frontend Developer (React)", "Harbor Labs", "Remote", JobType.REMOTE,
             "Create fast, accessible interfaces for our merchant dashboard. Work closely with design and the API team.",
             "3+ years with React\nComfortable with accessibility and performance work\nGood written communication", 2_000_000, 3_200_000, 2);
        seed(employer, "Product Design Intern", "Harbor Labs", "Mumbai, India", JobType.INTERNSHIP,
             "Join the design team for six months. You will ship real features, from research through to final UI.",
             "Portfolio with 2+ projects\nFigma proficiency", 25_000, 40_000, 4);
        seed(employer, "QA Automation Contractor", "Harbor Labs", "Pune, India", JobType.CONTRACT,
             "Six-month contract to expand our end-to-end test coverage across web and API layers.",
             "Experience with Playwright or Cypress\nCI pipeline experience", 1_200_000, 1_800_000, 6);
    }

    private void seed(User e, String title, String company, String loc, JobType type,
                      String desc, String req, int min, int max, int daysAgo) {
        Job j = new Job();
        j.setTitle(title);
        j.setCompany(company);
        j.setLocation(loc);
        j.setType(type);
        j.setDescription(desc);
        j.setRequirements(req);
        j.setSalaryMin(min);
        j.setSalaryMax(max);
        j.setPostedAt(Instant.now().minus(daysAgo, ChronoUnit.DAYS));
        j.setEmployer(e);
        jobs.save(j);
    }

    private User newUser(String name, String email, Role role, String company) {
        User u = new User();
        u.setName(name);
        u.setEmail(email);
        u.setPassword(encoder.encode("password123"));
        u.setRole(role);
        u.setCompany(company);
        return u;
    }
}
