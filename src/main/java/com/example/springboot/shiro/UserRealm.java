package com.example.springboot.shiro;

import com.example.springboot.entity.Permission;
import com.example.springboot.entity.Rolepermission;
import com.example.springboot.entity.User;
import com.example.springboot.entity.Userrole;
import com.example.springboot.service.PermissionService;
import com.example.springboot.service.RoleService;
import com.example.springboot.service.UserService;
import com.example.springboot.util.md5.Md5Util;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;

    /**
     * 执行授权逻辑
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行授权逻辑");
        //给资源进行授权
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        User user = (User) principalCollection.getPrimaryPrincipal();
        if (null != user) {
            List<Userrole> userroleList = userService.findRoleByUserId(user.getId());
            if (null != userroleList && userroleList.size() > 0) {
                for (Userrole userrole : userroleList) {
                    List<Rolepermission> rolepermissionList = roleService.findPermissionByRoleId(userrole.getRid());
                    if (null != rolepermissionList && rolepermissionList.size() > 0) {
                        for (Rolepermission rolepermission : rolepermissionList) {
                            Permission permission = permissionService.getPermission(rolepermission.getPid());
                            if (null != permission) {
                                info.addStringPermission(permission.getUrl());
                            }
                        }
                    }
                }
            }
        }
        return info;
    }

    /**
     * 执行认证逻辑
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("执行认证逻辑");
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String password = new String((char[]) token.getPassword());
        token.setPassword(Md5Util.getMD5(password).toCharArray());
        User user = userService.getUserByUsername(token.getUsername());
        if (null == user) {
            throw new AuthenticationException();
        }
        if (null != user && user.getStatus() == 0) {
            throw new DisabledAccountException();
        }
        return new SimpleAuthenticationInfo(user, user.getPassword(), "");
    }
}
