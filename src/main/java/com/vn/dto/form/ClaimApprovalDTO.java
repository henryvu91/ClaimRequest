package com.vn.dto.form;

import com.vn.utils.validateGroup.ValidateCreateClaimGroup;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link com.vn.model.Claim}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClaimApprovalDTO implements Serializable {
    private Integer id;
    @NotNull(message = "{MSG8}", groups = {ValidateCreateClaimGroup.class})
    private String remarks;
}