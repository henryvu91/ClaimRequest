package com.vn.services;

import com.vn.dto.form.AddProjectFormDTO;
import com.vn.dto.view.EditProjectDTO;
import org.springframework.data.domain.Page;

public interface ProjectService {
    String saveProject(AddProjectFormDTO addProjectFormDTO);

    Page<AddProjectFormDTO> getContentPaginated(int pageNo, int pageSize);

    String deleteProject(Integer projectId);

    EditProjectDTO getProjectById(Integer id);
}
