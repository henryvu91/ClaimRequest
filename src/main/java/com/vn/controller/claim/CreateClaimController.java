package com.vn.controller.claim;

import com.vn.dto.form.ClaimApprovalDTO;
import com.vn.dto.form.ClaimUpdateDTO;
import com.vn.dto.view.ClaimViewApprovalDTO;
import com.vn.dto.view.ClaimViewUpdateDTO;
import com.vn.dto.view.StaffViewDetailDto;
import com.vn.mapper.form.ClaimApprovalMapper;
import com.vn.mapper.form.ClaimUpdateMapper;
import com.vn.mapper.view.ClaimViewApprovalMapper;
import com.vn.mapper.view.ClaimViewUpdateMapper;
import com.vn.model.Claim;
import com.vn.model.Working;
import com.vn.services.ClaimService;
import com.vn.services.WorkingService;
import com.vn.utils.CurrentUserUtils;
import com.vn.utils.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class CreateClaimController {

    @Autowired
    WorkingService workingService;
    @Autowired
    ClaimService claimService;
    @Autowired
    ClaimViewUpdateMapper claimViewUpdateMapper;
    @Autowired
    ClaimUpdateMapper claimUpdateMapper;
    @Autowired
    ClaimViewApprovalMapper claimViewApprovalMapper;
    @Autowired
    ClaimApprovalMapper claimApprovalMapper;

    @GetMapping("/claim/create")
    public String createClaimUI(ModelMap modelMap) {
        addCurrentUserAndProjectList(modelMap);
//        Create blank claim
        Claim claim = new Claim();
        claim.setStatus(Status.DRAFT);
        modelMap.addAttribute("newClaim", claim);

        return "/view/claim/myClaim/create";
    }


    @PostMapping("/claim/create")
    public String createClaim(
            @Validated @ModelAttribute("newClaim") Claim claim,
            BindingResult result,
            ModelMap modelMap,
            RedirectAttributes redirectAttributes
    ) {
        if (!result.hasErrors()) {
            Claim addedClaim = claimService.save(claim, result);
            if (addedClaim != null) {
                redirectAttributes.addFlashAttribute("message", "Create new claim successfully");
                return "redirect:/claim/myDraft";
            }
        }
        modelMap.addAttribute("message", "Create new claim failed");
        addCurrentUserAndProjectList(modelMap);

        modelMap.addAttribute("newClaim", claim);

        return "view/claim/myClaim/create";

    }

    @GetMapping("/claim/myClaim/workingDetail")
    public String loadWorkingDetail(
            @RequestParam(name = "workingId") Integer workingId,
            ModelMap modelMap) {
        Working working = workingService.findById(workingId);
        modelMap.addAttribute("working", working);
        return "/view/claim/myClaim/workingDetail";
    }

    @GetMapping("/claim/update")
    public String updateClaimUI(
            @RequestParam(name = "claimId", required = false) Integer claimId,
            ModelMap modelMap) {
        //        Add information of user
        StaffViewDetailDto currentUser = CurrentUserUtils.getCurrentUserInfo();
        modelMap.addAttribute("currentUser", currentUser);
        String message;
//        Check claim id not null
        if (claimId == null) {
            message = "Cannot find the claim";
        } else {
            Integer staffId = currentUser.getId();
            Claim updateClaim = claimService.findClaimByIdAndStaffId(claimId, staffId);
//        Check claim is existed in the database
            if (updateClaim != null) {
                if (!updateClaim.getStatus().equals(Status.DRAFT)) {
                    message = "Can only update draft claim";
                } else {

//                    Create view update dto and form update dto
                    ClaimViewUpdateDTO viewUpdateDTO = claimViewUpdateMapper.toDto(updateClaim);
                    ClaimUpdateDTO updateDTO = claimUpdateMapper.toDto(updateClaim);

                    modelMap.addAttribute("viewUpdateClaim", viewUpdateDTO);
                    modelMap.addAttribute("updateClaim", updateDTO);
                    return "/view/claim/myClaim/update";
                }

            } else {
                message = "Cannot find the claim";
            }
        }

        modelMap.addAttribute("updateClaim", new ClaimUpdateDTO());
        modelMap.addAttribute("viewUpdateClaim", new ClaimViewUpdateDTO());
        modelMap.addAttribute("message", message);
        return "/view/claim/myClaim/update";
    }

    @PostMapping("/claim/update")
    public String updateClaim(
            @Validated @ModelAttribute("updateClaim") ClaimUpdateDTO claim,
            BindingResult result,
            ModelMap modelMap,
            RedirectAttributes redirectAttributes
    ) {
        if (!result.hasErrors()) {
            Claim updateClaim = claimService.update(claim, result);
            if (updateClaim != null) {
                redirectAttributes.addFlashAttribute("message", "Update claim successfully");
                return "redirect:/claim/myDraft";
            }
        }
        modelMap.addAttribute("message", "Update claim failed");

//        User info
        StaffViewDetailDto currentUser = CurrentUserUtils.getCurrentUserInfo();
        modelMap.addAttribute("currentUser", currentUser);

//        Info of claim to update
        Claim oldClaim = claimService.findClaimByIdAndStaffId(claim.getId(), currentUser.getId());
        if (oldClaim != null) {
            ClaimViewUpdateDTO viewUpdateDTO = claimViewUpdateMapper.toDto(oldClaim);
            modelMap.addAttribute("viewUpdateClaim", viewUpdateDTO);
        } else {
            modelMap.addAttribute("viewUpdateClaim", new ClaimViewUpdateDTO());
        }

        modelMap.addAttribute("updateClaim", claim);

        return "view/claim/myClaim/update";

    }

    @GetMapping("/claim/cancel")
    public String cancelClaim(
            @RequestParam(name = "claimId", required = false) Integer claimId,
            ModelMap modelMap,
            RedirectAttributes redirectAttributes) {
        //        Add information of user
        StaffViewDetailDto currentUser = CurrentUserUtils.getCurrentUserInfo();
//        Check claim id not null
        if (claimId != null) {
            Integer staffId = currentUser.getId();

            boolean isCancelled = claimService.cancel(claimId, staffId);

//        Check claim is cancelled
            if (isCancelled) {
                redirectAttributes.addFlashAttribute("message", "Canceled the claim successfully");
                return "redirect:/claim/myDraft";
            }
        }

        modelMap.addAttribute("message", "Canceled the claim failed");
        return "/view/claim/myClaim";
    }

    @GetMapping("/claim/review")
    public String reviewClaimUI(
            @RequestParam(name = "claimId", required = false) Integer claimId,
            ModelMap modelMap) {
//        //        Add information of user
//        StaffViewDetailDto currentUser = CurrentUserUtils.getCurrentUserInfo();
//        modelMap.addAttribute("currentUser", currentUser);
        String message;
//        Check claim id not null
        if (claimId == null) {
            message = "Cannot find the claim";
        } else {

            Claim reviewClaim = claimService.review(claimId);
//        Check claim is existed in the database
            if (reviewClaim != null) {

//                    Create view update dto and form update dto
                ClaimViewApprovalDTO viewApprovalDTO = claimViewApprovalMapper.toDto(reviewClaim);
                ClaimApprovalDTO approvalDTO = claimApprovalMapper.toDto(reviewClaim);

                modelMap.addAttribute("viewApprovalClaim", viewApprovalDTO);
                modelMap.addAttribute("approvalClaim", approvalDTO);
                return "/view/claim/approval/review";
            } else {
                message = "Cannot find the claim";
            }
        }

        modelMap.addAttribute("viewApprovalClaim", new ClaimViewApprovalDTO());
        modelMap.addAttribute("approvalClaim", new ClaimApprovalDTO());
        modelMap.addAttribute("message", message);
        return "/view/claim/approval/review";
    }

    private void addCurrentUserAndProjectList(ModelMap modelMap) {
        //        Add information of user
        StaffViewDetailDto currentUser = CurrentUserUtils.getCurrentUserInfo();
        modelMap.addAttribute("currentUser", currentUser);

//        Search all working which user has involved
        List<Working> workings = workingService.findByStaffId(currentUser.getId());
        modelMap.addAttribute("workingList", workings);
    }
}
