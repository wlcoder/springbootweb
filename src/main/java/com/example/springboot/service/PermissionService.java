package com.example.springboot.service;

import com.example.springboot.entity.Permission;

import java.util.List;

public interface PermissionService {

    List<Permission> getAllPermission();

    Permission getPermission(Long id);

    void savePermission(Permission permission);

    void updatePermission(Permission permission);

    void delPermission(Long id);
}
