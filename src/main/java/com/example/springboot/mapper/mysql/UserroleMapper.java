package com.example.springboot.mapper.mysql;

import com.example.springboot.entity.Userrole;

import java.util.List;

public interface UserroleMapper {

    List<Userrole> findAllUserrole();

    List<Userrole> findRoleByUserId(Long id);

    void saveUserrole(Userrole userrole);

    void delUserroleByUserId(Long id);

}
