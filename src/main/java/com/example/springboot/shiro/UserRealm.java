package com.example.springboot.shiro;

import com.example.springboot.entity.Permission;
import com.example.springboot.entity.Role;
import com.example.springboot.entity.User;
import com.example.springboot.service.RoleService;
import com.example.springboot.service.UserService;
import com.example.springboot.util.md5.Md5Util;
import org.apache.shiro.SecurityUtils;
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
            List<Role> roleList = userService.findRoleByUserId(user.getId());
            if (null != roleList && roleList.size() > 0) {
                for (Role role : roleList) {
                    info.addRole(role.getName());
                    List<Permission> permissionList = roleService.findPermissionByRoleId(role.getId());
                    if (null != permissionList && permissionList.size() > 0) {
                        for (Permission permission : permissionList) {
                            info.addStringPermission(permission.getUrl());
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
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user, user.getPassword(), getName());
        //验证成功 把用户信息保存在session中
        SecurityUtils.getSubject().getSession().setAttribute("userInfo", user);
        SecurityUtils.getSubject().getSession().setTimeout(30 * 60 * 1000);
        return authenticationInfo;
    }
}
