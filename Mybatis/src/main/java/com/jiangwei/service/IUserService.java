package com.jiangwei.service;


import com.jiangwei.entity.User;

/**
 * Created by Administrator on 2017\11\7 0007.
 */
public interface IUserService {
    User selectByPrimaryKey(Integer id);
    User selectByName(String username);
}
