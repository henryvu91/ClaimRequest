package com.vn.dto.form;

import com.vn.utils.validateGroup.ValidateUpdateStaffGroup;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link com.vn.model.Staff}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StaffUpdateInfoDto implements Serializable {
    @NotNull
    private Integer id;
    @NotBlank(groups = {ValidateUpdateStaffGroup.class},message = "{MSG8}")
    private Integer departmentId;
    @NotBlank(groups = {ValidateUpdateStaffGroup.class},message = "{MSG8}")
    private Integer roleId;
    @NotNull
    @Range(message = "{MSG8}", min = 1)
    private BigDecimal salary;

    private String name;

}