package com.vn.mapper;

import com.vn.dto.JobRankDTO;
import com.vn.model.JobRank;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {WorkingMapper.class})
public interface JobRankMapper {
    JobRank toEntity(JobRankDTO jobRankDTO);

    @AfterMapping
    default void linkWorkingById(@MappingTarget JobRank jobRank) {
        jobRank.getWorkingsById().forEach(workingById -> workingById.setJobRankByJobRankId(jobRank));
    }

    JobRankDTO toDto(JobRank jobRank);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    JobRank partialUpdate(JobRankDTO jobRankDTO, @MappingTarget JobRank jobRank);
}