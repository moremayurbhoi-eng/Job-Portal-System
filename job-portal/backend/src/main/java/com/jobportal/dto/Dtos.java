package com.jobportal.dto;

import com.jobportal.model.*;
import jakarta.validation.constraints.*;
import java.time.Instant;
import java.util.List;

public final class Dtos {
    private Dtos() {}

    public record RegisterRequest(
        @NotBlank String name,
        @NotBlank @Email String email,
        @NotBlank @Size(min = 6, message = "Password must be at least 6 characters") String password,
        @NotNull Role role,
        String company) {}

    public record LoginRequest(@NotBlank String email, @NotBlank String password) {}

    public record UserDto(Long id, String name, String email, Role role, String company) {}

    public record AuthResponse(String token, UserDto user) {}

    public record JobRequest(
        @NotBlank String title,
        @NotBlank String company,
        @NotBlank String location,
        @NotNull JobType type,
        @NotBlank @Size(max = 5000) String description,
        @Size(max = 3000) String requirements,
        @PositiveOrZero Integer salaryMin,
        @PositiveOrZero Integer salaryMax,
        Boolean active) {}

    public record JobResponse(
        Long id, String title, String company, String location, JobType type,
        String description, String requirements, Integer salaryMin, Integer salaryMax,
        boolean active, Instant postedAt, Long employerId, Long applicantCount) {}

    public record ApplyRequest(@NotBlank @Size(max = 3000) String coverLetter, String resumeUrl) {}

    public record StatusRequest(@NotNull ApplicationStatus status) {}

    public record ApplicationResponse(
        Long id, Long jobId, String jobTitle, String company,
        String candidateName, String candidateEmail,
        String coverLetter, String resumeUrl,
        ApplicationStatus status, Instant appliedAt) {}

    public record PageResponse<T>(List<T> content, int page, int totalPages, long totalElements) {}
}
