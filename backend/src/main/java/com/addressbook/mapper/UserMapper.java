package com.addressbook.mapper;

import com.addressbook.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    User findByUsername(@Param("username") String username);
    User findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
    int insert(User user);
    int countByUsername(@Param("username") String username);
}
