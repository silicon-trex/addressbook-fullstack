package com.addressbook.mapper;

import com.addressbook.model.Contact;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ContactMapper {
    List<Contact> findAllByUserId(@Param("userId") Integer userId);
    Contact findByIdAndUserId(@Param("id") Integer id, @Param("userId") Integer userId);
    List<Contact> search(@Param("userId") Integer userId, @Param("keyword") String keyword);
    int insert(Contact contact);
    int update(@Param("id") Integer id, @Param("name") String name,
               @Param("gender") String gender, @Param("phone") String phone,
               @Param("email") String email, @Param("address") String address,
               @Param("userId") Integer userId);
    int deleteByIdAndUserId(@Param("id") Integer id, @Param("userId") Integer userId);
    int countByPhoneAndUserId(@Param("phone") String phone, @Param("userId") Integer userId);
    int countByPhoneAndUserIdExcludeId(@Param("phone") String phone,
                                       @Param("userId") Integer userId,
                                       @Param("excludeId") Integer excludeId);
}
