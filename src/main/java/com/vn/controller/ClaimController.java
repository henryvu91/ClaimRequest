package com.vn.controller;

import com.vn.model.Claim;
import com.vn.model.Working;
import com.vn.services.ClaimService;
import com.vn.utils.Status;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequestMapping("/claim")
public class ClaimController {

    @Autowired
    private ClaimService claimService;

    @GetMapping("/draft")
    public String viewDraft(
            Model model,
            @RequestParam(defaultValue = "1") Integer pageNo,
            @RequestParam(defaultValue = "5") Integer pageSize
    ){
        Page<Claim> claims = claimService.findClaimByStatus(Status.DRAFT, pageNo, pageSize);
        model.addAttribute("totalPage", claims.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("claims", claims);
        return "view/claim/draft";
    }

    @GetMapping("/pendingApproval")
    public String viewPendingApproval(){
        return "view/claim/pending_approval";
    }

    @GetMapping("/paid")
    public String viewPaid(){
        return "view/claim/paid";
    }

    @GetMapping("/rejectedOrCanceled")
    public String viewRejectOrCancel(){
        return "view/claim/reject_cancel";
    }

    @GetMapping("/detail")
    public String detail(Model model
    ) {
        Optional<Claim> claim = claimService.deatil(1);
        model.addAttribute("status", "DRAFT");
        model.addAttribute("claim", claim);
        return "view/claim/detail";
    }
}
