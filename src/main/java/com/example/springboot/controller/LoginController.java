package com.example.springboot.controller;

import com.example.springboot.service.UserService;
import com.example.springboot.util.redis.RedisUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @RequestMapping("/toIndex")
    public String toindex() {
        return "/dashboard";
    }

    @RequestMapping("/toLogin")
    public String toLogin() {
        return "/login";
    }

    /**
     * 登录逻辑处理
     */
    @RequestMapping("/login")
    public String login(String username, String password, Model model) {
        //1.获取Subject
        Subject subject = SecurityUtils.getSubject();
        //2.封装用户数据
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        //3.执行登录方法
        try {
            subject.login(token);
            return "redirect:/toIndex";
        } catch (UnknownAccountException e) {
            model.addAttribute("msg", "用户名不存在");
            return "login";
        } catch (IncorrectCredentialsException e) {
            model.addAttribute("msg", "密码错误");
            return "login";
        }
    }

    @RequestMapping("/noAuth")
    public String unauthorizedRole() {
        System.out.println("------没有权限-------");
        return "noAuth";
    }



}
