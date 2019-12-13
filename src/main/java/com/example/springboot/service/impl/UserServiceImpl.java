package com.example.springboot.service.impl;

import com.example.springboot.entity.User;
import com.example.springboot.mapper.mysql.UserMapper;
import com.example.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> getAllUser() {
        return userMapper.getAllUser();
    }

    @Override
    public User getUser(String username, String password) {
        return userMapper.getUser(username, password);
    }

    @Override
    public User getUserById(Long id) {
        return userMapper.getUserById(id);
    }

    //多数据源配置事物 注意指定事物 mysqlTransactionManager
    @Transactional(rollbackFor = Exception.class, transactionManager = "mysqlTransactionManager")
    @Override
    public void saveUser(User user) {
        userMapper.saveUser(user);
    }

    @Override
    public void updateUser(User user) {
        userMapper.updateUser(user);
    }

    @Override
    public void delUser(Long id) {
        userMapper.delUser(id);
    }
}
