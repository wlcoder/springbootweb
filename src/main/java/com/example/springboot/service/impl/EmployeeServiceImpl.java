package com.example.springboot.service.impl;

import com.example.springboot.entity.Employee;
import com.example.springboot.mapper.EmployeeMapper;
import com.example.springboot.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public void saveEmployee(Employee employee) {
        employeeMapper.saveEmployee(employee);
    }

    @Override
    public List<Employee> getAllEmployee() {
        return employeeMapper.getAllEmployee();
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
