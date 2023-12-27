package com.vn.services;

import com.vn.model.JobRank;

import java.util.List;

public interface JobRankService {
    JobRank findById(Integer id);
    List<JobRank> findAll();
}
