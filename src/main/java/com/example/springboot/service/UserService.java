package com.example.springboot.service;

import com.example.springboot.entity.Role;
import com.example.springboot.entity.User;
import com.example.springboot.entity.Userrole;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface UserService {
    List<User> getAllUser(String name);

    User getUser(String username, String password);

    User getUserById(Long id);

    void saveUser(User user);

    void updateUser(User user);

    void delUser(Long id);

    void updataStatus(Long id, Long status);

    void updatePwd(User user);

    List<Role> findRoleByUserId(Long id);

    void saveUserrole(Userrole userrole);

    User getUserByUsername(String username);

    void uploadImg(Long id, MultipartFile file, String uploadDir);
}
