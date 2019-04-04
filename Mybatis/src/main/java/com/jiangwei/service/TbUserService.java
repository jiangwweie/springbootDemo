package com.jiangwei.service;

import com.jiangwei.entity.TbUser;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

public interface TbUserService {

    int deleteByPrimaryKey(Long id);

    @Cacheable(cacheNames = "TbUser",key = "#id")
    TbUser selectByPrimaryKey(Long id);

    int insert(TbUser record);

    int insertSelective(TbUser record);

    TbUser selectByPrimaryKey(TbUser tbUser);

    int updateByPrimaryKeySelective(TbUser record);

    int updateByPrimaryKey(TbUser record);


    List selectLimit(TbUser record, Integer start, Integer end);
}
