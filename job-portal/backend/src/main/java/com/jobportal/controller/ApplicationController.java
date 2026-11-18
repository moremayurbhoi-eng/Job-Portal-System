package com.jobportal.controller;

import com.jobportal.dto.Dtos.*;
import com.jobportal.model.User;
import com.jobportal.service.ApplicationService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {
    private final ApplicationService applications;

    public ApplicationController(ApplicationService applications) {
        this.applications = applications;
    }

    @GetMapping("/mine")
    @PreAuthorize("hasRole('CANDIDATE')")
    public List<ApplicationResponse> mine(@AuthenticationPrincipal User user) {
        return applications.mine(user);
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('EMPLOYER')")
    public ApplicationResponse updateStatus(@AuthenticationPrincipal User user, @PathVariable Long id,
                                            @Valid @RequestBody StatusRequest req) {
        return applications.updateStatus(user, id, req.status());
    }
}
