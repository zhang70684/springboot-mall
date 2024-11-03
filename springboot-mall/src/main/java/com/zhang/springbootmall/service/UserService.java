package com.zhang.springbootmall.service;

import com.zhang.springbootmall.dto.UserRegisterRequest;
import com.zhang.springbootmall.model.User;

public interface UserService {

    User getUserById(Integer userId);

    Integer register(UserRegisterRequest userRegisterRequest);
}
