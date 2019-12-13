package com.example.springboot.controller;

import com.example.springboot.entity.User;
import com.example.springboot.service.UserService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        Page page = PageHelper.startPage(pageNum, 5);
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
    public User addUser(@RequestBody User user) {
        userService.saveUser(user);
        return user;
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
    @RequestMapping(value = "/updateUser")
    public String updateUser(@RequestBody User user) {
        userService.updateUser(user);
        return "redirect:/queryUser";
    }

    /*
     * 删除用户
     * */
    @RequestMapping(value = "/deleteUser/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.delUser(id);
        return "redirect:/queryUser";
    }

}
