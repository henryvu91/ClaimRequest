package com.vn.mapper;

import com.vn.dto.StaffIdNameDto;
import com.vn.model.Staff;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface StaffMapper {

    @Mapping(source = "id",target = "id")
    @Mapping(source = "name",target = "name")
    Staff toEntity(StaffIdNameDto staffIdNameDto);


    @Mapping(source = "id",target = "id")
    @Mapping(source = "name",target = "name")
    StaffIdNameDto toDto(Staff staff);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Staff partialUpdate(StaffIdNameDto staffIdNameDto, @MappingTarget Staff staff);
}