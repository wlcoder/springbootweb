package com.example.springboot.service.impl;

import com.example.springboot.entity.User;
import com.example.springboot.entity.Userrole;
import com.example.springboot.mapper.mysql.UserMapper;
import com.example.springboot.mapper.mysql.UserroleMapper;
import com.example.springboot.service.UserService;
import com.example.springboot.util.exception.BaseException;
import com.example.springboot.util.md5.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserroleMapper userroleMapper;

    @Override
    public List<User> getAllUser(String name) {
        return userMapper.getAllUser(name);
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

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "mysqlTransactionManager")
    public void updatePwd(User user) {
        if (null == user.getId() || null == user.getPassword() || null == user.getNewpwd() || null == user.getRenewpwd()) {
            throw new BaseException("信息为空！");
        }
        User u = userMapper.getUserById(user.getId());
        if (null == u) {
            throw new BaseException("该用户不存在！");
        } else {
            if (!u.getPassword().equals(Md5Util.getMD5(user.getPassword()))) {
                throw new BaseException("原密码输入错误！");
            } else if (user.getNewpwd().equals(user.getPassword())) {
                throw new BaseException("原密码与新密码一致！");
            } else if (!user.getNewpwd().equals(user.getRenewpwd())) {
                throw new BaseException("两次密码输入不一致！");
            } else {
                userMapper.updatePwd(user.getId(), Md5Util.getMD5(user.getNewpwd()));
            }
        }
    }

    /*根据用户Id查询角色*/
    @Override
    public List<Userrole> findRoleByUserId(Long id) {
        return userroleMapper.findRoleByUserId(id);
    }

    /**
     * 设置用户角色
     */
    @Override
    public void saveUserrole(Userrole userrole) {
        if (null == userrole) {
            throw new BaseException("信息为空");
        }
        // 先删除之前拥有的角色
        List<Userrole> ur = userroleMapper.findRoleByUserId(userrole.getUid());
        if (null != ur && ur.size() > 0) {
            userroleMapper.delUserroleByUserId(userrole.getUid());
        }
        //再添加角色
        if (null != userrole.getRids() && userrole.getRids().length > 0) {
            Long[] rids = userrole.getRids();
            for (int i = 0; i < rids.length; i++) {
                userrole.setRid(rids[i]);
                userroleMapper.saveUserrole(userrole);
            }
        }
    }

    //搜索框查询
    @Override
    public List<User> searchUser(String name) {
        return userMapper.searchUser(name);
    }

    @Override
    public User getUserByUsername(String username) {
        return userMapper.getUserByUsername(username);
    }


}
