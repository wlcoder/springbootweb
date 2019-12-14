package com.example.springboot.service.impl;

import com.example.springboot.entity.Role;
import com.example.springboot.mapper.mysql.RoleMapper;
import com.example.springboot.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<Role> getAllRole() {
        return roleMapper.getAllRole();
    }

    @Override
    public Role getRole(Long id) {
        return roleMapper.getRole(id);
    }

    @Override
    public void saveRole(Role role) {
        roleMapper.saveRole(role);
    }

    @Override
    public void updateRole(Role role) {
        roleMapper.updateRole(role);

    }

    @Override
    public void delRole(Long id) {
        roleMapper.delRole(id);
    }

    @Override
    public void updataStatus(Long id, Long status) {
        roleMapper.updataStatus(id, status);
    }
}
