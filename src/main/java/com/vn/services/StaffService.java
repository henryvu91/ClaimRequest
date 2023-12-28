package com.vn.services;

import com.vn.dto.StaffIdNameDto;
import com.vn.dto.StaffIdNameDto2;
import com.vn.dto.StaffViewDetailDto;
import com.vn.model.Staff;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface StaffService {

    Staff findById(Integer id);

    Staff findByEmail(String email);

    List<Staff> findAll();

    Staff save(Staff staff, BindingResult result);

    Staff update(Staff staff);

    List<StaffIdNameDto2> findAllStaffName();

    StaffViewDetailDto findStaffViewDetailById(Integer id);
}
