package com.vn.repositories;

import com.vn.model.Claim;
import com.vn.utils.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface ClaimRepository extends JpaRepository<Claim, Integer> {
    List<Claim> findClaimByStatus(Status status);

//    Check any claims at the same time

    @Query("""
            SELECT c 
            FROM Claim c 
            WHERE c.date = :claimDate 
            AND c.workingByWorkingId.staffId = :staffId 
            AND ((c.fromTime BETWEEN :fromTime AND :toTime) OR (c.toTime BETWEEN :fromTime AND :toTime))""")
    List<Claim> findClaimByDateAndStaffIdAndTime(LocalDate claimDate,Integer staffId, LocalTime fromTime,LocalTime toTime);

    @Query("SELECT c FROM Claim c WHERE c.status IN (:status, :status2)")
    Page<Claim> findClaimByStatus(Status status, Status status2, Pageable pageable);

//    Find claim based on staffId and claimId
    @Query("SELECT c FROM Claim c WHERE c.id = :claimId AND c.workingByWorkingId.staffId = :staffId")
    Claim findClaimByIdAndStaffId(Integer claimId,Integer staffId);

}
