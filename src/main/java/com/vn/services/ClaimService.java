package com.vn.services;

import com.vn.model.Claim;
import com.vn.utils.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Optional;

public interface ClaimService {
    Page<Claim> findClaimByStatus(Status status, Status status2, Integer pageNo, Integer pageSize);
    Claim save(Claim claim, BindingResult result);
    //Optional<Claim> deatil(Integer id);

    Claim findClaimByIdAndStaffId(Integer claimId,Integer staffId);
}
