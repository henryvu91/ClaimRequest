package com.vn.services.impl;

import com.vn.model.Department;
import com.vn.repositories.DepartmentRepository;
import com.vn.services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    DepartmentRepository departmentRepository;
    @Override
    public Department findById(Integer id) {
        return departmentRepository.findById(id).orElse(null);
    }

    @Override
    public List<Department> findAll() {
        return departmentRepository.findAll();
    }
}
