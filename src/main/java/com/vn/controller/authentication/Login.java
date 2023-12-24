package com.vn.controller.authentication;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Login {

    @GetMapping("/login")
    public String getLogin(Model model) {
        if (model.containsAttribute("message")) {
            String message = (String) model.asMap().get("message");
            model.addAttribute("message", message);
        }
        return "view/login";
    }
}
