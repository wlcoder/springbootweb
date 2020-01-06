package com.example.springboot.util.exception;

import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class NoPermissionException {
    @ResponseBody
    @ExceptionHandler(UnauthorizedException.class)
    public String handleShiroException(Exception e) {
        System.out.println("无权限");
        return "无权限";
    }

    @ResponseBody
    @ExceptionHandler(AuthorizationException.class)
    public String AuthorizationException(Exception e) {
        return "权限认证失败";
    }
}
