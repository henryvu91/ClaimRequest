package com.vn.mapper.form;

import com.vn.dto.form.ClaimUpdateDTO;
import com.vn.model.Claim;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ClaimUpdateMapper {
//    @Mapping(source = "jobRankName", target = "workingByWorkingId.jobRankByJobRankId.name")
//    @Mapping(source = "projectEndDate", target = "workingByWorkingId.projectByProjectId.endDate")
//    @Mapping(source = "projectStartDate", target = "workingByWorkingId.projectByProjectId.startDate")
//    @Mapping(source = "projectName", target = "workingByWorkingId.projectByProjectId.name")
//    @Mapping(source = "leftProjectDate", target = "workingByWorkingId.endDate")
//    @Mapping(source = "joinedProjectDate", target = "workingByWorkingId.startDate")
    Claim toEntity(ClaimUpdateDTO claimUpdateDTO);

    @InheritInverseConfiguration(name = "toEntity")
    ClaimUpdateDTO toDto(Claim claim);

    @InheritConfiguration(name = "toEntity")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Claim partialUpdate(ClaimUpdateDTO claimUpdateDTO, @MappingTarget Claim claim);
}