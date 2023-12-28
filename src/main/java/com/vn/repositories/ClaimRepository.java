package com.vn.repositories;

import com.vn.model.Claim;
import com.vn.utils.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClaimRepository extends JpaRepository<Claim, Integer> {
    List<Claim> findClaimByStatus(Status status);
}
