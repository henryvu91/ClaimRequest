package com.vn.mapper.view;

import com.vn.dto.view.ClaimTotalDTO;
import com.vn.model.Claim;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ClaimTotalMapper {
    @Mapping(source = "claimId", target = "id")
    @Mapping(source = "claimStatus", target = "status")
    @Mapping(source = "claimProjectStartDate", target = "workingByWorkingId.projectByProjectId.startDate")
    @Mapping(source = "claimProjectEndDate", target = "workingByWorkingId.projectByProjectId.endDate")
    @Mapping(source = "claimTotalHours", target = "totalHours")
    @Mapping(source = "claimStaffName", target = "workingByWorkingId.staffByStaffId.name")
    @Mapping(source = "claimProjectName", target = "workingByWorkingId.projectByProjectId.name")
    Claim toEntity(ClaimTotalDTO totalClaimDTO);

    @Mapping(target = "claimId", source = "id")
    @Mapping(target = "claimStatus", source = "status")
    @Mapping(target = "claimProjectStartDate", source = "workingByWorkingId.projectByProjectId.startDate")
    @Mapping(target = "claimProjectEndDate", source = "workingByWorkingId.projectByProjectId.endDate")
    @Mapping(target = "claimTotalHours", source = "totalHours")
    @Mapping(target = "claimStaffName", source = "workingByWorkingId.staffByStaffId.name")
    @Mapping(target = "claimProjectName", source = "workingByWorkingId.projectByProjectId.name")
    ClaimTotalDTO toDto(Claim claim);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Claim partialUpdate(ClaimTotalDTO totalClaimDTO, @MappingTarget Claim claim);
}