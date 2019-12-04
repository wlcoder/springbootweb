package com.example.springboot.mapper.mysql;

import com.example.springboot.entity.Department;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DepartmentMapper {
    Department getDept(Integer id);

    List<Department> getAllDept();
}
