package com.vn.mapper;

import com.vn.dto.StaffViewDetailDto;
import com.vn.model.Staff;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface StaffViewMapper {

    @Mapping(source = "departmentName",target = "departmentByDepartmentId.name")
    @Mapping(source = "roleName",target = "roleByRoleId.name")
    Staff toEntity(StaffViewDetailDto staffViewDetailDto);

    @Mapping(source = "departmentByDepartmentId.name",target = "departmentName")
    @Mapping(source = "roleByRoleId.name",target = "roleName")
    StaffViewDetailDto toDto(Staff staff);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Staff partialUpdate(StaffViewDetailDto staffViewDetailDto, @MappingTarget Staff staff);
}