package com.vn.dto;

import lombok.Value;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link com.vn.model.Department}
 */
@Value
public class DepartmentDTO implements Serializable {
    Integer id;
    String name;
    List<StaffDTO> staffById;
}