package com.vn.services;

import com.vn.dto.view.JobRankDTO;
import com.vn.model.JobRank;

import java.util.List;

public interface JobRankService {
    JobRank findById(Integer id);

    List<JobRankDTO> findAll();
}
