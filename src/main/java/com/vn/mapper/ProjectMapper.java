package com.vn.mapper;

import com.vn.dto.ProjectDTO;
import com.vn.model.Project;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {WorkingMapper.class})
public interface ProjectMapper {
    Project toEntity(ProjectDTO projectDTO);

    @AfterMapping
    default void linkWorkingById(@MappingTarget Project project) {
        project.getWorkingsById().forEach(workingById -> workingById.setProjectByProjectId(project));
    }

    ProjectDTO toDto(Project project);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Project partialUpdate(ProjectDTO projectDTO, @MappingTarget Project project);
}