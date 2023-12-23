package com.vn.repositories;

import com.vn.model.RoleInProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoleInProjectRepository extends JpaRepository<RoleInProject,Integer> {
}
