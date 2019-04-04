package com.jiangwei.dao;

import com.jiangwei.entity.Role;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface RoleMapper {
    int deleteByPrimaryKey(Integer rid);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Integer rid);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    @Select(" select * from usert.role r where r.uid = #{uid} ")
    List<Role> selectByUid(Integer uid);
}