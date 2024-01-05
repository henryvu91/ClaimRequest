package com.vn.services;

import com.vn.model.Working;

import java.util.List;
import java.util.Optional;

public interface WorkingService {
    List<Working> findByStaffId(Integer staffId);

    Working findById(Integer workingId);
}
