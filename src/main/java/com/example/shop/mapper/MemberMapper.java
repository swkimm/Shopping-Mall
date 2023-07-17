package com.example.shop.mapper;

import com.example.shop.domain.Members;
import com.example.shop.domain.Signup;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Optional;

@Mapper
public interface MemberMapper {

    @Select("""
            SELECT * FROM Members WHERE email = #{email}
            """)
    Optional<Members> findByEmail(String email);

    @Insert("""
            INSERT INTO Members (name, email, password)
            VALUES (#{name}, #{email}, #{password})
            """)
    void save(Members members);


    @Select("""
            SELECT * FROM Members
            WHERE email = #{email}
            """)
    Members findMemberByEmail();

    @Select("""
            SELECT COUNT(nickName) 
            FROM Members
            WHERE nickName = #{nickName}
            """)
    Integer duplCheckNickName(String nickName);

    @Select("""
            SELECT COUNT(email)
            FROM Members
            WHERE email = #{email}
            """)
    Integer duplCheckEmail(String email);
}
