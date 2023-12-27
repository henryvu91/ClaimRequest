package com.vn.controller;

import com.vn.model.Claim;
import com.vn.services.ClaimServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/claim")
public class ClaimController {
    @Autowired
    private ClaimServiceImpl claimServiceImpl;

    @GetMapping("/create")
    public String createClaim(Model model) {
        if (model.containsAttribute("message")) {
            String message = (String) model.asMap().get("message");
            model.addAttribute("message", message);
        }
        model.addAttribute("addClaimForm", new Claim());
        return "view/claim/create";
    }
//    @GetMapping("/delete/{id}")
//    public String deleteClaim(@PathVariable("id") int id, Model model) {
//        claimServiceImpl.deleteClaim(id);
//
//        return "";
//    }

}
