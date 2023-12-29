package com.vn.services;

import com.vn.model.Claim;
import com.vn.repositories.ClaimRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class ClaimServiceImpl implements ClaimService {
    @Autowired
    private ClaimRepository claimRepository;
    @Override
    public Claim addClaim(Claim claim) {
        return claimRepository.save(claim);
    }

    @Override
    public void deleteClaim(int id) {
        claimRepository.deleteById(id);
    }

    @Override
    public Claim editClaim(Claim claim) {
        return null;
    }

    @Override
    public List<Claim> findAllClaim(SpringDataWebProperties.Pageable pageable) {
        return null;
    }


}
