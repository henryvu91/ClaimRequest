package com.vn.mapper;

import com.vn.dto.StaffUpdateInfoDto;
import com.vn.model.Staff;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface StaffUpdateInfoMapper {
    Staff toEntity(StaffUpdateInfoDto staffUpdateInfoDto);

    StaffUpdateInfoDto toDto(Staff staff);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Staff partialUpdate(StaffUpdateInfoDto staffUpdateInfoDto, @MappingTarget Staff staff);
}