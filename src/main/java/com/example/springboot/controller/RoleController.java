package com.example.springboot.controller;

import com.example.springboot.entity.*;
import com.example.springboot.service.PermissionService;
import com.example.springboot.service.RoleService;
import com.example.springboot.util.exception.BaseException;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;

    /*
     * 查询角色
     */
    @RequiresPermissions("role_menu")
    @RequestMapping(value = "/queryRole")
    public String queryRole(Model model, @RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum) {
        Page page = PageHelper.startPage(pageNum, 5, "id desc");
        List<Role> roleList = roleService.getAllRole();
        PageInfo pageInfo = new PageInfo<>(page.getResult());
        model.addAttribute("pageInfo", pageInfo);
        return "role/list";
    }

    /*
     * 新增角色
     */
    @ResponseBody
    @RequestMapping(value = "/addRole")
    public Map addRole(@RequestBody Role role) {
        Map<String, Object> map = new HashMap<>();
        try {
            if (null == role) {
                throw new BaseException("角色信息为空！");
            }
            roleService.saveRole(role);
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
    @RequestMapping("/toUpdateRole")
    public Map toEditPage(@RequestParam("id") Long id) {
        Role role = roleService.getRole(id);
        Map<String, Role> map = new HashMap<>();
        map.put("role", role);
        return map;
    }

    /*
     * 修改角色
     *
     */
    @ResponseBody
    @RequestMapping(value = "/updateRole")
    public Map updateRole(@RequestBody Role role) {
        Map<String, Object> map = new HashMap<>();
        try {
            if (null == role) {
                throw new BaseException("角色信息为空！");
            }
            roleService.updateRole(role);
            map.put("msg", "success");
        } catch (BaseException e) {
            map.put("msg", e.getMessage());
            return map;
        }
        return map;
    }

    /*
     * 删除角色
     */
    @RequestMapping(value = "/deleteRole")
    public String deleteRole(Long id) {
        roleService.delRole(id);
        return "redirect:/role/queryRole";
    }

    /*
     * 禁用 、启用
     */
    @ResponseBody
    @RequestMapping(value = "/updateStatus")
    public Map<String, Object> updateStatus(Long id, Long status) {
        Map<String, Object> map = new HashMap<>();
        try {
            roleService.updataStatus(id, status);
            map.put("msg", "success");
        } catch (BaseException e) {
            map.put("msg", "修改状态失败！");
            return map;
        }
        return map;
    }

    /*
     * 跳转到设置权限页面
     */
    @RequestMapping(value = "/toSetPermission/{id}")
    public String toSetPermission(Model model, @PathVariable("id") Long id) {
        //查询所有的权限
        List<Permission> permissionList = permissionService.getAllPermission();
        //查询角色信息
        Role role = roleService.getRole(id);
        //根据角色Id 查询所拥有的权限
        List<Permission> permissions = roleService.findPermissionByRoleId(id);
        List<String> list = new ArrayList();
        if (null != permissions && permissions.size() > 0) {
            //当前角色所拥有的权限
            for (Permission permission : permissions) {
                list.add(permission.getName());
            }
        }
        String permissionName = list.toString();
        model.addAttribute("permissionList", permissionList);
        model.addAttribute("roleName", role.getName());
        model.addAttribute("rid", id);
        model.addAttribute("permissionName", permissionName);
        return "role/setPermission";
    }

    @ResponseBody
    @RequestMapping("/setRolePermission")
    public Map setRolePermission(@RequestBody Rolepermission rolepermission) {
        Map<String, Object> map = new HashMap<>();
        try {
            roleService.saveRolepermission(rolepermission);
            map.put("msg", "success");
        } catch (BaseException e) {
            map.put("msg", e.getMessage());
        }
        return map;
    }


}
