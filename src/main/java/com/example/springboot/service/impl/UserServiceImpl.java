package com.example.springboot.service.impl;

import com.example.springboot.entity.User;
import com.example.springboot.mapper.mysql.UserMapper;
import com.example.springboot.service.UserService;
import com.example.springboot.util.exception.BaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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

    //多数据源配置事物 需要指定事物 mysqlTransactionManager
    @Transactional(rollbackFor = Exception.class, transactionManager = "mysqlTransactionManager")
    @Override
    public void saveUser(User user) throws BaseException {
        if (null != user && !StringUtils.isEmpty(user.getNickname())) {
            User u = userMapper.getUserByNickname(user.getNickname());
            if (null != u) {
                throw new BaseException("用户昵称重复！");
            } else {
                userMapper.saveUser(user);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "mysqlTransactionManager")
    public void updateUser(User user) {
        if (null != user && !StringUtils.isEmpty(user.getNickname())) {
            User u = userMapper.getUserByNickname(user.getNickname());
            if (null != u && (u.getId() != user.getId())) {
                throw new BaseException("用户昵称重复！");
            } else {
                userMapper.updateUser(user);
            }
        }
    }

    @Override
    public void delUser(Long id) {
        userMapper.delUser(id);
    }

    @Override
    public void updataStatus(Long id, Long status) {
        userMapper.updataStatus(id, status);
    }
}
