package com.vn.services.impl;

import com.vn.model.Staff;
import com.vn.repositories.StaffRepository;
import com.vn.services.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StaffServiceImpl implements StaffService {
    @Autowired
    StaffRepository staffRepository;
    @Override
    public Staff findById(Integer id) {
        return staffRepository.findById(id).orElse(null);
    }

    @Override
    public Staff findByEmail(String email) {
        return staffRepository.findByEmail(email);
    }

    @Override
    public List<Staff> findAll() {
        return staffRepository.findAll();
    }


}
