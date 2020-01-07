package com.example.springboot.mapper.mysql;

import com.example.springboot.entity.Permission;

import java.util.List;

public interface PermissionMapper {

    List<Permission> getAllPermission();

    Permission getPermission(Long id);

    void savePermission(Permission permission);

    void updatePermission(Permission permission);

    void delPermission(Long id);

    void saveBatchPermission(List<Permission> list);

}
