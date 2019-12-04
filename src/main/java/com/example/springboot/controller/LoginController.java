package com.example.springboot.controller;

import com.example.springboot.entity.User;
import com.example.springboot.service.UserService;
import com.example.springboot.util.redis.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class LoginController {
    @Autowired
    private UserService userService;
    @Autowired
    private RedisUtil redisUtil;


    @PostMapping(value = "/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        Map<String, Object> map, HttpSession session) {
        if (!StringUtils.isEmpty(username) && !StringUtils.isEmpty(password)) {
            User user = userService.getUser(username, password);
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
                redisUtil.set("sessionId",session.getId());
                //将用户保存进redis 测试
                redisUtil.set("user", user);
                System.out.println("redis中保存的user:" + redisUtil.get("user"));
                redisUtil.del("user");
                System.out.println("redis中保存的user:" + redisUtil.get("user"));
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
    }
}
