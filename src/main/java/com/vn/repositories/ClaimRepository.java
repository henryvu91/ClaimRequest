package com.vn.repositories;

import com.vn.dto.view.ClaimTotalDTO;
import com.vn.model.Claim;
import com.vn.utils.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClaimRepository extends JpaRepository<Claim, Integer> {

    @Query("SELECT c FROM Claim c WHERE c.status IN (:status, :status2)")
    Page<Claim> findClaimByStatus(Status status, Status status2, Pageable pageable);

    @Query("SELECT c FROM Claim c WHERE c.workingByWorkingId.staffByStaffId.id = :id AND c.status IN (:status, :status2)")
    Page<Claim> findClaimByStatusAndStaffId(Integer id, Status status, Status status2, Pageable pageable);

}
