package com.vn.mapper.view;

import com.vn.dto.view.JobRankDTO;
import com.vn.model.JobRank;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface JobRankMapper {
    JobRank toEntity(JobRankDTO jobRankDTO);

    JobRankDTO toDto(JobRank jobRank);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    JobRank partialUpdate(JobRankDTO jobRankDTO, @MappingTarget JobRank jobRank);
}