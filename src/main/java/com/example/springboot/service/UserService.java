package com.example.springboot.service;

import com.example.springboot.entity.User;
import org.springframework.stereotype.Service;


public interface UserService {
    User getUser(String userName, String passWord);
}
