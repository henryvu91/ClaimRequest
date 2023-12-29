package com.vn.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link com.vn.model.Working}
 */
@Data
public class WorkingDTO implements Serializable {
    Integer workingId;
    Integer workingStaffId;
    Integer workingProjectId;
    Integer workingJobRankId;
    LocalDate workingStartDate;
    LocalDate workingEndDate;
}