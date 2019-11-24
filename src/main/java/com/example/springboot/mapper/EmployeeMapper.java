package com.example.springboot.mapper;


import com.example.springboot.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EmployeeMapper {
    void saveEmployee(Employee employee);

    List<Employee> getAllEmployee();

    Employee getEmployee(Integer id);

    void updateEmployee(Employee employee);

    void delEmployee(Integer id);

}