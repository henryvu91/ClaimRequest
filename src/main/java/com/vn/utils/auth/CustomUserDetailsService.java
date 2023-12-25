package com.vn.utils.auth;

import com.vn.model.Staff;
import com.vn.repositories.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private StaffRepository staffRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Staff byEmail = staffRepository.findByEmail(username);
        if (byEmail == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new CustomUserDetail(byEmail);

    }
}
