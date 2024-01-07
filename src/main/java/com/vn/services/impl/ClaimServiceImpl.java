package com.vn.services.impl;

import com.vn.dto.form.ClaimUpdateDTO;
import com.vn.dto.view.ClaimTotalDTO;
import com.vn.dto.view.StaffViewDetailDto;
import com.vn.mapper.form.ClaimUpdateMapper;
import com.vn.mapper.view.ClaimTotalMapper;
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
import java.util.Optional;

@Service
public class ClaimServiceImpl implements ClaimService {

    @Autowired
    ClaimRepository claimRepository;

    @Autowired
    WorkingRepository workingRepository;

    @Autowired
    ClaimTotalMapper claimTotalMapper;

    @Autowired
    ClaimUpdateMapper claimUpdateMapper;
    @Override
    public Page<ClaimTotalDTO> findClaimByStatus(Status status, Status status2, Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        Page<Claim> claimByStatus = claimRepository.findClaimByStatus(status, status2, pageable);
        return claimByStatus.map(claimTotalMapper::toDto);
    }

    @Override
    public Page<ClaimTotalDTO> findClaimByStatusAndStaffId(Integer id, Status status, Status status2, Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        Page<Claim> claimByStatus = claimRepository.findClaimByStatusAndStaffId(id, status, status2, pageable);
        return claimByStatus.map(claimTotalMapper::toDto);
    }

    @Override
    public Page<ClaimTotalDTO> findClaimByPMAndStatus(Status status, Status status2, Integer staffId, Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        Page<Claim> claimByPMAndStatus = claimRepository.findClaimByPMAndStatus(status, status2, staffId, pageable);
        System.out.println(claimByPMAndStatus);
        return claimByPMAndStatus.map(claimTotalMapper::toDto);
    }

    @Override
    public Optional<Claim> detail(Integer id) {
        return claimRepository.findById(id);
    }

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

//        Check the claim is not in the same time with other claims, unless the claim status is CANCELLED or REJECTED
    List<Status> statusList = List.of(Status.CANCELLED,Status.REJECTED);
    List<Claim> claims = claimRepository.findClaimByDateAndStaffIdAndTime(claimDate,working.getStaffId(),from,to,statusList);
    if(!claims.isEmpty()){
        result.rejectValue("fromTime","Claim.Create.MSG5","The claim is at the same time with another claim");
        return null;
    }



//        Add the audit trail
    createTrail("Created on",claim);
    return claimRepository.save(claim);
}

    @Override
    public Claim update(ClaimUpdateDTO claim, BindingResult result) {
        //        Get the duration of staff in the project
        //            Get project date
        //        Old claim
        Claim old = claimRepository.findById(claim.getId()).orElse(null);
        if(old == null){
            return  null;
        }

        Project project = old.getWorkingByWorkingId().getProjectByProjectId();

        LocalDate projectStartDate = project.getStartDate();
        LocalDate projectEndDate = project.getEndDate();
        LocalDate claimDate = claim.getDate();
//        Claim date must be within the duration
        if(!isInRangeDate(projectStartDate,projectEndDate,claimDate)){
            result.rejectValue("date","Claim.Create.MSG2","Claim date must be within duration of project");
            return null;
        }

        //      Validate the time of the claim
        LocalTime from = claim.getFromTime();
        LocalTime to = claim.getToTime();
        if(from.isAfter(to)){
            result.rejectValue("fromTime","Claim.Create.MSG4","To time must be after From time");
            return null;
        }
//        Check the claim is not in the same time with other claims, unless the claim status is CANCELLED or REJECTED
        List<Status> statusList = List.of(Status.CANCELLED,Status.REJECTED);
        List<Claim> claims = claimRepository.findOtherClaimByDateAndStaffIdAndTime(claimDate,CurrentUserUtils.getCurrentUserInfo().getId(),from,to,claim.getId(),statusList);
        if(!claims.isEmpty()){
                result.rejectValue("fromTime","Claim.Create.MSG5","The claim is at the same time with another claim");
                return null;
        }


        claimUpdateMapper.partialUpdate(claim,old);
        //        Add the audit trail
        createTrail("Updated on",old);
        return claimRepository.save(old);

    }

    @Override
    public boolean cancel(Integer claimId, Integer staffId) {
//        Find the claim
        Claim claim = claimRepository.findClaimByIdAndStaffId(claimId,staffId);
        if(claim == null){
            return false;
        }

//        Check the status of claim. Can only cancel claim with status: DRAFT, PENDING
        Status status = claim.getStatus();
        if(status != Status.DRAFT && status != Status.PENDING){
            return false;
        }

        claim.setStatus(Status.CANCELLED);
        createTrail("Cancelled on",claim);
        claimRepository.save(claim);
        return true;
    }

    @Override
    public Claim review(Integer claimId) {

        StaffViewDetailDto staffViewDetailDto = CurrentUserUtils.getCurrentUserInfo();
        Integer currentStaffId = staffViewDetailDto.getId();

        return claimRepository.findClaimByIdAndPMAndStatus(claimId,Status.PENDING,currentStaffId);

    }

//    Method to cancel a claim


    //    Method to find Claim based on staffId and claimId
    @Override
    public Claim findClaimByIdAndStaffId(Integer claimId, Integer staffId) {
        Claim claim = claimRepository.findClaimByIdAndStaffId(claimId,staffId);
        if(claim == null){
            return null;
        }

        return claim;
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
            currentAuditTrail = currentAuditTrail + "\n"+ newLine;
            claim.setAuditTrail(currentAuditTrail);
        }
    }
}
