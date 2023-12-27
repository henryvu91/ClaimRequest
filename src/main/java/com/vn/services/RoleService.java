package com.vn.services;

import com.vn.model.Role;

import java.util.List;

public interface RoleService {
    Role findById(Integer id);
    List<Role> findAll();
}
