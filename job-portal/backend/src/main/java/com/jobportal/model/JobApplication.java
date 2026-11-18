package com.jobportal.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "job_applications",
       uniqueConstraints = @UniqueConstraint(columnNames = {"job_id", "candidate_id"}))
public class JobApplication {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Job job;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User candidate;

    @Column(length = 3000, nullable = false)
    private String coverLetter;

    private String resumeUrl;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status = ApplicationStatus.APPLIED;

    private Instant appliedAt = Instant.now();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Job getJob() { return job; }
    public void setJob(Job job) { this.job = job; }

    public User getCandidate() { return candidate; }
    public void setCandidate(User candidate) { this.candidate = candidate; }

    public String getCoverLetter() { return coverLetter; }
    public void setCoverLetter(String coverLetter) { this.coverLetter = coverLetter; }

    public String getResumeUrl() { return resumeUrl; }
    public void setResumeUrl(String resumeUrl) { this.resumeUrl = resumeUrl; }

    public ApplicationStatus getStatus() { return status; }
    public void setStatus(ApplicationStatus status) { this.status = status; }

    public Instant getAppliedAt() { return appliedAt; }
    public void setAppliedAt(Instant appliedAt) { this.appliedAt = appliedAt; }
}
