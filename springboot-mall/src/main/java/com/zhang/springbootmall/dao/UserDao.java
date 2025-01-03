package com.zhang.springbootmall.dao;

import com.zhang.springbootmall.dto.UserRegisterRequest;
import com.zhang.springbootmall.model.User;

public interface UserDao {

    User getUserById(Integer userId);

    User getUserByEmail(String email);

    Integer createUser(UserRegisterRequest userRegisterRequest);

}
