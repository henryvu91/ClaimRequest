package com.vn.services;

import com.vn.dto.view.ClaimTotalDTO;
import com.vn.dto.form.ClaimUpdateDTO;
import com.vn.model.Claim;
import com.vn.utils.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Optional;

public interface ClaimService {
    Page<ClaimTotalDTO> findClaimByStatus(Status status, Status status2, Integer pageNo, Integer pageSize);

    Page<ClaimTotalDTO> findClaimByStatusAndStaffId(Integer id, Status status, Status status2, Integer pageNo, Integer pageSize);

    Page<ClaimTotalDTO> findClaimByPMAndStatus(Status status, Status status2, Integer staffId, Integer pageNo, Integer pageSize);

    Optional<Claim> detail(Integer id);

    Claim findClaimByIdAndStaffId(Integer claimId, Integer staffId);

    Claim save(Claim claim, BindingResult result);

    Claim update(ClaimUpdateDTO claim, BindingResult result);

    boolean cancel(Integer claimId, Integer staffId);
}
