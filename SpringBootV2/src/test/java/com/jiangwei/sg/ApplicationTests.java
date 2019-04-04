package com.jiangwei.sg;

import com.jiangwei.sg.entity.SysUser;
import com.jiangwei.sg.mapper.SysUserMapper;
import com.jiangwei.sg.util.redis.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

	@Autowired
	SysUserMapper userMapper ;

	@Resource
    RedisUtil redisDao;

	@Test
	public void contextLoads() {
		SysUser user = new SysUser();
		user.setUsername("jiangwei");
		user.setPassword("691216");
		user.setName("jiangwei");
		user.setAddress("wuhan");
		int i = userMapper.insert(user);
		System.out.println(i);
	}

	@Test
	public void testbatchInsert(){
		SysUser user = new SysUser();
		user.setUsername("yingying");
		user.setPassword("691216");
		user.setName("yingying");
		user.setAddress("wuhan");
		user.setMobile("18271458826");
		user.setEmail("415313909@qq.com");

		SysUser user2 = new SysUser();
		user2.setUsername("chengcheng");
		user2.setPassword("691216");
		user2.setName("chengcheng");
		user2.setAddress("wuhan");
		user2.setMobile("18271458826");
		user2.setEmail("415313909@qq.com");
		List list = new ArrayList<>();
		list.add(user);
		list.add(user2);
		Map map = new HashMap<>();
		map.put("userlist",list);
		int i = userMapper.insertBatch(list);
		System.out.println(i);
	}

	@Test
	public void testRedis(){
		Object hello = redisDao.get("hello");
		System.out.println(hello);
	}

}
