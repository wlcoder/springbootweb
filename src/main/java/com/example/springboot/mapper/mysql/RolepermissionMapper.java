package com.example.springboot.mapper.mysql;

import com.example.springboot.entity.Rolepermission;

import java.util.List;

public interface RolepermissionMapper {

    List<Rolepermission> findAllRolepermission();

    List<Rolepermission> findPermissionByRoleId(Long id);

    void saveRolepermission(Rolepermission rolepermission);

    void delByRoleId(Long id);

}
