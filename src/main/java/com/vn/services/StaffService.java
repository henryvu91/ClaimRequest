package com.vn.services;

import com.vn.dto.form.StaffUpdateInfoDto;
import com.vn.dto.view.StaffIdNameDto;
import com.vn.dto.view.StaffViewDetailDto;
import com.vn.model.Staff;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface StaffService {

    com.vn.model.Staff findById(Integer id);

    com.vn.model.Staff findByEmail(String email);

    List<com.vn.model.Staff> findAll();

    com.vn.model.Staff save(com.vn.model.Staff staff, BindingResult result);

    Staff update(StaffUpdateInfoDto staff);

    List<StaffIdNameDto> findAllStaffName();

    StaffViewDetailDto findStaffViewDetailById(Integer id);
}
