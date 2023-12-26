package com.vn.repositories;

import com.vn.model.JobRank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface JobRankRepository extends JpaRepository<JobRank, Integer> {
}
