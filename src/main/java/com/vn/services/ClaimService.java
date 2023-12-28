package com.vn.services;

import com.vn.model.Claim;
import com.vn.utils.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface ClaimService {
    Page<Claim> findClaimByStatus(Status status, Integer pageNo);
}
