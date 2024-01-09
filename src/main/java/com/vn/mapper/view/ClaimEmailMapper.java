package com.vn.mapper.view;

import com.vn.dto.view.ClaimEmailDTO;
import com.vn.model.Claim;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ClaimEmailMapper {
    @Mapping(source = "projectName", target = "workingByWorkingId.projectByProjectId.name")
    @Mapping(source = "staffName", target = "workingByWorkingId.staffByStaffId.name")
    @Mapping(source = "staffEmail", target = "workingByWorkingId.staffByStaffId.email")
    @Mapping(source = "staffId", target = "workingByWorkingId.staffId")
    Claim toEntity(ClaimEmailDTO claimEmailDTO);

    @InheritInverseConfiguration(name = "toEntity")
    ClaimEmailDTO toDto(Claim claim);

    @InheritConfiguration(name = "toEntity")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Claim partialUpdate(ClaimEmailDTO claimEmailDTO, @MappingTarget Claim claim);
}