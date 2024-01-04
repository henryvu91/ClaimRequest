package com.vn.services;

import com.vn.dto.view.ClaimTotalDTO;
import com.vn.model.Claim;
import com.vn.utils.Status;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface ClaimService {
    Page<ClaimTotalDTO> findClaimByStatus(Status status, Status status2, Integer pageNo, Integer pageSize);

    Page<ClaimTotalDTO> findClaimByStatusAndStaffId(Integer id, Status status, Status status2, Integer pageNo, Integer pageSize);

    Optional<Claim> deatil(Integer id);

    List<ClaimTotalDTO> findByStaffIdAndStatus(Integer id, Status status, Status status2);

}
