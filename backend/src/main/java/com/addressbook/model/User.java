package com.addressbook.model;

import java.time.LocalDateTime;

public class User {
    private Integer userId;       // 用户编号
    private String username;      // 用户名
    private String password;      // 密码
    private Integer age;          // 年龄
    private LocalDateTime createTime; // 注册时间

    public User() {}

    public User(String username, String password, Integer age) {
        this.username = username;
        this.password = password;
        this.age = age;
    }

    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }

    public LocalDateTime getCreateTime() { return createTime; }
    public void setCreateTime(LocalDateTime createTime) { this.createTime = createTime; }
}
