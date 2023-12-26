package com.vn.services;

import com.vn.model.Claim;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;

import java.util.List;

public interface ClaimService {
    Claim addClaim(Claim claim);
    List<Claim> findAll(SpringDataWebProperties.Pageable pageable);
}
