package com.vn.dto.view;

import com.vn.utils.validateGroup.ValidateCreateStaffGroup;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Setter;
import lombok.Value;
import org.hibernate.validator.constraints.Range;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link com.vn.model.Staff}
 */
@Value
@Setter
public class StaffViewDetailDto implements Serializable {
    Integer id;
    @Email(message = "{MSG19}", groups = {ValidateCreateStaffGroup.class})
    @NotBlank(message = "{MSG8}", groups = {ValidateCreateStaffGroup.class})
    String email;
    String name;
    @Range(message = "{MSG8}", min = 1)
    BigDecimal salary;
    String departmentName;
    String roleName;
}