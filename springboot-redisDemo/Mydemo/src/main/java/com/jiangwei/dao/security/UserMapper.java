package com.jiangwei.dao.security;

import com.jiangwei.entity.security.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User getRoles(int uid);

    User selectByName(String  username);

    @Select("select * from user where password = #{password} ")
    List<User> findByPwd(String password);
}