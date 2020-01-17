package com.example.springboot.service.impl;

import com.example.springboot.entity.Role;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
        if (null != user && !StringUtils.isEmpty(user.getNickname()) && !StringUtils.isEmpty(user.getUsername())) {
            User u = userMapper.getUserByUsername(user.getUsername());
            User u1 = userMapper.getUserByNickname(user.getNickname());
            if (null != u) {
                throw new BaseException("用户已存在！");
            } else if (null != u1) {
                throw new BaseException("用户昵称重复！");
            } else {
                user.setCreateTime(new Date());
                user.setStatus(1L);
                user.setPassword(Md5Util.getMD5(user.getPassword()));
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
        User user = userMapper.getUserById(id);
        if (null != user && user.getUsername().equals("admin")) {
            throw new BaseException("超级管理员不可删除！");
        } else {
            userMapper.delUser(id);
        }
    }

    @Override
    public void updataStatus(Long id, Long status) {
        userMapper.updataStatus(id, status);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "mysqlTransactionManager")
    public void updatePwd(User user) {
        if (null == user.getId() || StringUtils.isEmpty(user.getPassword()) || StringUtils.isEmpty(user.getNewpwd()) || StringUtils.isEmpty(user.getRenewpwd())) {
            throw new BaseException("信息为空！");
        }
        User u = userMapper.getUserById(user.getId());
        if (null == u) {
            throw new BaseException("该用户不存在！");
        } else {
            if (!u.getPassword().equals(Md5Util.getMD5(user.getPassword()))) {
                throw new BaseException("原密码输入错误！");
            } /*else if (user.getNewpwd().equals(user.getPassword())) { 由前端判断
                throw new BaseException("原密码与新密码一致！");
            } else if (!user.getNewpwd().equals(user.getRenewpwd())) {
                throw new BaseException("两次密码输入不一致！");
            } */ else {
                userMapper.updatePwd(user.getId(), Md5Util.getMD5(user.getNewpwd()));
            }
        }
    }

    /*根据用户Id查询角色*/
    @Override
    public List<Role> findRoleByUserId(Long id) {
        return userMapper.findRoleByUserId(id);
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

    @Override
    public User getUserByUsername(String username) {
        return userMapper.getUserByUsername(username);
    }

    //上传用户图像
    @Override
    public void uploadImg(Long id, MultipartFile file, String uploadDir) {
        try {
            String imgUrl = null;
            String filename = upload(file, uploadDir, file.getOriginalFilename());
            if (!StringUtils.isEmpty(filename)) {
                imgUrl = new File(uploadDir).getName() + "/" + filename;
            }
            //保存图片路径
            User user = userMapper.getUserById(id);
            user.setImage(imgUrl);
            userMapper.updateUser(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //图片保存在本地磁盘内
    public String upload(MultipartFile file, String path, String fileName) throws Exception {
        // 生成新的文件名
        String realPath = path + "/" + UUID.randomUUID().toString().replace("-", "")
                + fileName.substring(fileName.lastIndexOf("."));
        File dest = new File(realPath);
        // 判断文件父目录是否存在
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdir();
        }
        // 保存文件
        file.transferTo(dest);
        return dest.getName();
    }

}
