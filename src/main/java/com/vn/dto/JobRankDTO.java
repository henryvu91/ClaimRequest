package com.vn.dto;

import lombok.Value;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link com.vn.model.JobRank}
 */
@Value
public class JobRankDTO implements Serializable {
    Integer id;
    String name;
    List<WorkingDTO> workingsById;
}