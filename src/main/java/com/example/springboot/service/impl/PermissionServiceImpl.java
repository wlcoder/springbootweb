package com.example.springboot.service.impl;

import com.example.springboot.entity.Permission;
import com.example.springboot.mapper.mysql.PermissionMapper;
import com.example.springboot.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public List<Permission> getAllPermission() {
        return permissionMapper.getAllPermission();
    }

    @Override
    public Permission getPermission(Long id) {
        return permissionMapper.getPermission(id);
    }

    @Override
    public void savePermission(Permission permission) {
        permissionMapper.savePermission(permission);

    }

    @Override
    public void updatePermission(Permission permission) {
        permissionMapper.updatePermission(permission);
    }

    @Override
    public void delPermission(Long id) {
        permissionMapper.delPermission(id);
    }
}
