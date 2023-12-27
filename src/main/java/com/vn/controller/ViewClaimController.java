package com.vn.controller;

import com.vn.model.Claim;
import com.vn.repositories.ClaimRepository;
import com.vn.utils.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Controller
@RequestMapping("/view")
public class ViewClaimController {

    @Autowired
    private ClaimRepository claimRepository;

    @GetMapping("/draft")
    public String viewDraft(Model model) {
        //Claim claim = claimRepository.findClaimByStatus(Status.DRAFT);
        List<Claim> claims = claimRepository.findClaimByStatus(Status.DRAFT);

//        if (claims != null) {
//            for (Claim claim : claims) {
//                LocalDate startDate = claim.getWorkingByWorkingId().getProjectByProjectId().getStartDate();
//                LocalDate endDate = claim.getWorkingByWorkingId().getProjectByProjectId().getEndDate();
//                if (startDate.isBefore(endDate)) {
//                    long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);
//                    model.addAttribute("duration", daysBetween);
//                }
//            }
//        }

        model.addAttribute("claims", claims);
        return "/view/view_claim";
    }

    @GetMapping("/detail")
    public String detail() {
        return "/view/detail";
    }
}
