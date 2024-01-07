package com.vn.mapper.form;

import com.vn.dto.form.SubmitClaimDTO;
import com.vn.model.Claim;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface SubmitClaimMapper {
    Claim toEntity(SubmitClaimDTO submitClaimDTO);

    SubmitClaimDTO toDto(Claim claim);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Claim partialUpdate(SubmitClaimDTO submitClaimDTO, @MappingTarget Claim claim);
}