package com.vn.services.impl;

import com.vn.model.Claim;
import com.vn.services.ClaimService;
import org.springframework.validation.BindingResult;

public class ClaimServiceImpl implements ClaimService {
    @Override
    public Claim save(Claim claim, BindingResult result) {
//        Claim date must be within the duration

        return null;
    }
}
