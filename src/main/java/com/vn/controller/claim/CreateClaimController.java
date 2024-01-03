package com.vn.controller.claim;

import com.vn.dto.form.ClaimCreateDto;
import com.vn.dto.view.StaffViewDetailDto;
import com.vn.model.Claim;
import com.vn.model.Working;
import com.vn.repositories.WorkingRepository;
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
import java.util.List;

@Controller
public class CreateClaimController {
    @Autowired
    WorkingRepository workingRepository;
    @GetMapping("/claim/create")
    public String createClaimUI(ModelMap modelMap){
//        Add information of user
        StaffViewDetailDto currentUser = CurrentUserUtils.getCurrentUserInfo();
        modelMap.addAttribute("currentUser",currentUser);

//        Search all working which user has involved
        List<Working> workings = workingRepository.findByStaffId(currentUser.getId());
        modelMap.addAttribute("workingList",workings);
//        Create blank claim
        Claim claim = new Claim();
        claim.setStatus(Status.DRAFT);
        modelMap.addAttribute("newClaim",claim);

        return "/view/claim/myClaim/create";
    }

    @PostMapping("/claim/create")
    public String createClaim(
           @Validated @ModelAttribute("newClaim") Claim claim,
           BindingResult result,
           ModelMap modelMap
    ){
        if(result.hasErrors()){
            modelMap.addAttribute("message","Create new claim failed");
            return "view/claim/myClaim/create";
        }else{

        }

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