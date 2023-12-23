package com.vn.repositories;

import com.vn.model.Staff;
import com.vn.model.Working;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface WorkingRepository extends JpaRepository<Working,Integer> {
}
