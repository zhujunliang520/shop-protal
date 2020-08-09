package com.fh.shopportal.logon.biz;

import com.fh.shopportal.logon.po.User;

public interface IUserService {
    User getUserByUserName(String userName);

    void addUser(User user);
}
