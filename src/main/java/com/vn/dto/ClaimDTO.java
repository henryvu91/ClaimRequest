package com.vn.dto;

import com.vn.utils.Status;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * DTO for {@link com.vn.model.Claim}
 */
@Value
public class ClaimDTO implements Serializable {
    Integer id;
    Integer workingId;
    LocalDate date;
    LocalTime fromTime;
    LocalTime toTime;
    Integer totalHours;
    Status status;
    String remarks;
    String auditTrail;
    WorkingDTO workingByWorkingId;
}