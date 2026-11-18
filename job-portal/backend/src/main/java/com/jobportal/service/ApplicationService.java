package com.jobportal.service;

import com.jobportal.dto.Dtos.*;
import com.jobportal.model.*;
import com.jobportal.repository.JobApplicationRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
public class ApplicationService {
    private final JobApplicationRepository applications;
    private final JobService jobService;

    public ApplicationService(JobApplicationRepository applications, JobService jobService) {
        this.applications = applications;
        this.jobService = jobService;
    }

    public ApplicationResponse apply(User candidate, Long jobId, ApplyRequest req) {
        Job job = jobService.find(jobId);
        if (!job.isActive()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This job is no longer accepting applications");
        }
        if (applications.existsByJobIdAndCandidateId(jobId, candidate.getId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "You have already applied to this job");
        }
        JobApplication a = new JobApplication();
        a.setJob(job);
        a.setCandidate(candidate);
        a.setCoverLetter(req.coverLetter());
        a.setResumeUrl(req.resumeUrl());
        JobApplication saved = applications.save(a);
        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<ApplicationResponse> mine(User candidate) {
        return applications.findByCandidateIdOrderByAppliedAtDesc(candidate.getId())
                .stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public List<ApplicationResponse> forJob(User employer, Long jobId) {
        jobService.owned(employer, jobId);
        return applications.findByJobIdOrderByAppliedAtDesc(jobId).stream().map(this::toResponse).toList();
    }

    public ApplicationResponse updateStatus(User employer, Long applicationId, ApplicationStatus status) {
        JobApplication app = applications.findById(applicationId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Application not found"));
        if (!app.getJob().getEmployer().getId().equals(employer.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not your job posting");
        }
        app.setStatus(status);
        return toResponse(app);
    }

    private ApplicationResponse toResponse(JobApplication a) {
        return new ApplicationResponse(a.getId(), a.getJob().getId(), a.getJob().getTitle(), a.getJob().getCompany(),
                a.getCandidate().getName(), a.getCandidate().getEmail(),
                a.getCoverLetter(), a.getResumeUrl(), a.getStatus(), a.getAppliedAt());
    }
}
