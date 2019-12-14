package com.example.springboot.controller;

import com.example.springboot.entity.User;
import com.example.springboot.service.UserService;
import com.example.springboot.util.exception.BaseException;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    private UserService userService;

    /*
     * 查询用户
     */
    @RequestMapping(value = "/queryUser")
    public String queryUser(Model model, @RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum) {
        Page page = PageHelper.startPage(pageNum, 5, "id desc");
        List<User> userList = userService.getAllUser();
        PageInfo pageInfo = new PageInfo<>(page.getResult());
        model.addAttribute("pageInfo", pageInfo);
        return "user/list";
    }

    /*
     * 新增用户
     */
    @ResponseBody
    @RequestMapping(value = "/addUser")
    public Map addUser(@RequestBody User user, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        try {
            if (null == user) {
                throw new BaseException("用户信息为空！");
            }
            String ip = request.getRemoteAddr();
            // String ip1 = request.getHeader("X-Forwarded-For");通过代理获取ip
            if (!StringUtils.isEmpty(ip) && "0:0:0:0:0:0:0:1".equals(ip)) {
                ip = "127.0.0.1";
            }
            user.setIp(ip);
            user.setCreateTime(new Date());
            userService.saveUser(user);
            map.put("msg", "success");
        } catch (BaseException e) {
            map.put("msg", e.getMessage());
            return map;
        }
        return map;
    }

    /*
     * 跳转到修改页面
     */
    @ResponseBody
    @RequestMapping("/toUpdateUser")
    public Map toEditPage(@RequestParam("id") Long id) {
        User user = userService.getUserById(id);
        Map<String, User> map = new HashMap<>();
        map.put("user", user);
        return map;
    }

    /*
     * 修改用户
     *
     */
    @ResponseBody
    @RequestMapping(value = "/updateUser")
    public Map updateUser(@RequestBody User user) {
        Map<String, Object> map = new HashMap<>();
        try {
            if (null == user) {
                throw new BaseException("用户信息为空！");
            }
            userService.updateUser(user);
            map.put("msg", "success");
        } catch (BaseException e) {
            map.put("msg", e.getMessage());
            return map;
        }
        return map;
    }

    /*
     * 删除用户
     */
    @RequestMapping(value = "/deleteUser")
    public String deleteUser(Long id) {
        userService.delUser(id);
        return "redirect:/user/queryUser";
    }

    /*
     * 禁用 、启用
     */
    @RequestMapping(value = "/updateStatus")
    public String updateStatus(Long id, Long status) {
        userService.updataStatus(id, status);
        return "success";
    }

}
