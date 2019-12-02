package com.example.springboot.controller;

import com.example.springboot.entity.Department;
import com.example.springboot.entity.Employee;
import com.example.springboot.service.DepartmentService;
import com.example.springboot.service.EmployeeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@Api(tags = "EmployeeController", description = "员工controller测试swagger")
@Controller
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private DepartmentService departmentService;

    @ApiOperation(value = "查询所有员工", notes = "获取用户列表")
    //查询所有员工返回列表页面
    @GetMapping("/emps")
    public String list(Model model, @RequestParam(value = "start", defaultValue = "0") int start, @RequestParam(value = "size", defaultValue = "5") int size) {
        PageHelper.startPage(start, size, "id desc");
        List<Employee> employees = employeeService.getAllEmployee();
        PageInfo<Employee> page = new PageInfo<>(employees);
        model.addAttribute("page", page);
        return "emp/list";
    }

    //员工添加页面
    @ApiOperation(value = "跳转到员工页面", notes = "跳转到员工页面")
    @GetMapping("/emp")
    public String toAddPage(Model model) {
        //来到添加页面,查出所有的部门，在页面显示
        List<Department> departments = departmentService.getAllDept();
        model.addAttribute("depts", departments);
        return "emp/add";
    }

    //员工添加
    @ApiOperation(value = "员工添加", notes = "员工添加")
    @ApiImplicitParam(name = "employee", value = "员工信息employee", required = true, dataType = "Employee")
    @PostMapping("/emp")
    public String addEmp(Employee employee) {
        //保存员工
        employeeService.saveEmployee(employee);
        return "redirect:/emps";
    }

    //修改页面，查出当前员工，在页面回显
    @ApiOperation(value = "跳转到员工修改页面", notes = "跳转到员工修改页面")
    @ApiImplicitParam(name = "id", value = "员工id", required = true, paramType = "path",dataType = "Integer")
    @GetMapping("/emp/{id}")
    public String toEditPage(@PathVariable("id") Integer id, Model model) {
        Employee employee = employeeService.getEmployee(id);
        model.addAttribute("emp", employee);
        //页面部门下拉显示
        Collection<Department> departments = departmentService.getAllDept();
        model.addAttribute("depts", departments);
        return "emp/add";
    }

    //员工修改
    @ApiOperation(value = "员工修改", notes = "员工修改")
    @ApiImplicitParam(name = "employee", value = "员工信息employee", required = true, dataType = "Employee")
    @PutMapping("/emp")
    public String updateEmployee(Employee employee) {
        employeeService.updateEmployee(employee);
        return "redirect:/emps";
    }

    //员工删除  paramType="path"解决传入类型不匹配
    @ApiOperation(value = "员工删除", notes = "员工删除")
    @ApiImplicitParam(name = "id", value = "员工id", required = true, paramType = "path",dataType = "Integer")
    @RequestMapping ("/emps/{id}")
    public String deleteEmployee(@PathVariable("id") Integer id) {
        employeeService.delEmployee(id);
        return "redirect:/emps";
    }

}
