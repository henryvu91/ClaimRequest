package com.vn.mapper.view;

import com.vn.dto.view.EditProjectDTO;
import com.vn.model.Project;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {EditWorkingByProjectMapper.class})
public interface EditProjectMapper {

    @Mapping(source = "projectId", target = "id")
    @Mapping(source = "projectCode", target = "code")
    @Mapping(source = "projectName", target = "name")
    @Mapping(source = "projectStartDate", target = "startDate")
    @Mapping(source = "projectEndDate", target = "endDate")
    @Mapping(source = "projectWorkingsById", target = "workingsById")
    Project toEntity(EditProjectDTO editProjectDTO);

    @AfterMapping
    default void linkWorkingsById(@MappingTarget Project project) {
        project.getWorkingsById().forEach(workingsById -> workingsById.setProjectByProjectId(project));
    }

    @Mapping(source = "id", target = "projectId")
    @Mapping(source = "code", target = "projectCode")
    @Mapping(source = "name", target = "projectName")
    @Mapping(source = "startDate", target = "projectStartDate")
    @Mapping(source = "endDate", target = "projectEndDate")
    @Mapping(source = "workingsById", target = "projectWorkingsById")
    EditProjectDTO toDto(Project project);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Project partialUpdate(EditProjectDTO editProjectDTO, @MappingTarget Project project);
}