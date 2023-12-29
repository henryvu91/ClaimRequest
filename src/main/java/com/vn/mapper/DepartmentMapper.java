package com.vn.mapper;

import com.vn.dto.DepartmentDTO;
import com.vn.model.Department;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {StaffMapper.class})
public interface DepartmentMapper {
    Department toEntity(DepartmentDTO departmentDTO);

    @AfterMapping
    default void linkStaffsById(@MappingTarget Department department) {
        department.getStaffById().forEach(staffsById -> staffsById.setDepartmentByDepartmentId(department));
    }

    DepartmentDTO toDto(Department department);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Department partialUpdate(DepartmentDTO departmentDTO, @MappingTarget Department department);
}