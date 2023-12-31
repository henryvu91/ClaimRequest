package com.vn.utils.session;

import com.vn.model.Staff;
import com.vn.services.impl.StaffServiceImpl;
import jakarta.servlet.http.HttpSession;

import java.security.Principal;

public class IdAndUsernameSession {

    public static void checkAndAddSession(Principal principal, HttpSession session, StaffServiceImpl staffService) {
        String username = principal.getName();

        if ((session.getAttribute("userId") == null)) {
            Staff byUsername = staffService.findByEmail(username);
            session.setAttribute("userId", byUsername.getId());
        }
        if (session.getAttribute("username") == null) {
            session.setAttribute("username", principal.getName());
        }

    }
}
