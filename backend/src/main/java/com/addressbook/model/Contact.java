package com.addressbook.model;

import java.time.LocalDateTime;

public class Contact {
    private Integer id;            // 编号
    private String name;           // 姓名
    private String gender;         // 性别
    private String address;        // 通讯地址
    private String email;          // 邮箱地址
    private String phone;          // 电话
    private Integer userId;        // 所属用户
    private LocalDateTime createTime; // 创建时间

    public Contact() {}

    public Contact(String name, String gender, String address, String email, String phone, Integer userId) {
        this.name = name;
        this.gender = gender;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.userId = userId;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }

    public LocalDateTime getCreateTime() { return createTime; }
    public void setCreateTime(LocalDateTime createTime) { this.createTime = createTime; }
}
