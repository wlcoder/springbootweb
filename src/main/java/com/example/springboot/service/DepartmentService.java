package com.example.springboot.service;

import com.example.springboot.entity.Department;

import java.util.List;

public interface DepartmentService {
    Department getDept(Integer id);

    List<Department> getAllDept();
}
