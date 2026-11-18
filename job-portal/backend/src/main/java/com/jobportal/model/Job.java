package com.jobportal.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "jobs")
public class Job {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false) private String title;
    @Column(nullable = false) private String company;
    @Column(nullable = false) private String location;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private JobType type;

    @Column(length = 5000, nullable = false) private String description;
    @Column(length = 3000) private String requirements;

    private Integer salaryMin;
    private Integer salaryMax;

    private boolean active = true;

    private Instant postedAt = Instant.now();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User employer;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getCompany() { return company; }
    public void setCompany(String company) { this.company = company; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public JobType getType() { return type; }
    public void setType(JobType type) { this.type = type; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getRequirements() { return requirements; }
    public void setRequirements(String requirements) { this.requirements = requirements; }

    public Integer getSalaryMin() { return salaryMin; }
    public void setSalaryMin(Integer salaryMin) { this.salaryMin = salaryMin; }

    public Integer getSalaryMax() { return salaryMax; }
    public void setSalaryMax(Integer salaryMax) { this.salaryMax = salaryMax; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    public Instant getPostedAt() { return postedAt; }
    public void setPostedAt(Instant postedAt) { this.postedAt = postedAt; }

    public User getEmployer() { return employer; }
    public void setEmployer(User employer) { this.employer = employer; }
}
