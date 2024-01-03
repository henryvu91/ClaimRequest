package com.vn.services.impl;

import com.vn.model.Claim;
import com.vn.model.Project;
import com.vn.model.Staff;
import com.vn.model.Working;
import com.vn.repositories.ProjectRepository;
import com.vn.repositories.WorkingRepository;
import com.vn.services.ClaimService;
import org.hibernate.jdbc.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class ClaimServiceImpl implements ClaimService {
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    WorkingRepository workingRepository;
    @Override
    public Claim save(Claim claim, BindingResult result) {
//        Get the working
        Working working = workingRepository.findById(claim.getWorkingId()).orElse(null);
        if (working==null){
            return null;
        }
//            Get project
        Project project = working.getProjectByProjectId();
        if (project==null){
            return null;
        }
//            Get project date
        LocalDate projectStartDate = project.getStartDate();
        LocalDate projectEndDate = project.getEndDate();
        LocalDate claimDate = claim.getDate();
//        Claim date must be within the duration
        if(!isInRangeDate(projectStartDate,projectEndDate,claimDate)){
            result.rejectValue("date","Claim.Create.MSG2","Claim date must be within duration of project");
            return null;
        }

//        Get the duration of staff in the project
        LocalDate startDateInProject = working.getStartDate();
        LocalDate endDateInProject = working.getEndDate();
//        Claim date must be within the duration when staff is in project
        if(!isInRangeDate(startDateInProject,endDateInProject,claimDate)){
            result.rejectValue("date","Claim.Create.MSG3","Claim date must happen when claimer is in project");
            return null;
        }
//      Validate the time of the claim
        LocalTime from = claim.getFromTime();
        LocalTime to = claim.getToTime();
        if(from.isAfter(to)){
            result.rejectValue("date","Claim.Create.MSG4","Claim date must happen when claimer is in project");
            return null;
        }

//        Check the claim is not in the same time with other claims



        return null;
    }

//    Method to check date in a range
    private boolean isInRangeDate(LocalDate startDate,LocalDate endDate,LocalDate checkDate){
        return checkDate.isEqual(startDate) || checkDate.isEqual(endDate) ||
                (checkDate.isAfter(startDate) && checkDate.isBefore(endDate));
    }

//    Method to check
}
