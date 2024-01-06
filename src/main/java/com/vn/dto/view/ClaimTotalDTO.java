package com.vn.dto.view;

import com.vn.utils.Status;
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
    Status claimStatus;
    String claimStaffName;
    String claimProjectName;
    Integer claimTotalHours;
    LocalDate claimProjectStartDate;
    LocalDate claimProjectEndDate;
}