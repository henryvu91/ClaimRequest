package com.vn.services;

import com.vn.model.Claim;
import com.vn.utils.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public interface ClaimService {
    Page<Claim> findClaimByStatus(Status status, Integer pageNo, Integer pageSize);
    Optional<Claim> deatil(Integer id);
}
