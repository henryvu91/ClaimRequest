package com.vn.mapper.form;

import com.vn.dto.form.StaffUpdateInfoDto;
import com.vn.model.Staff;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface StaffUpdateInfoMapper {
    StaffUpdateInfoDto toEntity(StaffUpdateInfoDto staffUpdateInfoDto);

    StaffUpdateInfoDto toDto(Staff staff);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Staff partialUpdate(StaffUpdateInfoDto staffUpdateInfoDto, @MappingTarget Staff staff);
}