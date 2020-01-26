package com.example.springboot.service.impl;

import com.example.springboot.entity.Permission;
import com.example.springboot.entity.Role;
import com.example.springboot.entity.Rolepermission;
import com.example.springboot.mapper.mysql.RoleMapper;
import com.example.springboot.mapper.mysql.RolepermissionMapper;
import com.example.springboot.service.RoleService;
import com.example.springboot.util.exception.BaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RolepermissionMapper rolepermissionMapper;

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
        //删除角色时删除角色对应的权限
        rolepermissionMapper.delByRoleId(id);
    }

    @Override
    public void updataStatus(Long id, Long status) {
        roleMapper.updataStatus(id, status);
    }

    @Override
    public List<Permission> findPermissionByRoleId(Long id) {
        return roleMapper.findPermissionByRoleId(id);
    }

    @Override
    public void saveRolepermission(Rolepermission rolepermission) {
        if (null == rolepermission) {
            throw new BaseException("信息为空");
        }
        // 先删除之前拥有的权限
        List<Rolepermission> rp = rolepermissionMapper.findPermissionByRoleId(rolepermission.getRid());
        if (null != rp && rp.size() > 0) {
            rolepermissionMapper.delByRoleId(rolepermission.getRid());
        }
        //再添加权限
        if (null != rolepermission.getPids() && rolepermission.getPids().length > 0) {
            Long[] pids = rolepermission.getPids();
            for (int i = 0; i < pids.length; i++) {
                rolepermission.setPid(pids[i]);
                rolepermissionMapper.saveRolepermission(rolepermission);
            }
        }
    }
}
