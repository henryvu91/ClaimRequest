package com.vn.dto.form;

import com.vn.utils.Status;
import com.vn.utils.validateGroup.ValidateCreateClaimGroup;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

/**
 * DTO for {@link com.vn.model.Claim}
 */
@Data
public class SubmitClaimDTO implements Serializable {
    Integer id;
    @NotNull(message = "{MSG8}", groups = {ValidateCreateClaimGroup.class})
    Integer workingId;
    @NotNull(message = "{MSG8}", groups = {ValidateCreateClaimGroup.class})
    Status status;
    String auditTrail;
}