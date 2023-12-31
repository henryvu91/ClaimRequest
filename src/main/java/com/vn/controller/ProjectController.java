package com.vn.controller;

import com.vn.dto.form.AddProjectFormDTO;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/project")
public class ProjectController {

    @GetMapping("/view")
    public String viewProjectGet() {
        return "view/project/view";
    }

    @GetMapping("/create")
    public String createProjectGet(Model model) {
        if (model.containsAttribute("message")) {
            String message = (String) model.asMap().get("message");
            model.addAttribute("message", message);
        }
        model.addAttribute("addProjectFormDTO", new AddProjectFormDTO());
        return "view/project/create";
    }

    @PostMapping("/create")
    public String createProjectPost(@ModelAttribute("addProjectForm") @Valid AddProjectFormDTO addProjectFormDTO, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("errors", result.getAllErrors());
            return "view/project/create";
        }

        return "view/project/create";
    }
}
