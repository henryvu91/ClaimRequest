package com.vn.services.impl;

import com.vn.dto.view.ClaimTotalDTO;
import com.vn.mapper.view.ClaimTotalMapper;
import com.vn.model.Claim;
import com.vn.model.Working;
import com.vn.repositories.ClaimRepository;
import com.vn.repositories.WorkingRepository;
import com.vn.services.ClaimService;
import com.vn.utils.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public List<ClaimTotalDTO> findByStaffIdAndStatus(Integer id, Status status, Status status2) {
        List<Working> byStaffId = workingRepository.findByStaffId(id);
        List<ClaimTotalDTO> claimDTOList = new ArrayList<>();
        for (Working working : byStaffId) {
            List<Claim> claimsById = working.getClaimsById();
            for (Claim claim : claimsById) {
                if (claim.getStatus() == status || claim.getStatus() == status2) {
                    claimDTOList.add(claimTotalMapper.toDto(claim));
                }
            }
        }
        return claimDTOList;
    }

    @Override
    public Optional<Claim> deatil(Integer id) {
        return claimRepository.findById(id);
    }

}
