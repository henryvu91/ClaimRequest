package com.vn.services.impl;

import com.vn.dto.form.AddProjectFormDTO;
import com.vn.mapper.form.AddProjectFormMapper;
import com.vn.model.Project;
import com.vn.repositories.ProjectRepository;
import com.vn.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final AddProjectFormMapper addProjectFormMapper;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository, AddProjectFormMapper addProjectFormMapper) {
        this.projectRepository = projectRepository;
        this.addProjectFormMapper = addProjectFormMapper;
    }

    @Override
    @Transactional
    public String saveProject(AddProjectFormDTO addProjectFormDTO) {
        Project project = addProjectFormMapper.toEntity(addProjectFormDTO);
        project.getWorkingsById().forEach(working -> working.setProjectId(project.getId()));
        Project resultSave = projectRepository.save(project);
        if (resultSave.getId() != null) {
            return "Add new Project successfully!";
        }
        return "Adding new Project failed!";
    }
}
