package com.vn.services.impl;

import com.vn.dto.form.AddProjectFormDTO;
import com.vn.dto.form.AddWorkByProjectDTO;
import com.vn.mapper.form.AddProjectFormMapper;
import com.vn.mapper.form.AddWorkByProjectMapper;
import com.vn.model.Project;
import com.vn.model.Working;
import com.vn.repositories.ProjectRepository;
import com.vn.repositories.WorkingRepository;
import com.vn.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    private final WorkingRepository workingRepository;
    private final AddProjectFormMapper addProjectFormMapper;

    private final AddWorkByProjectMapper addWorkByProjectMapper;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository, WorkingRepository workingRepository, AddProjectFormMapper addProjectFormMapper, AddWorkByProjectMapper addWorkByProjectMapper) {
        this.projectRepository = projectRepository;
        this.workingRepository = workingRepository;
        this.addProjectFormMapper = addProjectFormMapper;
        this.addWorkByProjectMapper = addWorkByProjectMapper;
    }

    @Override
    @Transactional
    public String saveProject(AddProjectFormDTO addProjectFormDTO) {
        try {
            Project project = addProjectFormMapper.toEntity(addProjectFormDTO);
            Project savedProject = projectRepository.save(project);
            if (savedProject == null || savedProject.getId() == null) {
                return "Adding new Project failed!";
            }


            List<AddWorkByProjectDTO> projectWorkingsById = addProjectFormDTO.getProjectWorkingsById();
            for (AddWorkByProjectDTO addWorkByProjectDTO : projectWorkingsById) {
                Working working = addWorkByProjectMapper.toEntity(addWorkByProjectDTO);
                working.setProjectId(savedProject.getId());
                workingRepository.save(working);
            }


            return "Add new Project successfully!";
        } catch (Exception e) {
            // Log the exception and handle it accordingly
            // logger.error("Error saving project: ", e);
            return "Adding new Project failed due to an error!";
        }
    }
}
