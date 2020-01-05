package com.example.springboot.service;

import com.example.springboot.entity.Permission;
import com.example.springboot.entity.Role;
import com.example.springboot.entity.Rolepermission;

import java.util.List;

public interface RoleService {

    List<Role> getAllRole();

    Role getRole(Long id);

    void saveRole(Role role);

    void updateRole(Role role);

    void delRole(Long id);

    void updataStatus(Long id, Long status);

    List<Permission> findPermissionByRoleId(Long id);

    void saveRolepermission(Rolepermission rolepermission);
}
