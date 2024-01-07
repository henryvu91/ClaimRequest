package com.vn.utils;

import com.vn.dto.view.StaffViewDetailDto;
import com.vn.utils.auth.CustomUserDetail;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class CurrentUserUtils {
//    Method to get the information of current user
    public static StaffViewDetailDto getCurrentUserInfo(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetail userDetail= (CustomUserDetail) authentication.getPrincipal();
        return userDetail.getUserInfo();
    }
}
