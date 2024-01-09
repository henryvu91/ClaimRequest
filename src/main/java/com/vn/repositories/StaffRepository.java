package com.vn.repositories;

import com.vn.dto.view.StaffIdNameDto;
import com.vn.dto.view.StaffViewDetailDto;
import com.vn.model.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface StaffRepository extends JpaRepository<Staff, Integer> {
    Staff findByEmail(String email);

    //    Check email is existed
    boolean existsByEmail(String email);

    @Query("SELECT new com.vn.dto.view.StaffIdNameDto(s.id,s.name) FROM Staff s")
    List<StaffIdNameDto> findAllStaffName();

//    Get the detail information of staff

    @Query("SELECT new com.vn.dto.view.StaffViewDetailDto(s.id, s.email, s.name, s.salary, s.departmentByDepartmentId.name, s.roleByRoleId.name) FROM Staff s WHERE s.id=:id")
    StaffViewDetailDto findStaffViewDetailById(@Param("id") Integer id);

    List<StaffIdNameDto> findByNameLike(String query);

    List<Staff> findStaffByRoleId(Integer id);
}
