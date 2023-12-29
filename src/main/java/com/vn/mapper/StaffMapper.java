package com.vn.mapper;

import com.vn.dto.StaffDTO;
import com.vn.model.Staff;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {DepartmentMapper.class, RoleMapper.class, WorkingMapper.class})
public interface StaffMapper {
    Staff toEntity(StaffDTO staffDTO);

    @AfterMapping
    default void linkWorkingById(@MappingTarget Staff staff) {
        staff.getWorkingsById().forEach(workingById -> workingById.setStaffByStaffId(staff));
    }

    StaffDTO toDto(Staff staff);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Staff partialUpdate(StaffDTO staffDTO, @MappingTarget Staff staff);
}