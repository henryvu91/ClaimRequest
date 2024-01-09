package com.vn.repositories;

import com.vn.model.Claim;
import com.vn.model.Staff;
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

    @Query("SELECT c FROM Claim c WHERE c.status IN :statusList")
    Page<Claim> findClaimByStatus(List<Status> statusList, Pageable pageable);

    //@Query(value = "SELECT c.* FROM claim c JOIN working w ON c.working_id = w.id WHERE c.status= :status AND w.project_id IN (SELECT w1.project_id FROM working w1 WHERE w1.staff_id = :staffId AND w1.job_rank_id = 1)", nativeQuery = true)
    @Query("""
        SELECT c FROM Claim c
        WHERE c.status IN :statusList
        AND c.workingByWorkingId.projectId IN
                (SELECT w.projectId FROM Working w
                WHERE w.staffId = :staffId AND w.jobRankId = 1)
""")
    Page<Claim> findClaimByPMAndStatus(List<Status> statusList, Integer staffId, Pageable pageable);

    @Query("""                                                                             
        SELECT c FROM Claim c
        WHERE
        c.id = :id AND
        c.status = :status  AND
        c.workingByWorkingId.projectId IN
                (SELECT w.projectId FROM Working w
                WHERE w.staffId = :staffId AND w.jobRankId = 1)
""")
    Claim findClaimByIdAndPMAndStatus(Integer id, Status status, Integer staffId);

    @Query("SELECT c FROM Claim c WHERE c.workingByWorkingId.staffByStaffId.id = :id AND c.status IN :statusList")
    Page<Claim> findClaimByStatusAndStaffId(Integer id, List<Status> statusList, Pageable pageable);
    @Query("""
        SELECT c FROM Claim c
        WHERE
        c.id = :id AND
        c.status = :status
""")
    Claim findClaimByIdAndStatus(Integer id, Status status);

    @Query("SELECT c FROM Claim c WHERE c.workingByWorkingId.staffByStaffId.id = :id AND c.status IN (:status, :status2)")
    Page<Claim> findClaimByStatusAndStaffId(Integer id, Status status, Status status2, Pageable pageable);

    //    Check any claims at the same time

    @Query("""
            SELECT c
            FROM Claim c
            WHERE c.date = :claimDate
            AND c.status NOT IN :statusList
            AND c.workingByWorkingId.staffId = :staffId
            AND ((c.fromTime BETWEEN :fromTime AND :toTime) OR (c.toTime BETWEEN :fromTime AND :toTime))""")
    List<Claim> findClaimByDateAndStaffIdAndTime(LocalDate claimDate, Integer staffId, LocalTime fromTime, LocalTime toTime,List<Status> statusList);

    @Query("""
            SELECT c
            FROM Claim c
            WHERE c.id != :claimId
            AND c.status NOT IN :statusList
            AND c.date = :claimDate
            AND c.workingByWorkingId.staffId = :staffId
            AND ((c.fromTime BETWEEN :fromTime AND :toTime) OR (c.toTime BETWEEN :fromTime AND :toTime))""")
    List<Claim> findOtherClaimByDateAndStaffIdAndTime(LocalDate claimDate, Integer staffId, LocalTime fromTime, LocalTime toTime,Integer claimId,List<Status> statusList);

    //    Find claim based on staffId and claimId
    @Query("SELECT c FROM Claim c WHERE c.id = :claimId AND c.workingByWorkingId.staffId = :staffId")
    Claim findClaimByIdAndStaffId(Integer claimId,Integer staffId);

    @Query("SELECT c.workingByWorkingId.projectId FROM Claim c WHERE c.id = :id")
    Integer findProjectIdByClaimId(Integer id);

    @Query("SELECT c.workingByWorkingId.staffByStaffId FROM Claim c WHERE c.id = :id AND c.workingByWorkingId.jobRankId = 1")
    Staff findPMByClaimId(Integer id);

    @Query("SELECT c.workingByWorkingId.staffByStaffId FROM Claim c WHERE c.id = :id")
    Staff findStaffByClaimId(Integer id);

}
