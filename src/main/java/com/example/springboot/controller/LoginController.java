package com.example.springboot.controller;

import com.example.springboot.entity.User;
import com.example.springboot.service.UserService;
import com.example.springboot.util.redis.RedisUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
public class LoginController {
    @Autowired
    private UserService userService;
    @Autowired
    private RedisUtil redisUtil;


  /*  @PostMapping(value = "/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        Map<String, Object> map, HttpSession session) {
        if (!StringUtils.isEmpty(username) && !StringUtils.isEmpty(password)) {
            String pwd = Md5Util.getMD5(password);
            User user = userService.getUser(username, pwd);
            if (null != user) {
                //登陆成功到主页
                String sessionId = session.getId();
                System.out.println("=========" + sessionId);
                session.setAttribute(sessionId, username);
                //设置session失效时间（默认失效时间30分钟）
                session.setMaxInactiveInterval(60 * 60);
                //用于页面显示登录用户名
                session.setAttribute("loginUser", username);

                //sessionId 放入redis中
                redisUtil.set("sessionId", session.getId());
                //将用户保存进redis 测试
                redisUtil.set("user", user);
                System.out.println("redis中保存的user:" + redisUtil.get("user"));
//                redisUtil.del("user");
//                System.out.println("redis中保存的user:" + redisUtil.get("user"));
                return "dashboard";
            } else {
                //登陆失败
                map.put("msg", "用户名密码错误！");
                return "login";
            }
        } else {
            map.put("msg", "用户名或者密码为空！");
            return "login";
        }
    }

    @RequestMapping(value = "/loginout")
    public String loginout(HttpSession session) {
        String sessionId = session.getId();
        session.removeAttribute(sessionId);
        session.invalidate();
        return "login";
    }*/


    @RequestMapping("/toLogin")
    public String toLogin() {
        System.out.println("跳转到登录页面");
        return "login";
    }

    /**
     * 登录逻辑处理
     * org.apache.shiro.authc.pam.UnsupportedTokenException 身份令牌异常，不支持的身份令牌
     * org.apache.shiro.authc.UnknownAccountException       未知账户/没找到帐号,登录失败
     * org.apache.shiro.authc.LockedAccountException        帐号锁定
     * org.apache.shiro.authz.DisabledAccountException      用户禁用
     * org.apache.shiro.authc.ExcessiveAttemptsException    登录重试次数，超限。只允许在一段时间内允许有一定数量的认证尝试
     * org.apache.shiro.authc.ConcurrentAccessException     一个用户多次登录异常：不允许多次登录，只能登录一次 。即不允许多处登录
     * org.apache.shiro.authz.AccountException              账户异常
     * org.apache.shiro.authz.ExpiredCredentialsException   过期的凭据异常
     * org.apache.shiro.authc.IncorrectCredentialsException 错误的凭据异常
     * org.apache.shiro.authc.CredentialsException          凭据异常
     * org.apache.shiro.authc.AuthenticationException       上面异常的父类
     */
    @RequestMapping("/login")
    public String login(String username, String password, boolean rememberMe, Model model, HttpSession session) {
        //1.获取Subject
        Subject subject = SecurityUtils.getSubject();
        //2.封装用户数据
        UsernamePasswordToken token = new UsernamePasswordToken(username, password, rememberMe);
//        if (rememberMe != null && rememberMe == 1) {
//            token.setRememberMe(true);
//        } else {
//            token.setRememberMe(false);
//        }
        //3.执行登录方法
        try {
            subject.login(token);
        } catch (UnknownAccountException e) {
            model.addAttribute("msg", "用户名不存在！");
            return "login";
        } catch (IncorrectCredentialsException e) {
            model.addAttribute("msg", "密码错误！");
            return "login";
        } catch (DisabledAccountException e) {
            model.addAttribute("msg", "该用户已被禁用！");
            return "login";
        }
        //判断验证是否通过
        if (subject.isAuthenticated()) {
            //获取登录用户信息 展示在页面上
            User userInfo = (User) subject.getSession().getAttribute("userInfo");
            String sessionId = session.getId();
            session.setAttribute(sessionId, userInfo);
            session.setAttribute("loginUser", username);
            //  model.addAttribute("userInfo", userInfo);
            //修改最后登录时间
            userInfo.setLastLoginTime(new Date());
            userService.updateUser(userInfo);
            return "index";
        } else {
            token.clear();
            return "login";
        }
    }

    @RequestMapping("/noAuth")
    public String unauthorizedRole() {
        System.out.println("------没有权限-------");
        return "noAuth";
    }

}
