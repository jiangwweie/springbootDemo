package com.dubbo.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.dubbo.service.UserService;

/**
 * describe:
 *
 * @author lalio
 * @email jiangwei.wh@outlook.com
 * @date 2018/10/23
 */
@Service
public class UserServiceImpl implements UserService {
    @Override
    public String getName() {
        return "dubbo demo";
    }
}
