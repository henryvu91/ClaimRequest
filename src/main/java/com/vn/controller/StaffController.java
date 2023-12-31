package com.vn.controller;

import com.vn.dto.form.StaffUpdateInfoDto;
import com.vn.dto.view.StaffIdNameDto;
import com.vn.dto.view.StaffViewDetailDto;
import com.vn.mapper.form.StaffUpdateInfoMapper;
import com.vn.mapper.view.StaffIdNameMapper;
import com.vn.mapper.view.StaffViewMapper;
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
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class StaffController {

    @Autowired
    StaffService staffService;

    @Autowired
    DepartmentService departmentService;

    @Autowired
    RoleService roleService;

    //    @Autowired
    StaffIdNameMapper staffIdNameMapper;

    //    @Autowired
    StaffViewMapper staffViewMapper;

    //    @Autowired
    StaffUpdateInfoMapper staffUpdateInfoMapper;

    //    Show the UI to create new staff
    @GetMapping("/staff/create")
    public String createStaffUI(ModelMap modelMap) {
        com.vn.model.Staff staff = new com.vn.model.Staff();
        List<Department> departments = departmentService.findAll();
        List<Role> roles = roleService.findAll();
        modelMap.addAttribute("newStaff", staff);
        modelMap.addAttribute("departments", departments);
        modelMap.addAttribute("roles", roles);
        return "/view/staff/create";
    }

    //    Method to create new staff in database
    @PostMapping("/staff/create")
    public String createStaff(
            @Validated @ModelAttribute(name = "newStaff") com.vn.model.Staff staff
            , BindingResult result
            , ModelMap modelMap) {

        boolean isError = false;
//        Validate the input
        if (result.hasErrors()) {
            isError = true;
            //TODO: Add message error
        } else {

//        Save staff into db
            com.vn.model.Staff addedStaff = staffService.save(staff, result);
            if (addedStaff == null) {
                isError = true;
                //TODO: Add message error
            }
        }

        if (isError) {
            List<Department> departments = departmentService.findAll();
            List<Role> roles = roleService.findAll();
            modelMap.addAttribute("newStaff", staff);
            modelMap.addAttribute("departments", departments);
            modelMap.addAttribute("roles", roles);
            return "view/staff/create";
        } else {
            return "redirect:/staff/view";
        }
    }

    @GetMapping("/staff/view")
    public String viewStaffUI(
            @RequestParam(name = "id", required = false) Integer id
            , ModelMap modelMap) {
//       List <StaffIdNameDto> nameList = staffService.findAll().stream().map(m->staffMapper.toDto(m)).toList();
        List<StaffIdNameDto> nameList = staffService.findAllStaffName();

//        check the list is empty
        if (!nameList.isEmpty()) {
            modelMap.addAttribute("nameList", nameList);
//            Check the id from param not null
            if (id == null) {
                id = nameList.get(0).getId();
            }
//            get staff information
//            StaffViewDetailDto viewStaff = staffViewMapper.toDto(staffService.findById(id));
            StaffViewDetailDto viewStaff = staffService.findStaffViewDetailById(id);
            if (viewStaff == null) {
//                TODO: error message
            } else {
                modelMap.addAttribute("viewStaff", viewStaff);
            }
        }
        return "view/staff/view";
    }

    //    Show the UI to create new staff
    @GetMapping("/staff/update")
    public String updateStaffUI(
            @RequestParam("id") Integer id,
            ModelMap modelMap) {

//        Staff staff = staffService.findById(id);
        StaffUpdateInfoDto staff = staffUpdateInfoMapper.toDto(staffService.findById(id));
        List<Department> departments = departmentService.findAll();
        List<Role> roles = roleService.findAll();
        modelMap.addAttribute("updateStaff", staff);
        modelMap.addAttribute("departments", departments);
        modelMap.addAttribute("roles", roles);
        return "/view/staff/update";
    }

    //    Method to create new staff in database
    @PostMapping("/staff/update")
    public String updateStaff(
            @Validated @ModelAttribute(name = "updateStaff") StaffUpdateInfoDto staff
            , BindingResult result
            , ModelMap modelMap) {

        boolean isError = false;
//        Validate the input
        if (result.hasErrors()) {
            isError = true;
            //TODO: Add message error
        } else {

//        Save staff into db
            Staff addedStaff = staffService.update(staff);
            if (addedStaff == null) {
                isError = true;
                //TODO: Add message error
            }
        }

        if (isError) {
            List<Department> departments = departmentService.findAll();
            List<Role> roles = roleService.findAll();
            modelMap.addAttribute("newStaff", staff);
            modelMap.addAttribute("departments", departments);
            modelMap.addAttribute("roles", roles);
            return "/view/staff/update";
        } else {
            return "redirect:/staff/view";
        }
    }
}
