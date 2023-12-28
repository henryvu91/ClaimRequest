package com.vn.dto;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.vn.model.Staff}
 */
@Value
public class StaffIdNameDto implements Serializable {
    Integer id;
    String name;
}