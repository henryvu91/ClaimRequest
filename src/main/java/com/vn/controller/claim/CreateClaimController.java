package com.vn.controller.claim;

import com.vn.dto.form.ClaimCreateDto;
import com.vn.model.Claim;
import com.vn.model.Working;
import com.vn.repositories.WorkingRepository;
import com.vn.utils.Status;
import com.vn.utils.auth.CustomUserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CreateClaimController {
    @Autowired
    WorkingRepository workingRepository;
    @GetMapping("/claim/myClaim/create")
    public String createClaimUI(ModelMap modelMap){
//        Add information of user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetail userDetail= (CustomUserDetail) authentication.getPrincipal();
        modelMap.addAttribute("currentUser",userDetail.getUserInfo());

//        Search all working which user has involved
        List<Working> workings = workingRepository.findByStaffId(userDetail.getUserId());
        modelMap.addAttribute("workingList",workings);
//        Create blank claim
        Claim claim = new Claim();
        claim.setStatus(Status.DRAFT);
        modelMap.addAttribute("newClaim",claim);

        return "/view/claim/myClaim/create";
    }

    @GetMapping("/claim/myClaim/workingDetail")
    public String loadWorkingDetail(
            @RequestParam(name = "workingId")Integer workingId,
            ModelMap modelMap){
        Working working = workingRepository.findById(workingId).orElse(null);
        modelMap.addAttribute("working",working);
        return "/view/claim/myClaim/workingDetail";
    }

}
