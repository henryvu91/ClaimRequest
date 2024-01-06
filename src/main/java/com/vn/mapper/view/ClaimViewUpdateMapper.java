package com.vn.mapper.view;

import com.vn.dto.view.ClaimViewUpdateDTO;
import com.vn.model.Claim;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ClaimViewUpdateMapper {
    @Mapping(source = "jobRankName", target = "workingByWorkingId.jobRankByJobRankId.name")
    @Mapping(source = "projectEndDate", target = "workingByWorkingId.projectByProjectId.endDate")
    @Mapping(source = "projectStartDate", target = "workingByWorkingId.projectByProjectId.startDate")
    @Mapping(source = "projectName", target = "workingByWorkingId.projectByProjectId.name")
    @Mapping(source = "leftProjectDate", target = "workingByWorkingId.endDate")
    @Mapping(source = "joinedProjectDate", target = "workingByWorkingId.startDate")
    Claim toEntity(ClaimViewUpdateDTO claimViewUpdateDTO);

    @InheritInverseConfiguration(name = "toEntity")
    ClaimViewUpdateDTO toDto(Claim claim);

    @InheritConfiguration(name = "toEntity")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Claim partialUpdate(ClaimViewUpdateDTO claimViewUpdateDTO, @MappingTarget Claim claim);
}