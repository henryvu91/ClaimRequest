package com.vn.mapper.view;

import com.vn.dto.view.ClaimViewApprovalDTO;
import com.vn.model.Claim;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ClaimViewApprovalMapper {
    @Mapping(source = "jobRankName", target = "workingByWorkingId.jobRankByJobRankId.name")
    @Mapping(source = "projectEndDate", target = "workingByWorkingId.projectByProjectId.endDate")
    @Mapping(source = "projectStartDate", target = "workingByWorkingId.projectByProjectId.startDate")
    @Mapping(source = "projectName", target = "workingByWorkingId.projectByProjectId.name")
    @Mapping(source = "department", target = "workingByWorkingId.staffByStaffId.departmentByDepartmentId.name")
    @Mapping(source = "staffName", target = "workingByWorkingId.staffByStaffId.name")
    @Mapping(source = "leftProjectDate", target = "workingByWorkingId.endDate")
    @Mapping(source = "joinedProjectDate", target = "workingByWorkingId.startDate")
    @Mapping(source = "staffId", target = "workingByWorkingId.staffId")
    Claim toEntity(ClaimViewApprovalDTO claimViewApprovalDTO);

    @InheritInverseConfiguration(name = "toEntity")
    ClaimViewApprovalDTO toDto(Claim claim);

    @InheritConfiguration(name = "toEntity")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Claim partialUpdate(ClaimViewApprovalDTO claimViewApprovalDTO, @MappingTarget Claim claim);
}