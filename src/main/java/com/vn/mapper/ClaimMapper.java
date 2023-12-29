package com.vn.mapper;

import com.vn.dto.ClaimDTO;
import com.vn.model.Claim;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {WorkingMapper.class})
public interface ClaimMapper {
    Claim toEntity(ClaimDTO claimDTO);

    ClaimDTO toDto(Claim claim);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Claim partialUpdate(ClaimDTO claimDTO, @MappingTarget Claim claim);
}