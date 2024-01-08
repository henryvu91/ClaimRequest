package com.vn.services.impl;

import com.vn.model.Working;
import com.vn.repositories.WorkingRepository;
import com.vn.services.WorkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkingServiceImpl implements WorkingService {
    @Autowired
    WorkingRepository workingRepository;
    @Override
    public List<Working> findByStaffId(Integer staffId) {
        return workingRepository.findByStaffId(staffId);
    }

    @Override
    public Working findById(Integer workingId) {
        return workingRepository.findById(workingId).orElse(null);
    }

    @Override
    public Boolean checkRecord(Integer staffId) {
       List<Working> workingList =  workingRepository.findByStaffIdAndJobRankId(staffId, 1);
       if(!workingList.isEmpty()){
           return true;
       }
       return false;
    }
}
