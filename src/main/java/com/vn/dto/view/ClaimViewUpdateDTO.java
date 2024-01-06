package com.vn.dto.view;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link com.vn.model.Claim}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClaimViewUpdateDTO implements Serializable {
    private Integer id;
    private LocalDate joinedProjectDate;
    private LocalDate leftProjectDate;
    private String projectName;
    private LocalDate projectStartDate;
    private LocalDate projectEndDate;
    private String jobRankName;
}