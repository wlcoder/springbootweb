package com.example.springboot.mapper.mysql;

import com.example.springboot.entity.Role;
import com.example.springboot.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {

    List<User> getAllUser(@Param("name") String name);

    User getUserById(Long id);

    User getUserByNickname(String nickname);

    User getUserByUsername(String username);

    User getUser(@Param("username") String username, @Param("password") String password);

    void saveUser(User user);

    void updateUser(User user);

    void delUser(Long id);

    void updataStatus(@Param("id") Long id, @Param("status") Long status);

    void updatePwd(@Param("id") Long id, @Param("password") String password);

    List<Role> findRoleByUserId(Long id);


}
