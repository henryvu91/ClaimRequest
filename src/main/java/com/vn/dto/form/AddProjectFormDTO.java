package com.vn.dto.form;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * DTO for {@link com.vn.model.Project}
 */
@Data
public class AddProjectFormDTO implements Serializable {
    Integer projectId;
    String projectCode;
    String projectName;
    LocalDate projectStartDate;
    LocalDate projectEndDate;
    List<AddWorkByProjectDTO> projectWorkingsById;
}