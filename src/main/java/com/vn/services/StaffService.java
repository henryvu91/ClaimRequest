package com.vn.services;

import com.vn.dto.form.StaffUpdateInfoDto;
import com.vn.dto.view.StaffIdNameDto;
import com.vn.dto.view.StaffViewDetailDto;
import com.vn.model.Staff;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface StaffService {

    Staff findById(Integer id);

    Staff findByEmail(String email);

    List<Staff> findAll();

    Staff save(Staff staff, BindingResult result);

    Staff update(StaffUpdateInfoDto staff);

    List<StaffIdNameDto> findAllStaffName();

    StaffViewDetailDto findStaffViewDetailById(Integer id);
}
