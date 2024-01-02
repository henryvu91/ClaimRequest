package com.vn.services;

import com.vn.model.Claim;
import org.springframework.validation.BindingResult;

public interface ClaimService {
//    Save new claim
    Claim save(Claim claim, BindingResult result);
}
