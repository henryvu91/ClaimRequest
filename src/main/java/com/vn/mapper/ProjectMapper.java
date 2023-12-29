package com.vn.mapper;

import com.vn.dto.ProjectDto;
import com.vn.model.Project;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {WorkingMapper.class})
public interface ProjectMapper {
    Project toEntity(ProjectDto projectDto);

    @AfterMapping
    default void linkWorkingsById(@MappingTarget Project project) {
        project.getWorkingsById().forEach(workingsById -> workingsById.setProjectByProjectId(project));
    }

    ProjectDto toDto(Project project);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Project partialUpdate(ProjectDto projectDto, @MappingTarget Project project);
}