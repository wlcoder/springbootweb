package com.example.springboot.service.impl;

import com.example.springboot.entity.Department;
import com.example.springboot.mapper.mysql.DepartmentMapper;
import com.example.springboot.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    private DepartmentMapper departmentMapper;

    @Override
    public Department getDept(Integer id) {
        return departmentMapper.getDept(id);
    }

    @Override
    public List<Department> getAllDept() {
        return departmentMapper.getAllDept();
    }
}
