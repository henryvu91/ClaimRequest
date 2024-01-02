package com.vn.dto.view;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.vn.model.JobRank}
 */
@Value
public class JobRankDTO implements Serializable {
    Integer id;
    String name;
}