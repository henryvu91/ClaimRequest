package com.vn.services.impl;

import com.vn.dto.form.ClaimApprovalDTO;
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
import jakarta.persistence.EntityNotFoundException;
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
    public Page<ClaimTotalDTO> findClaimByStatus(List<Status> statusList, Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        Page<Claim> claimByStatus = claimRepository.findClaimByStatus(statusList, pageable);
        return claimByStatus.map(claimTotalMapper::toDto);
    }

    @Override
    public Page<ClaimTotalDTO> findClaimByStatusAndStaffId(Integer id, List<Status> statusList, Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        Page<Claim> claimByStatus = claimRepository.findClaimByStatusAndStaffId(id, statusList, pageable);
        return claimByStatus.map(claimTotalMapper::toDto);
    }

    @Override
    public Page<ClaimTotalDTO> findClaimByPMAndStatus( List<Status> statusList, Integer staffId, Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        Page<Claim> claimByPMAndStatus = claimRepository.findClaimByPMAndStatus(statusList, staffId, pageable);
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
        if (working == null) {
            return null;
        }
//            Get project
        Project project = working.getProjectByProjectId();
        if (project == null) {
            return null;
        }
//            Get project date
        LocalDate projectStartDate = project.getStartDate();
        LocalDate projectEndDate = project.getEndDate();
        LocalDate claimDate = claim.getDate();
//        Claim date must be within the duration
        if (!isInRangeDate(projectStartDate, projectEndDate, claimDate)) {
            result.rejectValue("date", "Claim.Create.MSG2", "Claim date must be within duration of project");
            return null;
        }

//        Get the duration of staff in the project
        LocalDate startDateInProject = working.getStartDate();
        LocalDate endDateInProject = working.getEndDate();
//        Claim date must be within the duration when staff is in project
        if (!isInRangeDate(startDateInProject, endDateInProject, claimDate)) {
            result.rejectValue("date", "Claim.Create.MSG3", "Claim date must happen when claimer is in project");
            return null;
        }
//      Validate the time of the claim
        LocalTime from = claim.getFromTime();
        LocalTime to = claim.getToTime();
        if (from.isAfter(to)) {
            result.rejectValue("fromTime", "Claim.Create.MSG4", "To time must be after From time");
            return null;
        }

//        Check the claim is not in the same time with other claims, unless the claim status is CANCELLED or REJECTED
        List<Status> statusList = List.of(Status.CANCELLED, Status.REJECTED);
        List<Claim> claims = claimRepository.findClaimByDateAndStaffIdAndTime(claimDate, working.getStaffId(), from, to, statusList);
        if (!claims.isEmpty()) {
            result.rejectValue("fromTime", "Claim.Create.MSG5", "The claim is at the same time with another claim");
            return null;
        }


//        Add the audit trail
        createTrail("Created on", claim);
        return claimRepository.save(claim);
    }

    @Override
    public String submitClaimById(Integer id) {
        Claim claim;
        String currentContent;
        Optional<Claim> existingEntity = claimRepository.findById(id);
        if (existingEntity.isPresent()) {
            claim = existingEntity.get();
            currentContent = claim.getAuditTrail();
        } else {
            throw new EntityNotFoundException("Claim with id " + id + " not found");
        }
        claim.setStatus(Status.PENDING);
//        System.out.println("First IF: " + claim.getWorkingByWorkingId().getJobRankId());
        if (claim.getWorkingByWorkingId().getJobRankId() == 1) {
//            System.out.println(claim.getWorkingByWorkingId().getJobRankId());
            claim.setStatus(Status.APPROVED);
        }
        claim.setAuditTrail(currentContent + "\n" + "Submitted by " + CurrentUserUtils.getCurrentUserInfo().getName() + " on " + LocalDateTime.now());
        Claim result = claimRepository.save(claim);
        if (result != null) {
            return "The Claim with id " + id + " was sent successfully";
        }
        return "The Claim with id " + id + " was sent unsuccessfully";
    }

    @Override
    public Claim update(ClaimUpdateDTO claim, BindingResult result) {
        //        Get the duration of staff in the project
        //            Get project date
        //        Old claim
        Claim old = claimRepository.findById(claim.getId()).orElse(null);
        if (old == null) {
            return null;
        }

        Project project = old.getWorkingByWorkingId().getProjectByProjectId();

        LocalDate projectStartDate = project.getStartDate();
        LocalDate projectEndDate = project.getEndDate();
        LocalDate claimDate = claim.getDate();
//        Claim date must be within the duration
        if (!isInRangeDate(projectStartDate, projectEndDate, claimDate)) {
            result.rejectValue("date", "Claim.Create.MSG2", "Claim date must be within duration of project");
            return null;
        }

        //      Validate the time of the claim
        LocalTime from = claim.getFromTime();
        LocalTime to = claim.getToTime();
        if (from.isAfter(to)) {
            result.rejectValue("fromTime", "Claim.Create.MSG4", "To time must be after From time");
            return null;
        }
//        Check the claim is not in the same time with other claims, unless the claim status is CANCELLED or REJECTED
        List<Status> statusList = List.of(Status.CANCELLED, Status.REJECTED);
        List<Claim> claims = claimRepository.findOtherClaimByDateAndStaffIdAndTime(claimDate, CurrentUserUtils.getCurrentUserInfo().getId(), from, to, claim.getId(), statusList);
        if (!claims.isEmpty()) {
            result.rejectValue("fromTime", "Claim.Create.MSG5", "The claim is at the same time with another claim");
            return null;
        }


        claimUpdateMapper.partialUpdate(claim, old);
        //        Add the audit trail
        createTrail("Updated on", old);
        return claimRepository.save(old);

    }

    @Override
    public boolean cancel(Integer claimId, Integer staffId) {
//        Find the claim
        Claim claim = claimRepository.findClaimByIdAndStaffId(claimId, staffId);
        if (claim == null) {
            return false;
        }

//        Check the status of claim. Can only cancel claim with status: DRAFT, PENDING
        Status status = claim.getStatus();
        if (status != Status.DRAFT && status != Status.PENDING) {
            return false;
        }

        claim.setStatus(Status.CANCELLED);
        createTrail("Cancelled on", claim);
        claimRepository.save(claim);
        return true;
    }

    @Override
    public Claim review(Integer claimId, Status status, boolean isFinance) {

        StaffViewDetailDto staffViewDetailDto = CurrentUserUtils.getCurrentUserInfo();
        Integer currentStaffId = staffViewDetailDto.getId();
        if (isFinance) {
            String role = staffViewDetailDto.getRoleName();
            if (role.equals("ROLE_FINANCE")) {

                return claimRepository.findClaimByIdAndStatus(claimId, status);
            } else {
                return null;
            }
        }
        return claimRepository.findClaimByIdAndPMAndStatus(claimId, status, currentStaffId);
    }

//    Method to reject a claim


    @Override
    public boolean approveReturnReject(ClaimApprovalDTO claimApprovalDTO, Status statusAfter) {
        StaffViewDetailDto staffViewDetailDto = CurrentUserUtils.getCurrentUserInfo();
        Integer currentStaffId = staffViewDetailDto.getId();

//        Find the claim and check the current user is approval person
        Claim claim = claimRepository.findClaimByIdAndPMAndStatus(claimApprovalDTO.getId(), Status.PENDING, currentStaffId);
        if (claim == null) {
            return false;
        }

        claim.setStatus(statusAfter);
        String auditMessage = switch (statusAfter) {
            case REJECTED -> "Rejected on";
            case APPROVED -> "Approved on";
            case DRAFT -> "Returned on";
            default -> "Unknown action on";
        };
        createTrail(auditMessage, claim);
        claim.setRemarks(claimApprovalDTO.getRemarks());
        claimRepository.save(claim);
        return true;
    }

    @Override
    public boolean paidRejectFinance(ClaimApprovalDTO claimApprovalDTO, Status statusAfter) {
        StaffViewDetailDto staffViewDetailDto = CurrentUserUtils.getCurrentUserInfo();
        if(!staffViewDetailDto.getRoleName().equals("ROLE_FINANCE")){
            return false;
        }

//        Find the claim and check the claim is Approved
        Claim claim = claimRepository.findClaimByIdAndStatus(claimApprovalDTO.getId(),Status.APPROVED);

        if (claim == null) {
            return false;
        }

        claim.setStatus(statusAfter);
        String auditMessage = statusAfter.equals(Status.PAID)? "Paid on" : "Rejected on";
        createTrail(auditMessage, claim);
        claim.setRemarks(claimApprovalDTO.getRemarks());
        claimRepository.save(claim);
        return true;
    }

    //    Method to find Claim based on staffId and claimId
    @Override
    public Claim findClaimByIdAndStaffId(Integer claimId, Integer staffId) {
        Claim claim = claimRepository.findClaimByIdAndStaffId(claimId, staffId);
        if (claim == null) {
            return null;
        }

        return claim;
    }


    //    Method to check date in a range
    private boolean isInRangeDate(LocalDate startDate, LocalDate endDate, LocalDate checkDate) {
        if (endDate == null) {
            return checkDate.isEqual(startDate) || checkDate.isAfter(startDate);
        }
        return checkDate.isEqual(startDate) || checkDate.isEqual(endDate) ||
                (checkDate.isAfter(startDate) && checkDate.isBefore(endDate));
    }

    //    Method to create new audit trail
    private void createTrail(String prefixMessage, Claim claim) {
        String newLine = prefixMessage + " " + LocalDateTime.now().toString() + " by " + CurrentUserUtils.getCurrentUserInfo().getName();
        String currentAuditTrail = claim.getAuditTrail();

        if (currentAuditTrail == null) {
            claim.setAuditTrail(newLine);
        } else {
            currentAuditTrail = currentAuditTrail + "\n" + newLine;
            claim.setAuditTrail(currentAuditTrail);
        }
    }

}
