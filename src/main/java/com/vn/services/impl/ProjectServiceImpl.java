package com.vn.services.impl;

import com.vn.dto.form.AddProjectFormDTO;
import com.vn.dto.form.AddWorkByProjectDTO;
import com.vn.dto.view.EditProjectDTO;
import com.vn.mapper.form.AddProjectFormMapper;
import com.vn.mapper.form.AddWorkByProjectMapper;
import com.vn.mapper.view.EditProjectMapper;
import com.vn.mapper.view.EditWorkingByProjectMapper;
import com.vn.model.Project;
import com.vn.model.Working;
import com.vn.repositories.ProjectRepository;
import com.vn.repositories.WorkingRepository;
import com.vn.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final WorkingRepository workingRepository;
    private final AddProjectFormMapper addProjectFormMapper;
    private final AddWorkByProjectMapper addWorkByProjectMapper;
    private final EditProjectMapper editProjectMapper;
    private final EditWorkingByProjectMapper editWorkingByProjectMapper;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository, WorkingRepository workingRepository, AddProjectFormMapper addProjectFormMapper, AddWorkByProjectMapper addWorkByProjectMapper, EditProjectMapper editProjectMapper, EditWorkingByProjectMapper editWorkingByProjectMapper) {
        this.projectRepository = projectRepository;
        this.workingRepository = workingRepository;
        this.addProjectFormMapper = addProjectFormMapper;
        this.addWorkByProjectMapper = addWorkByProjectMapper;
        this.editProjectMapper = editProjectMapper;
        this.editWorkingByProjectMapper = editWorkingByProjectMapper;
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

    @Override
    public Page<AddProjectFormDTO> getContentPaginated(int pageNo, int pageSize) {
        Pageable pageable = Pageable.ofSize(pageSize).withPage(pageNo);
        Page<Project> projectPage = projectRepository.findAll(pageable);
        return projectPage.map(addProjectFormMapper::toDto);
    }

    @Override
    @Transactional
    public String deleteProject(Integer projectId) {
        try {
            projectRepository.deleteById(projectId);
            return "Delete Project id " + projectId + " successfully!";
        } catch (Exception e) {
            return "Delete Project id " + projectId + " failed due to an error!";
        }
    }

    @Override
    public EditProjectDTO getProjectById(Integer id) {
        Optional<Project> project = projectRepository.findById(id);
        return project.map(editProjectMapper::toDto).orElse(null);
    }
}
