package com.vn.repositories;

import com.vn.model.Staff;
import com.vn.model.Working;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface WorkingRepository extends JpaRepository<Working, Integer> {
    //    Search working via staff id
    @Query("SELECT w FROM Working w WHERE w.staffId = :staffId")
    List<Working> findByStaffId(@Param("staffId") Integer staffId);


    List<Working> findByStaffIdAndJobRankId(Integer staffId, Integer jobRankId);

    @Query("SELECT w.staffByStaffId FROM Working w WHERE w.projectId = :projectId AND w.jobRankId = 1")
    Staff findPMByProjectId(Integer projectId);
}
