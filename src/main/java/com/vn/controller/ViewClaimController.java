package com.vn.controller;

import com.vn.model.Claim;
import com.vn.services.ClaimService;
import com.vn.utils.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/view")
public class ViewClaimController {

    @Autowired
    private ClaimService claimService;

    @GetMapping("/draft")
    public String viewDraft(
            Model model,
            @RequestParam(defaultValue = "1") Integer pageNo
    ){
        Page<Claim> claims = claimService.findClaimByStatus(Status.DRAFT, pageNo);

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
        model.addAttribute("totalPage", claims.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("claims", claims);
        return "/view/view_claim";
    }

    @GetMapping("/pending")
    public String viewPendingApprove(){
        return "/view/view_claim";
    }

    @GetMapping("/paid")
    public String viewPaid(){
        return "/view/view_claim";
    }

    @GetMapping("/rejectOrCancel")
    public String viewRejectOrCancel(){
        return "/view/view_claim";
    }

    @GetMapping("/detail")
    public String detail() {
        return "/view/detail";
    }
}
