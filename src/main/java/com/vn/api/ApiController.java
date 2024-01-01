package com.vn.api;

import com.vn.dto.view.JobRankDTO;
import com.vn.dto.view.StaffIdNameDto;
import com.vn.services.JobRankService;
import com.vn.services.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {

    private final StaffService staffService;
    private final JobRankService jobRankService;

    @Autowired
    public ApiController(StaffService staffService, JobRankService jobRankService) {
        this.staffService = staffService;
        this.jobRankService = jobRankService;
    }

    @GetMapping("/search-staff")
    public List<StaffIdNameDto> searchStaff(@RequestParam String query) {
        return staffService.findByNameLike(query);
    }

    @GetMapping("/get-jobRank")
    public List<JobRankDTO> getJobRank() {
        return jobRankService.findAll();
    }
}