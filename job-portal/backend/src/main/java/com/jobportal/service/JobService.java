package com.jobportal.service;

import com.jobportal.dto.Dtos.*;
import com.jobportal.model.*;
import com.jobportal.repository.JobApplicationRepository;
import com.jobportal.repository.JobRepository;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
public class JobService {
    private final JobRepository jobs;
    private final JobApplicationRepository applications;

    public JobService(JobRepository jobs, JobApplicationRepository applications) {
        this.jobs = jobs;
        this.applications = applications;
    }

    @Transactional(readOnly = true)
    public PageResponse<JobResponse> search(String q, String location, JobType type, int page, int size) {
        Pageable pageable = PageRequest.of(Math.max(page, 0), Math.min(Math.max(size, 1), 50),
                Sort.by(Sort.Direction.DESC, "postedAt"));
        Page<Job> result = jobs.search(q == null ? "" : q.trim(), location == null ? "" : location.trim(), type, pageable);
        return new PageResponse<>(result.map(j -> toResponse(j, null)).getContent(),
                result.getNumber(), result.getTotalPages(), result.getTotalElements());
    }

    @Transactional(readOnly = true)
    public JobResponse get(Long id) {
        return toResponse(find(id), null);
    }

    @Transactional(readOnly = true)
    public List<JobResponse> mine(User employer) {
        return jobs.findByEmployerIdOrderByPostedAtDesc(employer.getId()).stream()
                .map(j -> toResponse(j, applications.countByJobId(j.getId()))).toList();
    }

    public JobResponse create(User employer, JobRequest r) {
        Job job = new Job();
        job.setEmployer(employer);
        apply(job, r);
        return toResponse(jobs.save(job), 0L);
    }

    public JobResponse update(User employer, Long id, JobRequest r) {
        Job job = owned(employer, id);
        apply(job, r);
        return toResponse(job, applications.countByJobId(id));
    }

    public void delete(User employer, Long id) {
        Job job = owned(employer, id);
        applications.deleteByJobId(id);
        jobs.delete(job);
    }

    // ---- helpers ----
    Job find(Long id) {
        return jobs.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Job not found"));
    }

    Job owned(User employer, Long id) {
        Job job = find(id);
        if (!job.getEmployer().getId().equals(employer.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can only manage your own jobs");
        }
        return job;
    }

    private void apply(Job job, JobRequest r) {
        if (r.salaryMin() != null && r.salaryMax() != null && r.salaryMin() > r.salaryMax()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Minimum salary cannot exceed maximum salary");
        }
        job.setTitle(r.title().trim());
        job.setCompany(r.company().trim());
        job.setLocation(r.location().trim());
        job.setType(r.type());
        job.setDescription(r.description());
        job.setRequirements(r.requirements());
        job.setSalaryMin(r.salaryMin());
        job.setSalaryMax(r.salaryMax());
        if (r.active() != null) job.setActive(r.active());
    }

    static JobResponse toResponse(Job j, Long applicantCount) {
        return new JobResponse(j.getId(), j.getTitle(), j.getCompany(), j.getLocation(), j.getType(),
                j.getDescription(), j.getRequirements(), j.getSalaryMin(), j.getSalaryMax(),
                j.isActive(), j.getPostedAt(), j.getEmployer().getId(), applicantCount);
    }
}
