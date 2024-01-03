package com.vn.controller;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.vn.model.Claim;
import com.vn.model.Working;
import com.vn.repositories.ClaimRepository;
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

    @Autowired
    private ClaimRepository claimRepository;



    @GetMapping("/pendingApproval")
    public String viewPendingApproval(
            Model model,
            @RequestParam(defaultValue = "1") Integer pageNo,
            @RequestParam(defaultValue = "5") Integer pageSize
    ){
        viewClaim(model, Status.PENDING, Status.PENDING, pageNo, pageSize);
        return "view/claim/pending_approval";
    }

    @GetMapping("/approvedOrPaid")
    public String viewApprovedOrPaid(
            Model model,
            @RequestParam(defaultValue = "1") Integer pageNo,
            @RequestParam(defaultValue = "5") Integer pageSize
    ){
        viewClaim(model, Status.APPROVED, Status.PAID, pageNo, pageSize);
        return "view/claim/approved_paid";
    }

    @GetMapping("/approved")
    public String viewApproved(
            Model model,
            @RequestParam(defaultValue = "1") Integer pageNo,
            @RequestParam(defaultValue = "5") Integer pageSize
    ){
        viewClaim(model, Status.APPROVED, Status.APPROVED, pageNo, pageSize);
        return "view/claim/approved";
    }

    @GetMapping("/paid")
    public String viewPaid(
            Model model,
            @RequestParam(defaultValue = "1") Integer pageNo,
            @RequestParam(defaultValue = "5") Integer pageSize
    ){
        viewClaim(model, Status.PAID, Status.PAID, pageNo, pageSize);
        return "view/claim/paid";
    }

    @GetMapping("/draft")
    public String viewDraft(
            Model model,
            @RequestParam(defaultValue = "1") Integer pageNo,
            @RequestParam(defaultValue = "5") Integer pageSize
    ){
        viewClaim(model, Status.DRAFT, Status.DRAFT, pageNo, pageSize);
        return "view/claim/draft";
    }

    @GetMapping("/detail")
    public String detail(Model model
    ) {
        Optional<Claim> claimOptional = claimRepository.findById(1);
        if (claimOptional.isPresent()){
            Claim claim = claimOptional.get();
            System.out.println(claim);
            model.addAttribute("claim", claim);

        }

        model.addAttribute("status", "DRAFT");
        return "view/claim/detail";
    }

    private void viewClaim(Model model, Status status1, Status status2, Integer pageNo, Integer pageSize) {
        Page<Claim> claims = claimService.findClaimByStatus(status1, status2, pageNo, pageSize);
        model.addAttribute("totalPage", claims.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("claims", claims);
    }
}
