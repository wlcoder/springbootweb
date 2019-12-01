package com.example.springboot.controller;

import com.example.springboot.entity.Department;
import com.example.springboot.entity.Employee;
import com.example.springboot.service.DepartmentService;
import com.example.springboot.service.EmployeeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@Controller
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private DepartmentService departmentService;

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
    @GetMapping("/emp")
    public String toAddPage(Model model) {
        //来到添加页面,查出所有的部门，在页面显示
        List<Department> departments = departmentService.getAllDept();
        model.addAttribute("depts", departments);
        return "emp/add";
    }

    //员工添加
    @PostMapping("/emp")
    public String addEmp(Employee employee) {
        //保存员工
        employeeService.saveEmployee(employee);
        return "redirect:/emps";
    }

    //修改页面，查出当前员工，在页面回显
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
    @PutMapping("/emp")
    public String updateEmployee(Employee employee) {
        employeeService.updateEmployee(employee);
        return "redirect:/emps";
    }

    //员工删除
    @DeleteMapping("/emp/{id}")
    public String deleteEmployee(@PathVariable("id") Integer id) {
        employeeService.delEmployee(id);
        return "redirect:/emps";
    }


}
