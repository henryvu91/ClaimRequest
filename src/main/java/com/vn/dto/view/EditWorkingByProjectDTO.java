package com.vn.dto.view;

import lombok.Value;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link com.vn.model.Working}
 */
@Value
public class EditWorkingByProjectDTO implements Serializable {
    Integer workingId;
    Integer workingStaffId;
    Integer workingProjectId;
    Integer workingJobRankId;
    LocalDate workingStartDate;
    LocalDate workingEndDate;
    String workingJobRankName;
    String workingStaffName;
}