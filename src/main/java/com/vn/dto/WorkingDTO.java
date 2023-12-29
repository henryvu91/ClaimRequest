package com.vn.dto;

import lombok.Value;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link com.vn.model.Working}
 */
@Value
public class WorkingDTO implements Serializable {
    Integer id;
    Integer staffId;
    Integer projectId;
    Integer jobRankId;
    LocalDate startDate;
    LocalDate endDate;
}