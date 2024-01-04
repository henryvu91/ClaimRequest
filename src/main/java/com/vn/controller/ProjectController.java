package com.vn.controller;

import com.vn.dto.form.AddProjectFormDTO;
import com.vn.services.ProjectService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/project")
public class ProjectController {
    private static final String PROJECT_CREATE_LINK = "view/project/create";

    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/view")
    public String viewProjectGet(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize,
            HttpSession session,
            Model model) {
        if (model.containsAttribute("message")) {
            String message = (String) model.asMap().get("message");
            model.addAttribute("message", message);
        }

        int pageNoI = (pageNo <= 0) ? 0 : pageNo - 1;

        Page<AddProjectFormDTO> addProjectFormDTO = projectService.getContentPaginated(pageNoI, pageSize);

        Integer totalPages = addProjectFormDTO.getTotalPages();

        model.addAttribute("projectList", addProjectFormDTO);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("pageSize", pageSize);

        return "view/project/view";
    }

    @GetMapping("/create")
    public String createProjectGet(Model model) {
        if (model.containsAttribute("message")) {
            String message = (String) model.asMap().get("message");
            model.addAttribute("message", message);
        }
        AddProjectFormDTO addProjectFormDTO = new AddProjectFormDTO();
//        addProjectFormDTO.setProjectWorkingsById(Collections.singletonList(new AddWorkByProjectDTO()));
        model.addAttribute("addProjectForm", addProjectFormDTO);
        return PROJECT_CREATE_LINK;
    }

    @PostMapping("/create")
    public String createProjectPost(@ModelAttribute("addProjectForm") @Valid AddProjectFormDTO addProjectFormDTO, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("errors", result.getAllErrors());
            return PROJECT_CREATE_LINK;
        }
        String message = projectService.saveProject(addProjectFormDTO);
        if (message.equals("Add new Project successfully!")) {
            redirectAttributes.addFlashAttribute("message", message);
            return "redirect:/project/view";
        }
        model.addAttribute("message", message);
        return PROJECT_CREATE_LINK;
    }

    @PostMapping("/delete")
    public String deleteProjectPost(@RequestParam("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        String message = projectService.deleteProject(id);
        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/project/view";
    }
}
