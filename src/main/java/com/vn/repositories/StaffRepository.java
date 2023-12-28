package com.vn.repositories;

import com.vn.dto.StaffIdNameDto;
import com.vn.dto.StaffIdNameDto2;
import com.vn.dto.StaffViewDetailDto;
import com.vn.model.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface StaffRepository extends JpaRepository<Staff, Integer> {
    Staff findByEmail(String email);

//    Check email is existed
    boolean existsByEmail(String email);

    @Query("SELECT s.id,s.name FROM Staff s")
    List<StaffIdNameDto2> findAllStaffName();

//    Get the detail information of staff

    @Query("SELECT s.id, s.email, s.name, s.salary, s.departmentByDepartmentId.name, s.roleByRoleId.name FROM Staff s WHERE s.id=:id")
    StaffViewDetailDto findStaffViewDetailById(Integer id);
}
