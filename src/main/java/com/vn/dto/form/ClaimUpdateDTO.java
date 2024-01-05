package com.vn.dto.form;

import com.vn.utils.Status;
import com.vn.utils.validateGroup.ValidateCreateClaimGroup;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * DTO for {@link com.vn.model.Claim}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClaimUpdateDTO implements Serializable {
    @NotNull(message = "{MSG8}")
    private Integer id;
    @NotNull(message = "{MSG8}", groups = {ValidateCreateClaimGroup.class})
    private Integer workingId;
    @NotNull(message = "{MSG8}", groups = {ValidateCreateClaimGroup.class})
    @Future(message = "{Claim.Create.MSG1}", groups = {ValidateCreateClaimGroup.class})
    private LocalDate date;
    @NotNull(message = "{MSG8}", groups = {ValidateCreateClaimGroup.class})
    private LocalTime fromTime;
    @NotNull(message = "{MSG8}", groups = {ValidateCreateClaimGroup.class})
    private LocalTime toTime;
    @NotNull(message = "{MSG8}", groups = {ValidateCreateClaimGroup.class})
    private Integer totalHours;
    @NotNull(message = "{MSG8}", groups = {ValidateCreateClaimGroup.class})
    private Status status;
    @NotNull(message = "{MSG8}", groups = {ValidateCreateClaimGroup.class})
    private String remarks;
    private LocalDate joinedProjectDate;
    private LocalDate leftProjectDate;
    private String projectName;
    private LocalDate projectStartDate;
    private LocalDate projectEndDate;
    private String jobRankName;
}