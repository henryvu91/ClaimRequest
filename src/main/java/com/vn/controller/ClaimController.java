package com.vn.controller;

import com.vn.dto.view.ClaimTotalDTO;
import com.vn.model.Claim;
import com.vn.services.ClaimService;
import com.vn.utils.CurrentUserUtils;
import com.vn.utils.Status;
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

    @GetMapping("/pendingApproval")
    public String viewPendingApproval(
            Model model,
            @RequestParam(defaultValue = "1") Integer pageNo,
            @RequestParam(defaultValue = "5") Integer pageSize
    ){
        claimPM(model, Status.APPROVED, Status.APPROVED, pageNo, pageSize);
        return "view/claim/for_approval";
    }

    @GetMapping("/approvedOrPaid")
    public String viewApprovedOrPaid(
            Model model,
            @RequestParam(defaultValue = "1") Integer pageNo,
            @RequestParam(defaultValue = "5") Integer pageSize
    ){
        claimPM(model, Status.APPROVED, Status.PAID, pageNo, pageSize);
        return "view/claim/for_approval";
    }

    @GetMapping("/approved")
    public String viewApproved(
            Model model,
            @RequestParam(defaultValue = "1") Integer pageNo,
            @RequestParam(defaultValue = "5") Integer pageSize
    ){
        viewClaim(model, Status.APPROVED, Status.APPROVED, pageNo, pageSize);
        return "view/claim/for_finance";
    }

    @GetMapping("/paid")
    public String viewPaid(
            Model model,
            @RequestParam(defaultValue = "1") Integer pageNo,
            @RequestParam(defaultValue = "5") Integer pageSize
    ){
        viewClaim(model, Status.PAID, Status.PAID, pageNo, pageSize);
        return "view/claim/for_finance";
    }

    @GetMapping("/myDraft")
    public String myDraft(
            Model model,
            @RequestParam(defaultValue = "1") Integer pageNo,
            @RequestParam(defaultValue = "2") Integer pageSize
    ){
        myClaim(model, Status.DRAFT, Status.DRAFT, pageNo, pageSize);
        return "view/claim/myClaim";
    }

    @GetMapping("/myPending")
    public String myPending(
            Model model,
            @RequestParam(defaultValue = "1") Integer pageNo,
            @RequestParam(defaultValue = "2") Integer pageSize
    ){
        myClaim(model, Status.PENDING, Status.PENDING, pageNo, pageSize);
        return "view/claim/myClaim";
    }

    @GetMapping("/myApproved")
    public String myApproved(
            Model model,
            @RequestParam(defaultValue = "1") Integer pageNo,
            @RequestParam(defaultValue = "2") Integer pageSize
    ){
        myClaim(model, Status.APPROVED, Status.APPROVED, pageNo, pageSize);
        return "view/claim/myClaim";
    }

    @GetMapping("/myPaid")
    public String myPaid(
            Model model,
            @RequestParam(defaultValue = "1") Integer pageNo,
            @RequestParam(defaultValue = "2") Integer pageSize
    ){
        myClaim(model, Status.PAID, Status.PAID, pageNo, pageSize);
        return "view/claim/myClaim";
    }

    @GetMapping("/myRejectOrCancel")
    public String myRejectOrCancel(
            Model model,
            @RequestParam(defaultValue = "1") Integer pageNo,
            @RequestParam(defaultValue = "2") Integer pageSize
    ){
        myClaim(model, Status.REJECTED, Status.CANCELLED, pageNo, pageSize);
        return "view/claim/myClaim";
    }

    @GetMapping("/detail")
    public String detail(Model model, @RequestParam Integer id
    ) {
        Optional<Claim> claimOptional = claimService.detail(id);
        if (claimOptional.isPresent()){
            Claim claim = claimOptional.get();
            model.addAttribute("claim", claim);
        }
        return "view/claim/detail";
    }

    private void viewClaim(Model model, Status status1, Status status2, Integer pageNo, Integer pageSize) {
        Page<ClaimTotalDTO> claims = claimService.findClaimByStatus(status1, status2, pageNo, pageSize);
        model.addAttribute("totalPage", claims.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("claims", claims);
    }

    private void claimPM(Model model, Status status1, Status status2, Integer pageNo, Integer pageSize) {
        Integer staffId = CurrentUserUtils.getCurrentUserInfo().getId();
        Page<ClaimTotalDTO> claims = claimService.findClaimByPMAndStatus(status1, status2, staffId, pageNo, pageSize);
        model.addAttribute("totalPage", claims.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("claims", claims);
    }

    private void myClaim(Model model, Status status1, Status status2, Integer pageNo, Integer pageSize) {
        Integer id = CurrentUserUtils.getCurrentUserInfo().getId();
        Page<ClaimTotalDTO> claimTotalDTOList = claimService.findClaimByStatusAndStaffId(id, status1, status2, pageNo, pageSize);
        model.addAttribute("totalPage", claimTotalDTOList.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("claims", claimTotalDTOList);
    }
}
