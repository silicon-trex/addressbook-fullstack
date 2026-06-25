package com.addressbook.controller;

import com.addressbook.model.Contact;
import com.addressbook.model.User;
import com.addressbook.service.ContactService;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contacts")
public class ContactController {
    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping
    public ApiResult<List<Contact>> list(@RequestParam(required = false) String keyword,
                                          HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) return ApiResult.error("未登录");

        List<Contact> contacts;
        if (keyword != null && !keyword.trim().isEmpty()) {
            contacts = contactService.searchContacts(keyword.trim(), user.getUserId());
        } else {
            contacts = contactService.getAllContacts(user.getUserId());
        }
        return ApiResult.success(contacts);
    }

    @GetMapping("/{id}")
    public ApiResult<Contact> getOne(@PathVariable Integer id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) return ApiResult.error("未登录");

        Contact contact = contactService.getContactById(id, user.getUserId());
        if (contact == null) return ApiResult.error("记录不存在");
        return ApiResult.success(contact);
    }

    @PostMapping
    public ApiResult<?> add(@RequestParam String name,
                             @RequestParam String gender,
                             @RequestParam String phone,
                             @RequestParam(required = false) String email,
                             @RequestParam(required = false) String address,
                             HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) return ApiResult.error("未登录");

        if (contactService.checkPhoneExists(phone, user.getUserId())) {
            return ApiResult.error("电话已存在，请使用其他电话！");
        }

        Contact contact = new Contact(name, gender, address, email, phone, user.getUserId());
        if (contactService.addContact(contact)) {
            return ApiResult.success(null);
        }
        return ApiResult.error("添加失败，请重试！");
    }

    @PutMapping("/{id}")
    public ApiResult<?> edit(@PathVariable Integer id,
                              @RequestParam String name,
                              @RequestParam String gender,
                              @RequestParam String phone,
                              @RequestParam(required = false) String email,
                              @RequestParam(required = false) String address,
                              HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) return ApiResult.error("未登录");

        Contact oldContact = contactService.getContactById(id, user.getUserId());
        if (oldContact == null) return ApiResult.error("记录不存在");

        if (!oldContact.getPhone().equals(phone)
                && contactService.checkPhoneExistsExcludeId(phone, user.getUserId(), id)) {
            return ApiResult.error("电话已存在，请使用其他电话！");
        }

        if (contactService.updateContact(id, name, gender, phone, email, address, user.getUserId())) {
            return ApiResult.success(null);
        }
        return ApiResult.error("修改失败！");
    }

    @DeleteMapping("/{id}")
    public ApiResult<?> delete(@PathVariable Integer id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) return ApiResult.error("未登录");

        Contact contact = contactService.getContactById(id, user.getUserId());
        if (contact == null) return ApiResult.error("记录不存在");

        if (contactService.deleteContact(id, user.getUserId())) {
            return ApiResult.success(null);
        }
        return ApiResult.error("删除失败！");
    }
}
