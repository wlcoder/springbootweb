package com.example.springboot.shiro;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

public class UserRealm extends AuthorizingRealm {
    /**
     * 执行授权逻辑
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行授权逻辑");
        //给资源进行授权
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //添加资源的授权
//        info.addStringPermission("user:add");
//        info.addStringPermission("user:update");
        return info;
    }

    /**
     * 执行认证逻辑
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("执行认证逻辑");
        String name = "wl";
        String password = "123456";
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        if (!token.getUsername().equals(name)) {
            return null;
        }
        //2.判断密码
        return new SimpleAuthenticationInfo("", password, "");
    }
}
