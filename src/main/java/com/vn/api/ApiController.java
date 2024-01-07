package com.vn.api;

import com.vn.dto.view.JobRankDTO;
import com.vn.dto.view.StaffIdNameDto;
import com.vn.services.ClaimService;
import com.vn.services.JobRankService;
import com.vn.services.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ApiController {

    private final StaffService staffService;
    private final JobRankService jobRankService;

    private final ClaimService claimService;

    @Autowired
    public ApiController(StaffService staffService, JobRankService jobRankService, ClaimService claimService) {
        this.staffService = staffService;
        this.jobRankService = jobRankService;
        this.claimService = claimService;
    }

    @GetMapping("/search-staff")
    public List<StaffIdNameDto> searchStaff(@RequestParam String query) {
        return staffService.findByNameLike(query);
    }

    @GetMapping("/get-jobRank")
    public List<JobRankDTO> getJobRank() {
        return jobRankService.findAll();
    }

    @PostMapping("/claim/myDraft/submitClaim")
    public ResponseEntity<Map<String, String>> submitClaim(@RequestParam Integer id) {
        Map<String, String> resultMessage = new HashMap<>();
        String message = claimService.submitClaimById(id);
        resultMessage.put("message", message);
        return ResponseEntity.ok(resultMessage);
    }

}