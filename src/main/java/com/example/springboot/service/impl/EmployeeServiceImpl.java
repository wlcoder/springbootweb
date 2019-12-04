package com.example.springboot.service.impl;

import com.example.springboot.entity.Department;
import com.example.springboot.entity.Employee;
import com.example.springboot.mapper.mysql.DepartmentMapper;
import com.example.springboot.mapper.mysql.EmployeeMapper;
import com.example.springboot.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeMapper employeeMapper;
    @Autowired
    private DepartmentMapper departmentMapper;

    @Override
    public void saveEmployee(Employee employee) {
        employeeMapper.saveEmployee(employee);
    }

    @Override
    public List<Employee> getAllEmployee() {
        List<Employee> list = employeeMapper.getAllEmployee();
        List<Employee> employeeslist = new ArrayList<>();
        if (null != list && list.size() > 0) {
            for (Employee emp : list) {
                Department dept = departmentMapper.getDept(emp.getDeptId());
                emp.setDepartment(dept);
                employeeslist.add(emp);
            }
        }
        return employeeslist;
    }

    @Override
    public Employee getEmployee(Integer id) {
        return employeeMapper.getEmployee(id);
    }

    @Override
    public void updateEmployee(Employee employee) {
        employeeMapper.updateEmployee(employee);
    }

    @Override
    public void delEmployee(Integer id) {
        employeeMapper.delEmployee(id);
    }
}
