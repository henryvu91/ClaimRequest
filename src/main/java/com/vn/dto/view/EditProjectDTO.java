package com.vn.dto.view;

import lombok.Value;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * DTO for {@link com.vn.model.Project}
 */
@Value
public class EditProjectDTO implements Serializable {
    Integer projectId;
    String projectCode;
    String projectName;
    LocalDate projectStartDate;
    LocalDate projectEndDate;
    List<EditWorkingByProjectDTO> projectWorkingsById;
}