package com.example.springboot.mapper.mysql;

import com.example.springboot.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {

    List<User> getAllUser();

    User getUserById(Long id);

    User getUser(@Param("username") String username, @Param("password") String password);

    void saveUser(User user);

    void updateUser(User user);

    void delUser(Long id);
}
