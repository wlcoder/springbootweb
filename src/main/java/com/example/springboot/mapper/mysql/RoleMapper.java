package com.example.springboot.mapper.mysql;

import com.example.springboot.entity.Permission;
import com.example.springboot.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RoleMapper {

    List<Role> getAllRole();

    Role getRole(Long id);

    void saveRole(Role role);

    void updateRole(Role role);

    void delRole(Long id);

    void updataStatus(@Param("id") Long id, @Param("status") Long status);

    List<Permission> findPermissionByRoleId(Long id);
}
