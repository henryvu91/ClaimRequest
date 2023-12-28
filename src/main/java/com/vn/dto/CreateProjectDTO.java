package com.vn.dto;

import lombok.Value;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * DTO for {@link com.vn.model.Project} and {@link com.vn.model.Working}
 */
@Value
public class CreateProjectDTO implements Serializable {
    String projectCode;
    String name;
    LocalDate projectStartDate;
    LocalDate projectEndDate;
    List<WorkingDTO> workingDTOs;
}