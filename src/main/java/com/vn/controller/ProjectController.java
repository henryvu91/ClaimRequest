package com.vn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/project")
public class ProjectController {

    @GetMapping("/view")
    public String viewProjectGet() {
        return "view/project/view";
    }

    @GetMapping("/create")
    public String createProjectGet() {
        return "view/project/create";
    }

    @PostMapping("/create")
    public String createProjectPost() {
        return "view/project/create";
    }
}
