package com.addressbook.service;

import com.addressbook.mapper.ContactMapper;
import com.addressbook.model.Contact;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {
    private final ContactMapper contactMapper;

    public ContactService(ContactMapper contactMapper) {
        this.contactMapper = contactMapper;
    }

    public List<Contact> getAllContacts(Integer userId) {
        return contactMapper.findAllByUserId(userId);
    }

    public Contact getContactById(Integer id, Integer userId) {
        return contactMapper.findByIdAndUserId(id, userId);
    }

    public List<Contact> searchContacts(String keyword, Integer userId) {
        return contactMapper.search(userId, keyword);
    }

    public boolean addContact(Contact contact) {
        return contactMapper.insert(contact) > 0;
    }

    public boolean updateContact(Integer id, String name, String gender, String phone,
                                 String email, String address, Integer userId) {
        return contactMapper.update(id, name, gender, phone, email, address, userId) > 0;
    }

    public boolean deleteContact(Integer id, Integer userId) {
        return contactMapper.deleteByIdAndUserId(id, userId) > 0;
    }

    public boolean checkPhoneExists(String phone, Integer userId) {
        return contactMapper.countByPhoneAndUserId(phone, userId) > 0;
    }

    public boolean checkPhoneExistsExcludeId(String phone, Integer userId, Integer excludeId) {
        return contactMapper.countByPhoneAndUserIdExcludeId(phone, userId, excludeId) > 0;
    }
}
