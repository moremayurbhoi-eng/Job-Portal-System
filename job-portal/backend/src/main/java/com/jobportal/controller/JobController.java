package com.jobportal.controller;

import com.jobportal.dto.Dtos.*;
import com.jobportal.model.JobType;
import com.jobportal.model.User;
import com.jobportal.service.ApplicationService;
import com.jobportal.service.JobService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
public class JobController {
    private final JobService jobs;
    private final ApplicationService applications;

    public JobController(JobService jobs, ApplicationService applications) {
        this.jobs = jobs;
        this.applications = applications;
    }

    @GetMapping
    public PageResponse<JobResponse> search(@RequestParam(defaultValue = "") String q,
                                            @RequestParam(defaultValue = "") String location,
                                            @RequestParam(required = false) JobType type,
                                            @RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "10") int size) {
        return jobs.search(q, location, type, page, size);
    }

    @GetMapping("/{id}")
    public JobResponse get(@PathVariable Long id) {
        return jobs.get(id);
    }

    @GetMapping("/mine")
    @PreAuthorize("hasRole('EMPLOYER')")
    public List<JobResponse> mine(@AuthenticationPrincipal User user) {
        return jobs.mine(user);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('EMPLOYER')")
    public JobResponse create(@AuthenticationPrincipal User user, @Valid @RequestBody JobRequest req) {
        return jobs.create(user, req);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('EMPLOYER')")
    public JobResponse update(@AuthenticationPrincipal User user, @PathVariable Long id,
                              @Valid @RequestBody JobRequest req) {
        return jobs.update(user, id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('EMPLOYER')")
    public void delete(@AuthenticationPrincipal User user, @PathVariable Long id) {
        jobs.delete(user, id);
    }

    @PostMapping("/{id}/apply")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('CANDIDATE')")
    public ApplicationResponse apply(@AuthenticationPrincipal User user, @PathVariable Long id,
                                     @Valid @RequestBody ApplyRequest req) {
        return applications.apply(user, id, req);
    }

    @GetMapping("/{id}/applications")
    @PreAuthorize("hasRole('EMPLOYER')")
    public List<ApplicationResponse> applicants(@AuthenticationPrincipal User user, @PathVariable Long id) {
        return applications.forJob(user, id);
    }
}
