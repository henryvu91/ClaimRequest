package com.vn.dto;

import lombok.Value;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * DTO for {@link com.vn.model.Project}
 */
@Value
public class ProjectDto implements Serializable {
    Integer id;
    String code;
    String name;
    LocalDate startDate;
    LocalDate endDate;
    List<WorkingDTO> workingsById;
}