package com.vn.mapper;

import com.vn.dto.WorkingDTO;
import com.vn.model.Working;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface WorkingMapper {
    Working toEntity(WorkingDTO workingDTO);

    WorkingDTO toDto(Working working);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Working partialUpdate(WorkingDTO workingDTO, @MappingTarget Working working);
}