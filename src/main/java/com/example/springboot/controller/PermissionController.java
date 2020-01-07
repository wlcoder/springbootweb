package com.example.springboot.controller;

import com.example.springboot.entity.Permission;
import com.example.springboot.service.PermissionService;
import com.example.springboot.util.excel.ExcelUtils;
import com.example.springboot.util.exception.BaseException;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/permission")
public class PermissionController {
    @Autowired
    private PermissionService permissionService;

    /*
     * 查询权限
     */
    @RequiresPermissions("permission_menu")
    @RequestMapping(value = "/queryPermission")
    public String queryPermission(Model model, @RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum) {
        Page page = PageHelper.startPage(pageNum, 5, "id desc");
        List<Permission> permissionList = permissionService.getAllPermission();
        PageInfo pageInfo = new PageInfo<>(page.getResult());
        model.addAttribute("pageInfo", pageInfo);
        return "permission/list";
    }

    /*
     * 新增权限
     */
    @ResponseBody
    @RequestMapping(value = "/addPermission")
    public Map addPermission(@RequestBody Permission permission) {
        Map<String, Object> map = new HashMap<>();
        try {
            if (null == permission) {
                throw new BaseException("权限信息为空！");
            }
            permissionService.savePermission(permission);
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
    @RequestMapping("/toUpdatePermission")
    public Map toEditPage(Long id) {
        Permission permission = permissionService.getPermission(id);
        Map<String, Permission> map = new HashMap<>();
        map.put("permission", permission);
        return map;
    }

    /*
     * 修改权限
     *
     */
    @ResponseBody
    @RequestMapping(value = "/updatePermission")
    public Map updatePermission(@RequestBody Permission permission) {
        Map<String, Object> map = new HashMap<>();
        try {
            if (null == permission) {
                throw new BaseException("权限信息为空！");
            }
            permissionService.updatePermission(permission);
            map.put("msg", "success");
        } catch (BaseException e) {
            map.put("msg", e.getMessage());
            return map;
        }
        return map;
    }

    /*
     * 删除权限
     */
    @RequestMapping(value = "/deletePermission")
    public String deletePermission(Long id) {
        permissionService.delPermission(id);
        return "redirect:/permission/queryPermission";
    }

    /*
     * 权限导出
     * 按照日期进行导出
     *
     */
    @RequestMapping(value = "/exportExcel")
    public void exportExcel(HttpServletResponse response) {
        System.out.println("执行权限导出。。。。。。。。");
        List<Permission> permissionList = permissionService.getAllPermission();
        String path = "D:\\my_Java\\springbootweb-excel\\";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
        String date = dateFormat.format(new Date());
        path = path + "permission" + date + ".xlsx";
        ExcelUtils.writeExcel(response, permissionList, Permission.class, path);
    }

}
