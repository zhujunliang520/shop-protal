package com.fh.shopportal.logon.mapper;

import com.fh.shopportal.logon.po.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

public interface UserMapper {
    User getUserByUserName(String userName);

    @Insert("insert into mall_user(userName,password,realName,phone,email,sex) values(#{userName},#{password},#{realName},#{phone},#{email},#{sex})")
    void addUser(User user);
}
