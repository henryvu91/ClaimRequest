package com.vn.controller.authorization;

import com.vn.services.WorkingService;
import com.vn.utils.CurrentUserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class SecurityAdvice {
    private final WorkingService workingService;

    @Autowired
    public SecurityAdvice(WorkingService workingService) {
        this.workingService = workingService;
    }

    @ModelAttribute("hasAccess")
    public boolean addAccessAttribute(Authentication authentication) {
        String username = (authentication != null) ? authentication.getName() : null;
        return (username != null) && workingService.checkRecord(CurrentUserUtils.getCurrentUserInfo().getId());
    }
}