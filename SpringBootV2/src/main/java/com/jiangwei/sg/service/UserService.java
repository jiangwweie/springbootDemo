package com.jiangwei.sg.service;

import com.jiangwei.sg.entity.SysUser;

public interface UserService {

    SysUser getUserByUname(String username);

    SysUser getById(int uid);
}
