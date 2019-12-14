package com.example.springboot.service;

import com.example.springboot.entity.User;

import java.util.List;


public interface UserService {
    List<User> getAllUser();

    User getUser(String username, String password);

    User getUserById(Long id);

    void saveUser(User user);

    void updateUser(User user);

    void delUser(Long id);

    void updataStatus(Long id, Long status);
}
