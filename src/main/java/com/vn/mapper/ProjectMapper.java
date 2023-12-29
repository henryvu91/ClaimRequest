package com.vn.mapper;

import com.vn.dto.ProjectDTO;
import com.vn.model.Project;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProjectMapper {
    ProjectMapper INSTANCE = Mappers.getMapper(ProjectMapper.class);

    @Mapping(source = "id", target = "projectId")
    @Mapping(source = "code", target = "projectCode")
    @Mapping(source = "name", target = "projectName")
    @Mapping(source = "startDate", target = "projectStartDate")
    @Mapping(source = "endDate", target = "projectEndDate")
    ProjectDTO toDTO(Project project);

}
