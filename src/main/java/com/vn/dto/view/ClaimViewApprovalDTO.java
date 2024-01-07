package com.vn.dto.view;

import com.vn.utils.Status;
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
public class ClaimViewApprovalDTO implements Serializable {
    private Integer id;

    private LocalDate date;
    private LocalTime fromTime;
    private LocalTime toTime;
    private Integer totalHours;
    private Status status;
    private Integer staffId;
    private LocalDate joinedProjectDate;
    private LocalDate leftProjectDate;
    private String staffName;
    private String department;
    private String projectName;
    private LocalDate projectStartDate;
    private LocalDate projectEndDate;
    private String jobRankName;
}