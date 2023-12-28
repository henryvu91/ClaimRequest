package com.vn.dto;

import lombok.Getter;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.vn.model.Staff}
 */
@Value
@Getter
public class StaffIdNameDto2 implements Serializable {
    Integer id;
    String name;
}