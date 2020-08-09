package com.fh.shopportal.logon.biz.impl;

import com.fh.shopportal.logon.biz.IUserService;
import com.fh.shopportal.logon.mapper.UserMapper;


import com.fh.shopportal.logon.po.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("userService")
public class UserServiceImpl implements IUserService {
    @Resource
    private UserMapper userMapper;

    @Override
    public User getUserByUserName(String userName) {
        User user = (User) userMapper.getUserByUserName(userName);
        return user;
    }

    @Override
    public void addUser(User user) {
        userMapper.addUser(user);
    }
}
