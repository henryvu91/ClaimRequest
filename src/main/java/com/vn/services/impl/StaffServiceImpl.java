package com.vn.services.impl;

import com.vn.dto.form.StaffUpdateInfoDto;
import com.vn.dto.view.StaffIdNameDto;
import com.vn.dto.view.StaffViewDetailDto;
import com.vn.mapper.form.StaffUpdateInfoMapper;
import com.vn.model.Staff;
import com.vn.repositories.DepartmentRepository;
import com.vn.repositories.RoleRepository;
import com.vn.repositories.StaffRepository;
import com.vn.services.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;

@Service
public class StaffServiceImpl implements StaffService {
    @Autowired
    StaffRepository staffRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    StaffUpdateInfoMapper staffUpdateInfoMapper;
    @Override
    public com.vn.model.Staff findById(Integer id) {
        return staffRepository.findById(id).orElse(null);
    }

    @Override
    public com.vn.model.Staff findByEmail(String email) {
        return staffRepository.findByEmail(email);
    }

    @Override
    public List<com.vn.model.Staff> findAll() {
        return staffRepository.findAll();
    }

    @Override
    public com.vn.model.Staff save(com.vn.model.Staff staff, BindingResult result) {

//        Check email not duplicate
        String email = staff.getEmail();
        if(staffRepository.existsByEmail(email)){
//            Add the error message
            result.rejectValue("email","MSG21");
            return null;
        }
// Encode the password
        String encodePassword = passwordEncoder.encode(staff.getPassword());
        staff.setPassword(encodePassword);
//        Add new staff
        return staffRepository.save(staff);
    }

    @Override
    public Staff update(StaffUpdateInfoDto staff) {
//        Get the staff from database
        com.vn.model.Staff updateStaff = staffRepository.findById(staff.getId()).orElse(null);
        if(updateStaff==null){
            return null;
        }

        staffUpdateInfoMapper.partialUpdate(staff,updateStaff);

        return staffRepository.save(updateStaff);
    }

    @Override
    public List<StaffIdNameDto> findAllStaffName() {
        return staffRepository.findAllStaffName();
    }

    @Override
    public StaffViewDetailDto findStaffViewDetailById(Integer id) {
        return staffRepository.findStaffViewDetailById(id);
    }
}
