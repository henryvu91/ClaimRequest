package com.vn.controller;

import com.vn.model.Claim;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/claim")
public class ClaimController {
    @GetMapping("/create")
    public String createClaim(Model model){
        if (model.containsAttribute("message")) {
            String message = (String) model.asMap().get("message");
            model.addAttribute("message", message);
        }
        model.addAttribute("addClaimForm", new Claim());
        return "view/claim/create";
    }


}
