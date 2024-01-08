package com.vn.controller;

import com.vn.dto.form.ClaimApprovalDTO;
import com.vn.dto.view.ClaimTotalDTO;
import com.vn.dto.view.ClaimViewApprovalDTO;
import com.vn.mapper.form.ClaimApprovalMapper;
import com.vn.mapper.view.ClaimViewApprovalMapper;
import com.vn.model.Claim;
import com.vn.services.ClaimService;
import com.vn.utils.CurrentUserUtils;
import com.vn.utils.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
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
            @RequestParam(defaultValue = "10") Integer pageSize
    ){
        List<Status> statusList = List.of(Status.PENDING);
        claimPM(model, statusList, pageNo, pageSize,"Claims For My Vetting");
        return "view/claim/for_approval";
    }

    @GetMapping("/approvedOrPaid")
    public String viewApprovedOrPaid(
            Model model,
            @RequestParam(defaultValue = "1") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize
    ){
        List<Status> statusList = List.of(Status.APPROVED, Status.PAID);
        claimPM(model, statusList, pageNo, pageSize,"Approved Or Paid Claims");
        return "view/claim/for_approval";
    }

    @GetMapping("/approved")
    public String viewApproved(
            Model model,
            @RequestParam(defaultValue = "1") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize
    ){
        List<Status> statusList = List.of(Status.APPROVED);
        viewClaim(model, statusList, pageNo, pageSize,"Approved Claims");
        return "view/claim/for_finance";
    }

    @GetMapping("/paid")
    public String viewPaid(
            Model model,
            @RequestParam(defaultValue = "1") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize
    ){
        List<Status> statusList = List.of(Status.PAID);
        viewClaim(model, statusList, pageNo, pageSize,"Paid Claims");
        return "view/claim/for_finance";
    }

    @GetMapping("/myDraft")
    public String myDraft(
            Model model,
            @RequestParam(defaultValue = "1") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize
    ){
        List<Status> statusList = List.of(Status.DRAFT);
        myClaim(model, statusList, pageNo, pageSize,"My Draft Claims");
        return "view/claim/myClaim";
    }

    @GetMapping("/myPending")
    public String myPending(
            Model model,
            @RequestParam(defaultValue = "1") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize
    ){
        List<Status> statusList = List.of(Status.PENDING);
        myClaim(model,statusList, pageNo, pageSize,"My Pending Claims");
        return "view/claim/myClaim";
    }

    @GetMapping("/myApproved")
    public String myApproved(
            Model model,
            @RequestParam(defaultValue = "1") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize
    ){
        List<Status> statusList = List.of(Status.APPROVED);
        myClaim(model, statusList, pageNo, pageSize, "My Approved Claims");
        return "view/claim/myClaim";
    }

    @GetMapping("/myPaid")
    public String myPaid(
            Model model,
            @RequestParam(defaultValue = "1") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize
    ){
        List<Status> statusList = List.of(Status.PAID);
        myClaim(model,statusList, pageNo, pageSize,"My Paid Claims");
        return "view/claim/myClaim";
    }

    @GetMapping("/myRejectOrCancel")
    public String myRejectOrCancel(
            Model model,
            @RequestParam(defaultValue = "1") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize
    ){
        List<Status> statusList = List.of( Status.REJECTED, Status.CANCELLED);
        myClaim(model,statusList, pageNo, pageSize,"My Rejected Or Cancelled Claims");
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

    private void viewClaim(Model model, List<Status> statusList, Integer pageNo, Integer pageSize,String titlePage) {
        Page<ClaimTotalDTO> claims = claimService.findClaimByStatus(statusList, pageNo, pageSize);
        model.addAttribute("totalPage", claims.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("claims", claims);
        model.addAttribute("titlePage", titlePage);

    }

    private void claimPM(Model model, List<Status> statusList, Integer pageNo, Integer pageSize,String titlePage) {
        Integer staffId = CurrentUserUtils.getCurrentUserInfo().getId();
        Page<ClaimTotalDTO> claims = claimService.findClaimByPMAndStatus(statusList, staffId, pageNo, pageSize);
        model.addAttribute("totalPage", claims.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("claims", claims);
        model.addAttribute("titlePage", titlePage);
    }

    private void myClaim(Model model, List<Status> statusList, Integer pageNo, Integer pageSize,String titlePage) {
        Integer id = CurrentUserUtils.getCurrentUserInfo().getId();
        Page<ClaimTotalDTO> claimTotalDTOList = claimService.findClaimByStatusAndStaffId(id, statusList, pageNo, pageSize);
        model.addAttribute("totalPage", claimTotalDTOList.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("claims", claimTotalDTOList);
        model.addAttribute("titlePage", titlePage);
    }
}
