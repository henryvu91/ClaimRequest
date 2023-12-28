package com.vn.mapper;

import com.vn.dto.StaffIdNameDto2;
import com.vn.model.Staff;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface StaffMapper {

    @Mapping(source = "id",target = "id")
    @Mapping(source = "name",target = "name")
    Staff toEntity(StaffIdNameDto2 staffIdNameDto2);


    @Mapping(source = "id",target = "id")
    @Mapping(source = "name",target = "name")
    StaffIdNameDto2 toDto(Staff staff);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Staff partialUpdate(StaffIdNameDto2 staffIdNameDto2, @MappingTarget Staff staff);
}