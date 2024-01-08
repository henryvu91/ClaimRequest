package com.vn.services;

import com.vn.model.Working;

import java.util.List;

public interface WorkingService {
    List<Working> findByStaffId(Integer staffId);

    Working findById(Integer workingId);

    Boolean checkRecord(Integer staffId);
}
