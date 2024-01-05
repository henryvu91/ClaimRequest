package com.vn.controller.claim;

import com.vn.dto.form.ClaimCreateDto;
import com.vn.dto.form.ClaimUpdateDTO;
import com.vn.dto.view.StaffViewDetailDto;
import com.vn.model.Claim;
import com.vn.model.Working;
import com.vn.repositories.ClaimRepository;
import com.vn.repositories.WorkingRepository;
import com.vn.services.ClaimService;
import com.vn.services.WorkingService;
import com.vn.utils.CurrentUserUtils;
import com.vn.utils.Status;
import com.vn.utils.auth.CustomUserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
public class CreateClaimController {

    @Autowired
    WorkingService workingService;
    @Autowired
    ClaimService claimService;

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
            ModelMap modelMap
    ) {
        if (result.hasErrors()) {
            modelMap.addAttribute("message", "Create new claim failed");
            addCurrentUserAndProjectList(modelMap);
            modelMap.addAttribute("newClaim", claim);
            return "view/claim/myClaim/create";
        } else {
            Claim addedClaim = claimService.save(claim, result);
            if (addedClaim == null) {
                modelMap.addAttribute("message", "Create new claim failed");
                addCurrentUserAndProjectList(modelMap);
                LocalDate claimDate = claim.getDate();
                LocalTime from = claim.getFromTime();
                LocalTime to = claim.getToTime();

                claim.setDate(claimDate);
                claim.setFromTime(from);
                claim.setToTime(to);
                modelMap.addAttribute("newClaim", claim);

                return "view/claim/myClaim/create";
            } else {
                modelMap.addAttribute("message", "Create new claim successfully");
                return "redirect:/claim/create";
            }
        }

    }

    @GetMapping("/claim/myClaim/workingDetail")
    public String loadWorkingDetail(
            @RequestParam(name = "workingId") Integer workingId,
            ModelMap modelMap) {
        Working working = workingService.findById(workingId);
        modelMap.addAttribute("working", working);
        return "/view/claim/myClaim/workingDetail";
    }

    @GetMapping("/claim/myClaim/update")
    public String updateClaimUI(
            @RequestParam(name="claimId") Integer claimId,
            ModelMap modelMap) {
        if(claimId == null){
            modelMap.addAttribute("updateClaim", new ClaimUpdateDTO());
            return "/view/claim/myClaim/update";
        }

        return "/view/claim/myClaim/update";
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
