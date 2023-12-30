package com.vn.mapper.form;

import com.vn.dto.form.AddWorkByProjectDTO;
import com.vn.model.Working;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface AddWorkByProjectMapper {

    @Mapping(source = "id", target = "workingId")
    @Mapping(source = "staffId", target = "workingStaffId")
    @Mapping(source = "projectId", target = "workingProjectId")
    @Mapping(source = "jobRankId", target = "workingJobRankId")
    @Mapping(source = "startDate", target = "workingStartDate")
    @Mapping(source = "endDate", target = "workingEndDate")
    Working toEntity(AddWorkByProjectDTO addWorkByProjectDTO);

    @Mapping(source = "workingId", target = "id")
    @Mapping(source = "workingStaffId", target = "staffId")
    @Mapping(source = "workingProjectId", target = "projectId")
    @Mapping(source = "workingJobRankId", target = "jobRankId")
    @Mapping(source = "workingStartDate", target = "startDate")
    @Mapping(source = "workingEndDate", target = "endDate")
    AddWorkByProjectDTO toDto(Working working);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Working partialUpdate(AddWorkByProjectDTO addWorkByProjectDTO, @MappingTarget Working working);
}