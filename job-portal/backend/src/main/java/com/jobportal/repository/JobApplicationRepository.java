package com.jobportal.repository;

import com.jobportal.model.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {
    boolean existsByJobIdAndCandidateId(Long jobId, Long candidateId);
    List<JobApplication> findByCandidateIdOrderByAppliedAtDesc(Long candidateId);
    List<JobApplication> findByJobIdOrderByAppliedAtDesc(Long jobId);
    long countByJobId(Long jobId);
    void deleteByJobId(Long jobId);
}
