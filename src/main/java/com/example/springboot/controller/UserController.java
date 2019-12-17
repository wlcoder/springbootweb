package com.example.springboot.controller;

import com.example.springboot.entity.Role;
import com.example.springboot.entity.User;
import com.example.springboot.entity.Userrole;
import com.example.springboot.service.RoleService;
import com.example.springboot.service.UserService;
import com.example.springboot.util.exception.BaseException;
import com.example.springboot.util.md5.Md5Util;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;


    /*
     * 查询用户
     */
    @RequestMapping(value = "/queryUser")
    public String queryUser(Model model, @RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum, String name) {
        Page page = PageHelper.startPage(pageNum, 5, "id desc");
        List<User> userList = userService.getAllUser(name);
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
            user.setPassword(Md5Util.getMD5(user.getPassword()));
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

    /*
     * 密码修改
     */
    @ResponseBody
    @RequestMapping(value = "/updatePwd")
    public Map updatePwd(@RequestBody User user) {
        Map<String, Object> map = new HashMap<>();
        try {
            userService.updatePwd(user);
            map.put("msg", "success");
        } catch (BaseException e) {
            map.put("msg", e.getMessage());
            return map;
        }
        return map;
    }

    /*
     * 设置角色
     */
  /*  @ResponseBody
    @RequestMapping(value = "/setRole")
    public Map setRole(Long id) {
        Map<String, Object> map = new HashMap<>();
        try {
            //查询所有的角色
            List<Role> roleList = roleService.getAllRole();
            //根据用户Id 查询所拥有的角色
            List<Userrole> userroleList = userService.findRoleByUserId(id);
            if (null != userroleList && userroleList.size() > 0) {
                if (null != roleList && roleList.size() > 0) {
                    for (Role role : roleList) {
                        for (Userrole userrole : userroleList) {
                            if (role.getId() == userrole.getRid()) {
                                role.setChecked(true);
                            }
                        }
                    }
                }
            }
            map.put("roleList", roleList);
        } catch (BaseException e) {
            map.put("msg", e.getMessage());
            return map;
        }
        return map;
    }
*/

    /*
     * 跳转到设置角色页面
     */
    @RequestMapping(value = "/toSetRole/{id}")
    public String toSetRole(Model model, @PathVariable("id") Long id) {
        //查询所有的角色
        List<Role> roleList = roleService.getAllRole();
        //查询用户信息
        User user = userService.getUserById(id);
        //根据用户Id 查询所拥有的角色
        List<Userrole> userroleList = userService.findRoleByUserId(id);

        List<String> list = new ArrayList();
        if (null != userroleList && userroleList.size() > 0) {
            if (null != roleList && roleList.size() > 0) {
                for (Role role : roleList) {
                    for (Userrole userrole : userroleList) {
                        if (role.getId() == userrole.getRid()) {
                            role.setChecked(true);
                        }
                    }
                }
            }
            //当前用户所拥有的角色名称
            for (Userrole role : userroleList) {
                list.add(role.getName());
            }
        }
        String roleName = list.toString();
        model.addAttribute("roleList", roleList);
        model.addAttribute("userName", user.getUsername());
        model.addAttribute("uid", id);
        model.addAttribute("roleName", roleName);
        return "user/setRole";
    }

    //设置角色
    @ResponseBody
    @RequestMapping("/setUserRole")
    public Map setUserRole(@RequestBody Userrole userrole) {
        Map<String, Object> map = new HashMap<>();
        try {
            userService.saveUserrole(userrole);
            map.put("msg", "success");
        } catch (BaseException e) {
            map.put("msg", e.getMessage());
        }
        return map;
    }

    //根据搜索框查询用户
    @ResponseBody
    @RequestMapping("/searchUser")
    public Map searchUser(String name) {
        Map<String, Object> map = new HashMap<>();
        try {
            List<User> list = userService.searchUser(name);
            map.put("msg", list);
        } catch (BaseException e) {
            map.put("msg", e.getMessage());
        }
        return map;
        // return "redirect:/user/queryUser?name=" + name;
    }

}
