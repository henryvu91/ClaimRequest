package com.vn.dto;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.vn.model.Role}
 */
@Value
public class RoleDTO implements Serializable {
    Integer id;
    String name;
}