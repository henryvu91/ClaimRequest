package com.vn.services.impl;

import com.vn.model.JobRank;
import com.vn.repositories.JobRankRepository;
import com.vn.services.JobRankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobRankServiceImpl implements JobRankService {

    @Autowired
    JobRankRepository jobRankRepository;
    @Override
    public JobRank findById(Integer id) {
        return jobRankRepository.findById(id).orElse(null);
    }

    @Override
    public List<JobRank> findAll() {
        return jobRankRepository.findAll();
    }
}
