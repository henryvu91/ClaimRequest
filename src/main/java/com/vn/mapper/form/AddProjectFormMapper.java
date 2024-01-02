package com.vn.mapper.form;

import com.vn.dto.form.AddProjectFormDTO;
import com.vn.model.Project;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {AddWorkByProjectMapper.class})
public interface AddProjectFormMapper {

    @Mapping(source = "projectId", target = "id")
    @Mapping(source = "projectCode", target = "code")
    @Mapping(source = "projectName", target = "name")
    @Mapping(source = "projectStartDate", target = "startDate")
    @Mapping(source = "projectEndDate", target = "endDate")
    Project toEntity(AddProjectFormDTO addProjectFormDTO);

    @AfterMapping
    default void linkWorkingsById(@MappingTarget Project project) {
        project.getWorkingsById().forEach(workingsById -> workingsById.setProjectByProjectId(project));
    }

    @Mapping(source = "id", target = "projectId")
    @Mapping(source = "code", target = "projectCode")
    @Mapping(source = "name", target = "projectName")
    @Mapping(source = "startDate", target = "projectStartDate")
    @Mapping(source = "endDate", target = "projectEndDate")
    AddProjectFormDTO toDto(Project project);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Project partialUpdate(AddProjectFormDTO addProjectFormDTO, @MappingTarget Project project);
}