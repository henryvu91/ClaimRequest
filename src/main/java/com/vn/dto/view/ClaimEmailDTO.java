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
public class ClaimEmailDTO implements Serializable {
    private Integer id;
    private Integer staffId;
    private String staffEmail;
    private String staffName;
    private String projectName;
    private LocalDate date;
    private String remarks;

}