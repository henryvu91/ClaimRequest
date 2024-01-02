package com.vn.dto.form;

import jakarta.validation.constraints.NotNull;
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
    @NotNull(message = "Project Code is required")
    String projectCode;
    @NotNull(message = "Project Name is required")
    String projectName;
    @NotNull(message = "Project Start Date is required")
    LocalDate projectStartDate;
    @NotNull(message = "Project End Date is required")
    LocalDate projectEndDate;
    List<AddWorkByProjectDTO> projectWorkingsById;
}