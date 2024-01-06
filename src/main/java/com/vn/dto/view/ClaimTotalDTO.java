package com.vn.dto.view;

import lombok.Value;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * DTO for {@link com.vn.model.Claim}
 */
@Value
public class ClaimTotalDTO implements Serializable {
    Integer claimId;
    String claimStaffName;
    String claimProjectName;
    Integer claimTotalHours;
    LocalDate claimProjectStartDate;
    LocalDate claimProjectEndDate;
}