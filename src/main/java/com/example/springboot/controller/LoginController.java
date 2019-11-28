package com.example.springboot.controller;

import com.example.springboot.entity.User;
import com.example.springboot.service.UserService;
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

    @PostMapping(value = "/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        Map<String, Object> map, HttpSession session) {
        if (!StringUtils.isEmpty(username) && !StringUtils.isEmpty(password)) {
            User user = userService.getUser(username, password);
            if (null != user) {
                //登陆成功，防止表单重复提交，可以重定向到主页
                session.setAttribute("loginUser", username);
                return "redirect:/main.html";
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
}
