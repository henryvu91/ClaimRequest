package com.vn.utils.session;

import com.vn.model.Staff;
import com.vn.services.impl.StaffServiceImpl;
import jakarta.servlet.http.HttpSession;

import java.security.Principal;

public class UserInfoSession {

    public static void checkAndAddSession(Principal principal, HttpSession session, StaffServiceImpl staffService) {

        if (session.getAttribute("staffId") == null || session.getAttribute("staffEmail") == null || session.getAttribute("staffName") == null || session.getAttribute("departmentName") == null) {

            Staff staff = staffService.findByEmail(principal.getName());

            if (staff != null) {
                session.setAttribute("staffEmail", principal.getName());
                session.setAttribute("staffId", staff.getId());
                session.setAttribute("staffName", staff.getName());
                session.setAttribute("departmentName", staff.getDepartmentByDepartmentId().getName());
            }
        }
    }
}
