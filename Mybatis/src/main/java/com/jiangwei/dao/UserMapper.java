package com.jiangwei.dao;

import com.jiangwei.entity.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User getRoles(int uid);

    User selectByName(String username);

    @Select("select * from user where password = #{password} ")
    List<User> findByPwd(String password);
}