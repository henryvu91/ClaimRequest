package com.vn.dto.form;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link com.vn.model.Working}
 */
@Data
public class AddWorkByProjectDTO implements Serializable {
    Integer workingId;
    @NotNull(message = "Working Staff Name is required")
    Integer workingStaffId;
    @NotNull(message = "Working Project Name is required")
    Integer workingProjectId;
    @NotNull(message = "Working Job Rank Name is required")
    Integer workingJobRankId;
    @NotNull(message = "Working Start Date is required")
    LocalDate workingStartDate;
    @NotNull(message = "Working End Date is required")
    LocalDate workingEndDate;
}