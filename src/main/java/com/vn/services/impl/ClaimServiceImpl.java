package com.vn.services.impl;

import com.vn.model.Claim;
import com.vn.repositories.ClaimRepository;
import com.vn.services.ClaimService;
import com.vn.utils.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClaimServiceImpl implements ClaimService {

    @Autowired
    ClaimRepository claimRepository;

    @Override
    public Page<Claim> findClaimByStatus(Status status, Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return claimRepository.findClaimByStatus(status, pageable);
    }
}
