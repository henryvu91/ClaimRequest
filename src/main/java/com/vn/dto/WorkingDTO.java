package com.vn.dto;

import lombok.Value;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link com.vn.model.Working}
 */
@Value
public class WorkingDTO implements Serializable {
    Integer staffId;
    Integer projectId;
    Integer jobRankId;
    LocalDate workingStartDate;
    LocalDate workingEndDate;
}