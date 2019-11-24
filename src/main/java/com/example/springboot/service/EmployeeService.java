package com.example.springboot.service;

import com.example.springboot.entity.Employee;

import java.util.List;

public interface EmployeeService {

    void saveEmployee(Employee employee);

    List<Employee> getAllEmployee();

    Employee getEmployee(Integer id);

    void updateEmployee(Employee employee);

    void delEmployee(Integer id);
}
