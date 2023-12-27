package com.vn.controller;

import com.vn.model.Department;
import com.vn.model.Role;
import com.vn.model.Staff;
import com.vn.services.DepartmentService;
import com.vn.services.RoleService;
import com.vn.services.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class StaffController {

    @Autowired
    StaffService staffService;

    @Autowired
    DepartmentService departmentService;

    @Autowired
    RoleService roleService;

//    Show the UI to create new staff
    @GetMapping("/staff/createStaff")
    public String createStaffUI(ModelMap modelMap){
        Staff staff = new Staff();
        List<Department> departments = departmentService.findAll();
        List<Role> roles = roleService.findAll();
        modelMap.addAttribute("newStaff",staff);
        modelMap.addAttribute("departments",departments);
        modelMap.addAttribute("roles",roles);
        return "view/staff/createStaff";
    }

//    Method to create new staff in database
    @PostMapping("/staff/createStaff")
    public String createStaff(
            @Validated @ModelAttribute(name = "newStaff")Staff staff
            , BindingResult result
            , ModelMap modelMap){

        boolean isError = false;
//        Validate the input
        if(result.hasErrors()){
            isError = true;
            modelMap.addAttribute("newStaff",staff);
            //TODO: Add message error
            return "view/staff/createStaff";
        }

//        Save staff into db
        Staff addedStaff = staffService.save(staff,result);
        if(addedStaff == null){
            isError = true;
            //TODO: Add message error
        }
        if(isError){
            List<Department> departments = departmentService.findAll();
            List<Role> roles = roleService.findAll();
            modelMap.addAttribute("newStaff",staff);
            modelMap.addAttribute("departments",departments);
            modelMap.addAttribute("roles",roles);
            return "view/staff/createStaff";
        }else{
            return "view/staff/viewStaff";
        }
    }
    @GetMapping("/staff/viewStaff")
    public String viewStaffUI(ModelMap modelMap){

        return "view/staff/viewStaff";
    }
}
