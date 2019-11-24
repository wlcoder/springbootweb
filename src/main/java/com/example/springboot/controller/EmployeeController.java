package com.example.springboot.controller;

import com.example.springboot.dao.DepartmentDao;
import com.example.springboot.entity.Department;
import com.example.springboot.entity.Employee;
import com.example.springboot.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Controller
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    @Autowired
    DepartmentDao departmentDao;

    //查询所有员工返回列表页面
    @GetMapping("/emps")
    public String list(Model model) {
        Collection<Employee> employees = employeeService.getAllEmployee();
        //放在请求域中
        model.addAttribute("emps", employees);
        return "emp/list";
    }

    //来到员工添加页面
    @GetMapping("/emp")
    public String toAddPage(Model model) {
        //来到添加页面,查出所有的部门，在页面显示
        Collection<Department> departments = departmentDao.getDepartments();
        model.addAttribute("depts", departments);
        return "emp/add";
    }

    //员工添加
    @PostMapping("/emp")
    public String addEmp(Employee employee) {
        //来到员工列表页面
        System.out.println("保存的员工信息：" + employee);
        //保存员工
        employeeService.saveEmployee(employee);
        return "redirect:/emps";
    }

    //来到修改页面，查出当前员工，在页面回显
    @GetMapping("/emp/{id}")
    public String toEditPage(@PathVariable("id") Integer id, Model model) {
        Employee employee = employeeService.getEmployee(id);
        model.addAttribute("emp", employee);
        //页面要显示所有的部门列表
        Collection<Department> departments = departmentDao.getDepartments();
        model.addAttribute("depts", departments);
        //回到修改页面(add是一个修改添加二合一的页面);
        return "emp/add";
    }

    //员工修改；需要提交员工id；
    @PutMapping("/emp")
    public String updateEmployee(Employee employee) {
        System.out.println("修改的员工数据：" + employee);
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
