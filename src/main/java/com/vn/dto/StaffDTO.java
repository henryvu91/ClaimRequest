package com.vn.dto;

import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * DTO for {@link com.vn.model.Staff}
 */
@Value
public class StaffDTO implements Serializable {
    Integer id;
    Integer departmentId;
    Integer roleId;
    String email;
    String password;
    String name;
    BigDecimal salary;
    DepartmentDTO departmentByDepartmentId;
    RoleDTO roleByRoleId;
    List<WorkingDTO> workingsById;
}