package com.zhang.springbootmall.service.impl;

import com.zhang.springbootmall.dao.UserDao;
import com.zhang.springbootmall.dto.UserRegisterRequest;
import com.zhang.springbootmall.model.User;
import com.zhang.springbootmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }

    @Override
    public Integer register(UserRegisterRequest userRegisterRequest) {
        return userDao.createUser(userRegisterRequest);
    }
}
