package com.vn.mapper;

import com.vn.dto.WorkingDTO;
import com.vn.model.Working;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface WorkingMapper {
    WorkingMapper INSTANCE = Mappers.getMapper(WorkingMapper.class);

    @Mapping(source = "id", target = "workingId")
    @Mapping(source = "staffId", target = "workingStaffId")
    @Mapping(source = "projectId", target = "workingProjectId")
    @Mapping(source = "jobRankId", target = "workingJobRankId")
    @Mapping(source = "startDate", target = "workingStartDate")
    @Mapping(source = "endDate", target = "workingEndDate")
    WorkingDTO toDTO(Working working);

}
