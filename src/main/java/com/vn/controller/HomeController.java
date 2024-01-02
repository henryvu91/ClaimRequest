package com.vn.controller;

import com.vn.services.impl.StaffServiceImpl;
import com.vn.utils.session.UserInfoSession;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class HomeController {

    private final StaffServiceImpl staffService;

    public HomeController(StaffServiceImpl staffService) {
        this.staffService = staffService;
    }

    @GetMapping("/")
    public String index(Principal principal, HttpSession session, Model model) {
        if (model.containsAttribute("message")) {
            String message = (String) model.asMap().get("message");
            model.addAttribute("message", message);
        }

        if (principal != null) {
            UserInfoSession.checkAndAddSession(principal, session, staffService);
        }

        model.addAttribute("pageTitle", "Home Page");
        return "index";
    }
}
