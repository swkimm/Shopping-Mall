package com.example.shop.mapper;

import com.example.shop.domain.Members;
import com.example.shop.domain.Signup;
import com.example.shop.request.MemberUpdateRequest;
import com.example.shop.response.MemberListResponse;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface MemberMapper {

    @Select("""
            SELECT * FROM Members WHERE email = #{email}
            """)
    Optional<Members> findByEmail(String email);

    @Insert("""
            INSERT INTO 
                Members 
                ( name
                , phone
                , nickName
                , email
                , pwd 
                , address )
            VALUES 
                ( #{name}
                , #{phone}
                , #{nickName}
                , #{email}
                , #{pwd}
                , #{address} )
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


    @Select("""
            SELECT * FROM Members
            WHERE email = #{email}
            """)
    Members getMemberProfile(String email);

    @Update("""
        UPDATE Members
        SET 
            name = #{update.name}
            , phone = #{update.phone}
            , nickName = #{update.nickName}
            , email = #{update.email}
            , address = #{addressParam}
        WHERE
            email = #{emailParam};
        """)
    void updateProfileAddress(@Param("update") MemberUpdateRequest update, @Param("emailParam") String email, @Param("addressParam") String address);


    @Update("""
        UPDATE Members
        SET 
            name = #{update.name}
            , phone = #{update.phone}
            , nickName = #{update.nickName}
            , email = #{update.email}
            , address = #{update.address}
        WHERE
            email = #{emailParam};
        """)
    void updateProfile(@Param("update") MemberUpdateRequest update, @Param("emailParam") String email);

    @Select("""
            SELECT address
            FROM Members
            WHERE email = #{email}
            """)
    String getAddress(String email);

    @Select("""
            SELECT * FROM Members
            WHERE authority = #{authority}
            """)
    List<MemberListResponse> getMemberList(Integer authority);

    @Delete("""
            DELETE FROM Members 
            WHERE email = #{email}
            """)
    int deleteMember(String email);
}
