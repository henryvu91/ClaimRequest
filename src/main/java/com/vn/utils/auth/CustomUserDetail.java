package com.vn.utils.auth;

import com.vn.dto.view.StaffViewDetailDto;
import com.vn.mapper.view.StaffViewMapper;
import com.vn.mapper.view.StaffViewMapperImpl;
import com.vn.model.Staff;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomUserDetail implements UserDetails {
    @Serial
    private static final long serialVersionUID = 1L;
    private final Staff staff;


    private final StaffViewMapper staffViewMapper = new StaffViewMapperImpl();

    public CustomUserDetail(Staff staff) {
        this.staff = staff;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(staff.getRoleByRoleId().getName()));
        return authorities;
    }

    @Override
    public String getPassword() {
        return staff.getPassword();
    }

    @Override
    public String getUsername() {
        return staff.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public StaffViewDetailDto getUserInfo(){
        return staffViewMapper.toDto(staff);
    }
}
