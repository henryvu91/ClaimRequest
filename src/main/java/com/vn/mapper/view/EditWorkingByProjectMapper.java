package com.vn.mapper.view;

import com.vn.dto.view.EditWorkingByProjectDTO;
import com.vn.model.Working;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface EditWorkingByProjectMapper {

    @Mapping(source = "workingId", target = "id")
    @Mapping(source = "workingStaffId", target = "staffId")
    @Mapping(source = "workingProjectId", target = "projectId")
    @Mapping(source = "workingJobRankId", target = "jobRankId")
    @Mapping(source = "workingStartDate", target = "startDate")
    @Mapping(source = "workingEndDate", target = "endDate")
    Working toEntity(EditWorkingByProjectDTO editWorkingByProjectDTO);

    @Mapping(source = "id", target = "workingId")
    @Mapping(source = "staffId", target = "workingStaffId")
    @Mapping(source = "projectId", target = "workingProjectId")
    @Mapping(source = "jobRankId", target = "workingJobRankId")
    @Mapping(source = "startDate", target = "workingStartDate")
    @Mapping(source = "endDate", target = "workingEndDate")
    @Mapping(source = "jobRankByJobRankId.name", target = "workingJobRankName")
    @Mapping(source = "staffByStaffId.name", target = "workingStaffName")
    EditWorkingByProjectDTO toDto(Working working);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Working partialUpdate(EditWorkingByProjectDTO editWorkingByProjectDTO, @MappingTarget Working working);
}