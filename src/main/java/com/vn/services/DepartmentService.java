package com.vn.services;

import com.vn.model.Department;

import java.util.List;

public interface DepartmentService {

    Department findById(Integer id);
    List<Department> findAll();
}
