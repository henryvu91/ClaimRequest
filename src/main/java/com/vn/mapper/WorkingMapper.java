package com.vn.mapper;

import com.vn.dto.WorkingDTO;
import com.vn.model.Working;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {ClaimMapper.class, StaffMapper.class, ProjectMapper.class, JobRankMapper.class})
public interface WorkingMapper {
    Working toEntity(WorkingDTO workingDTO);

    @AfterMapping
    default void linkClaimsById(@MappingTarget Working working) {
        working.getClaimsById().forEach(claimsById -> claimsById.setWorkingByWorkingId(working));
    }

    WorkingDTO toDto(Working working);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Working partialUpdate(WorkingDTO workingDTO, @MappingTarget Working working);
}