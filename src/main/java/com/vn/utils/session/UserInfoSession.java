package com.vn.utils.session;

import com.vn.model.Staff;
import com.vn.services.impl.StaffServiceImpl;
import jakarta.servlet.http.HttpSession;

import java.security.Principal;

public class UserInfoSession {

    public static void checkAndAddSession(Principal principal, HttpSession session, StaffServiceImpl staffService) {
        String username = principal.getName();
        Staff byUsername = staffService.findByEmail(username);

        if ((session.getAttribute("staffId") == null)) {
            session.setAttribute("staffId", byUsername.getId());
        }
        if (session.getAttribute("staffEmail") == null) {
            session.setAttribute("staffEmail", principal.getName());
        }
        if (session.getAttribute("staffName") == null) {
            session.setAttribute("staffName", byUsername.getName());
        }
        if (session.getAttribute("departmentName") == null) {
            session.setAttribute("departmentName", byUsername.getDepartmentByDepartmentId().getName());
        }

    }
}
