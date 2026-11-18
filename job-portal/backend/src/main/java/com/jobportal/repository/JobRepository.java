package com.jobportal.repository;

import com.jobportal.model.Job;
import com.jobportal.model.JobType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {

    @Query("""
        select j from Job j
        where j.active = true
          and (lower(j.title) like lower(concat('%', :q, '%'))
               or lower(j.company) like lower(concat('%', :q, '%')))
          and lower(j.location) like lower(concat('%', :location, '%'))
          and (:type is null or j.type = :type)
        """)
    Page<Job> search(@Param("q") String q,
                     @Param("location") String location,
                     @Param("type") JobType type,
                     Pageable pageable);

    List<Job> findByEmployerIdOrderByPostedAtDesc(Long employerId);
}
