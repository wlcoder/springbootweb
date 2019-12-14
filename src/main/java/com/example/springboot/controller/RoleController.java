package com.example.springboot.controller;

import com.example.springboot.entity.Role;
import com.example.springboot.service.RoleService;
import com.example.springboot.util.exception.BaseException;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    /*
     * 查询角色
     */
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
    @RequestMapping(value = "/updateStatus")
    public String updateStatus(Long id, Long status) {
        roleService.updataStatus(id, status);
        return "success";
    }


}
