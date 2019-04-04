package com.jiangwei.service.impl;

import com.jiangwei.config.aspect.annotation.CacheEvict;
import com.jiangwei.config.aspect.annotation.Cacheable;
import com.jiangwei.dao.TbUserMapper;
import com.jiangwei.entity.TbUser;
import com.jiangwei.service.TbUserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TbUserServiceImpl implements TbUserService {

    @Autowired
    TbUserMapper userMapper;

    Logger log = Logger.getLogger(TbUserServiceImpl.class);


    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @CacheEvict(key = "TbUser", fieldKey = "#id")
    @Override
    public int deleteByPrimaryKey(Long id) {
        log.error("clear redis-  - -- - -   - - -- - - - - - - - - - --  --  -");
        return userMapper.deleteByPrimaryKey(id);
    }


    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @Cacheable(key = "TbUser", fieldKey = "#id")
    @Override
    public TbUser selectByPrimaryKey(Long id) {
        log.error("select in db -  - -- - -   - - -- - - - - - - - - - --  --  -");

        return userMapper.selectByPrimaryKey(id);
    }

    /**
     * update修改
     *
     * @param record
     * @return
     */
    @CacheEvict(key = "TbUser", fieldKey = "#record.id")
    @Override
    public int updateByPrimaryKeySelective(TbUser record) {
        log.error("clear redis-  - -- - -   - - -- - - - - - - - - - --  --  -");

        return userMapper.updateByPrimaryKeySelective(record);
    }


    /**
     * 插入,不需要缓存  查询的时候在缓存
     *
     * @param record
     * @return
     */
    @Override
    public int insert(TbUser record) {
        return userMapper.insert(record);
    }


    @Override
    public int insertSelective(TbUser record) {
        return userMapper.insert(record);
    }


    @Override
    public TbUser selectByPrimaryKey(TbUser tbUser) {
        return userMapper.selectByPrimaryKey(tbUser.getId());
    }


    @Override
    public int updateByPrimaryKey(TbUser tbUser) {
        return userMapper.updateByPrimaryKeySelective(tbUser);
    }

    @Override
    public List selectLimit(TbUser record, Integer start, Integer end) {
        Map map = new HashMap<>();
        map.put("status", record.getStatus());
        map.put("start", start);
        map.put("end", end);
        return userMapper.selectLimit(map);
    }
}
