package com.vn.mapper.form;

import com.vn.dto.form.ClaimApprovalDTO;
import com.vn.model.Claim;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ClaimApprovalMapper {
    Claim toEntity(ClaimApprovalDTO claimApprovalDTO);

    ClaimApprovalDTO toDto(Claim claim);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Claim partialUpdate(ClaimApprovalDTO claimApprovalDTO, @MappingTarget Claim claim);
}