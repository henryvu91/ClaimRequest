package com.vn.mapper.form;

import com.vn.dto.form.StaffUpdateInfoDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface StaffUpdateInfoMapper {
    StaffUpdateInfoDto toEntity(StaffUpdateInfoDto staffUpdateInfoDto);

    StaffUpdateInfoDto toDto(com.vn.model.Staff staff);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    com.vn.model.Staff partialUpdate(StaffUpdateInfoDto staffUpdateInfoDto, @MappingTarget com.vn.model.Staff staff);
}