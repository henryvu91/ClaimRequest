package com.vn.controller;

import com.vn.model.Department;
import com.vn.model.Role;
import com.vn.model.Staff;
import com.vn.repositories.DepartmentRepository;
import com.vn.repositories.RoleRepository;
import com.vn.repositories.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class StaffController {

    @Autowired
    StaffRepository staffRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    RoleRepository roleRepository;

//    Show the UI to create new staff
    @GetMapping("/staff/createStaff")
    public String createStaffUI(ModelMap modelMap){
        Staff staff = new Staff();
        List<Department> departments = departmentRepository.findAll();
        List<Role> roles = roleRepository.findAll();
        modelMap.addAttribute("newStaff",staff);
        modelMap.addAttribute("departments",departments);
        modelMap.addAttribute("roles",roles);
        return "view/staff/createStaff";
    }

    @PostMapping("/staff/createStaff")
    public String createStaff(ModelMap modelMap){

        return "view/staff/createStaff";
    }
}
