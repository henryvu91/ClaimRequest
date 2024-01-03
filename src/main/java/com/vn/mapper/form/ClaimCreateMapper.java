package com.vn.mapper.form;

import com.vn.dto.form.ClaimCreateDto;
import com.vn.model.Claim;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ClaimCreateMapper {
    @Mapping(source = "workingByWorkingIdProjectId", target = "workingByWorkingId.projectId")
    @Mapping(source = "workingByWorkingIdStaffId", target = "workingByWorkingId.staffId")
    Claim toEntity(ClaimCreateDto claimCreateDto);

    @InheritInverseConfiguration(name = "toEntity")
    ClaimCreateDto toDto(Claim claim);

    @InheritConfiguration(name = "toEntity")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Claim partialUpdate(ClaimCreateDto claimCreateDto, @MappingTarget Claim claim);
}