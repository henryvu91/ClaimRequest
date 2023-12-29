package com.vn.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link com.vn.model.Project}
 */
@Data
public class ProjectDTO implements Serializable {
    Integer projectId;
    String projectCode;
    String projectName;
    LocalDate projectStartDate;
    LocalDate projectEndDate;
}