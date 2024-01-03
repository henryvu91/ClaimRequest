package com.vn.dto.form;

import com.vn.utils.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * DTO for {@link com.vn.model.Claim}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClaimCreateDto implements Serializable {
    LocalDate date;
    LocalTime fromTime;
    LocalTime toTime;
    Integer totalHours;
    Status status;
    String remarks;
    String auditTrail;
    Integer workingByWorkingIdStaffId;
    Integer workingByWorkingIdProjectId;
}