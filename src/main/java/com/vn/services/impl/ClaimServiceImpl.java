package com.vn.services.impl;

import com.vn.model.Claim;
import com.vn.model.Project;
import com.vn.model.Working;
import com.vn.repositories.ClaimRepository;
import com.vn.repositories.WorkingRepository;
import com.vn.services.ClaimService;
import com.vn.utils.CurrentUserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import com.vn.utils.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class ClaimServiceImpl implements ClaimService {
    @Autowired
    WorkingRepository workingRepository;

    @Autowired
    ClaimRepository claimRepository;
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
            result.rejectValue("fromTime","Claim.Create.MSG4","To time must be after From time");
            return null;
        }

//        Check the claim is not in the same time with other claims
        List<Claim> claims = claimRepository.findClaimByDateAndStaffIdAndTime(claimDate,working.getStaffId(),from,to);
        if(!claims.isEmpty()){
            result.rejectValue("fromTime","Claim.Create.MSG5","The claim is at the same time with another claim");
            return null;
        }

//        Add the audit trail
        createTrail("Created on",claim);
        return claimRepository.save(claim);
    }

//    Method to find Claim based on staffId and claimId
    @Override
    public Claim findClaimByIdAndStaffId(Integer claimId, Integer staffId) {
        return claimRepository.findClaimByIdAndStaffId(claimId,staffId);
    }

    //    Method to check date in a range
    private boolean isInRangeDate(LocalDate startDate,LocalDate endDate,LocalDate checkDate){
        if(endDate == null){
            return checkDate.isEqual(startDate) || checkDate.isAfter(startDate);
        }
        return checkDate.isEqual(startDate) || checkDate.isEqual(endDate) ||
                (checkDate.isAfter(startDate) && checkDate.isBefore(endDate));
    }

//    Method to create new audit trail
    private void createTrail(String prefixMessage,Claim claim){
        String newLine = prefixMessage + " " + LocalDateTime.now().toString() + " by "+CurrentUserUtils.getCurrentUserInfo().getName();
        String currentAuditTrail = claim.getAuditTrail();

        if(currentAuditTrail == null){
            claim.setAuditTrail(newLine);
        }else {
            currentAuditTrail = currentAuditTrail + "/n"+ newLine;
            claim.setAuditTrail(currentAuditTrail);
        }
    }
    public Page<Claim> findClaimByStatus(Status status, Status status2, Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return claimRepository.findClaimByStatus(status, status2, pageable);
    }

//    @Override
//    public Optional<Claim> deatil(Integer id) {
//        return claimRepository.findById(id);
//    }

}
