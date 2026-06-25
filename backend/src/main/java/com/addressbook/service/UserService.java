package com.addressbook.service;

import com.addressbook.mapper.UserMapper;
import com.addressbook.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserMapper userMapper;

    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public boolean checkUsernameExists(String username) {
        return userMapper.countByUsername(username) > 0;
    }

    public boolean register(User user) {
        return userMapper.insert(user) > 0;
    }

    public User login(String username, String password) {
        return userMapper.findByUsernameAndPassword(username, password);
    }
}
