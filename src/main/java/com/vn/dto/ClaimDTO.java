package com.vn.dto;

import com.vn.utils.Status;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;


@Data
public class ClaimDTO implements Serializable {
    //    @Enumerated(EnumType.STRING)
    Status status;
    Integer staffId;
    String staffName;
    String staffDepartment;
    String projectName;
    LocalDate startDate;
    LocalDate endDate;
    LocalDate date;
    LocalTime fromTime;
    LocalTime toTime;
    Integer totalHours;
    String remarks;
    String auditTrail;
}
