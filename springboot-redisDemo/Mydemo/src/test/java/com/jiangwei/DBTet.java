package com.jiangwei;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.jiangwei.dao.security.RoleMapper;
import com.jiangwei.dao.security.UrlConfigMapper;
import com.jiangwei.dao.security.UserMapper;
import com.jiangwei.entity.security.Role;
import com.jiangwei.entity.security.UrlConfig;
import com.jiangwei.entity.security.User;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

/**
 * describe:
 *
 * @author lalio
 * @email jiangwei.wh@outlook.com
 * @date 2018/11/16
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DBTet {

    public final static Logger log = LoggerFactory.getLogger(DBTet.class);


    @Autowired
    UserMapper userMapper;

    @Autowired
    RoleMapper roleMapper;

    @Autowired
    UrlConfigMapper urlConfigMapper;


    @Value("classpath:static/data/urlConfig.json")
    private Resource urlconfig;

    @Test
    public void testUrl() {
        Map map = new HashMap();
        //查询所有资源权限
        List<UrlConfig> configs = urlConfigMapper.findAll();
        Collection<ConfigAttribute> atts ;
        for (UrlConfig config :configs){
            //单个资源路径对应的角色要求
            String[] rids = config.getRoles().split(",");
            atts = new ArrayList<>();
            for (String rid:rids){
                Role role = roleMapper.selectByPrimaryKey(Integer.valueOf(rid));
                ConfigAttribute ca = new SecurityConfig(role.getName());
                atts.add(ca);
            }
            map.put(config.getUrl(),atts);
        }
        System.out.println( map);
    }


    @Test
    public void test1() {
        PageHelper.startPage(2, 3);
        List<User> users = userMapper.findByPwd("123456");

    }

    @Test
    public void test2() throws IOException {
        String areaData = IOUtils.toString(urlconfig.getInputStream(), Charset.forName("UTF-8"));
        System.out.println("路径json内容-------------------------------------------------" + areaData);
        JSONObject jsonObj = JSONObject.parseObject(areaData);
        for (Map.Entry<String, Object> entry : jsonObj.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
            String key = entry.getKey();
            JSONArray value = (JSONArray) entry.getValue();
        }
    }

    @Test
    public void test3() {
        User jiangwei = userMapper.selectByPrimaryKey(1);
        System.out.println(jiangwei);
    }

    @Test
    public void testlog() {
        log.trace("lombok info log!");
        log.debug("lombok debug log!");
        log.info("lombok info log!");
        log.warn("lombok warn log!");
        log.error("lombok error log!");
    }
}
