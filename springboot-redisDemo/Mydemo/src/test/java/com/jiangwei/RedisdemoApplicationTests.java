package com.jiangwei;

import com.jiangwei.dao.security.UserMapper;
import com.jiangwei.dao.redis.RedisDao;
import com.jiangwei.entity.test.City;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.*;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisdemoApplicationTests {


    @Resource
    private RedisTemplate<String, Object> template;

    @Autowired
    private UserMapper  userMapper;

    @Resource
    RedisDao redisDao;


    /**
     * a example for multiset of RedisTemplate
     */
    @Test
    public void multisetTest() {
        RedisSerializer redisSerializer = new StringRedisSerializer();
        template.setKeySerializer(redisSerializer);
        ValueOperations<String, Object> ops = template.opsForValue();
        Map map = new HashMap();
        map.put("hubei", new City("wuhan", "武汉"));
        map.put("chongqin", new City("chongqin", "重庆"));
        map.put("sichuan", new City("sichuan", "成都"));
        ops.multiSet(map);
        City city = (City) ops.get("city");
        System.out.println(city.toString());
    }

    /**
     * example for list (lset ,lget)
     * lset ->  rightpush
     */
    @Test
    public void test() {
        boolean lSet1 = redisDao.lSet("province", "hubei");
        System.out.println(lSet1);
        boolean lSet2 = redisDao.lSet("province", "sichuan");
        System.out.println(lSet2);
        boolean lSet3 = redisDao.lSet("province", "jiangxi");
        System.out.println(lSet3);
        List<Object> provinces = redisDao.lGet("province", 0, -1);
        System.out.println(provinces.toString());
    }

    /**
     * example for list (get List size)
     */
    @Test
    public void test1() {
        long listSize = redisDao.lGetListSize("province");
        System.out.println(listSize);
        long size2 = redisDao.lGetListSize("hubei");
        System.out.println(size2);
    }

    /**
     * example for map (put content into hash)
     */
    @Test
    public void test2() {
        boolean hset = redisDao.hset("couple", "jiangwei", "yingying");
        System.out.println(hset);
    }

    /**
     * example for map (if key exits/ get )
     */
    @Test
    public void test3() {
//        redisUtil.hset("provinces", "hubei", "wuhan");
//        redisUtil.hset("provinces", "jiangxi", "nanchang");
//        redisUtil.hset("provinces", "guangdong", "shenzhen");
//        redisUtil.hdel("provinces","guangdong");
        System.out.println(redisDao.hasKey("provinces"));
        System.out.println(redisDao.hmget("provinces"));
    }

    /**
     * example for set (set one or many  )
     */
    @Test
    public void test4() {
        long testSet = redisDao.sSet("testSet", new String[]{"set1", "set2", "set3", "set4", "set5", "set6", "set7", "set8"});
        redisDao.setRemove("testSet","set2");
        System.out.println(redisDao.sGet("testSet"));
    }

    /**
     * example for map(remove this key  and return a randmom value  )
     */
    @Test
    public void test5() {
        System.out.println(redisDao.randomRemove("testSet"));
        System.out.println(redisDao.sGet("testSet"));
    }

    @Test
    public void test6() throws IOException {
        File file = new File("C:\\Users\\jiangwei\\Desktop\\allUid.csv");
        BufferedReader br = new BufferedReader(new FileReader(file));
        List<String> list = new ArrayList();
        String line;
        while ((line = br.readLine()) != null) {
            line = line.trim();
            list.add(line);
            redisDao.sSet("uid111",line);
        }
        br.close();
        System.out.println(list.size());

        File file2 = new File("C:\\Users\\jiangwei\\Desktop\\allUid2.csv");
        BufferedReader br2 = new BufferedReader(new FileReader(file2));
        List list2 = new ArrayList();
        String line2;
        while ((line2 = br2.readLine()) != null) {
            line2 = line2.trim();
            list2.add(line2);
            redisDao.sSet("uid222",line2);
        }
        br2.close();
        System.out.println(list2.size());




    }

    @Test
    public void test7() throws IOException {
        Set set = redisDao.difference("uid222", "uid111");
        Set<Object> sGet = redisDao.sGet("uid111");
        Set<Object> sGet2 = redisDao.sGet("uid222");
        Iterator it = set.iterator();
        List list = new ArrayList();
        while(it.hasNext()){
            list.add(it.next());
        }
        System.out.println(list.size());
        StringBuffer sb = new StringBuffer("insert into storedemo.t_member_source (uid,source ,addtime) values ");
        for (int i = 0; i <list.size() ; i++) {
            String uid = (String) list.get(i);
            sb.append(" ( "+uid+", '17102001' , '1542085992' ), ");
        }
        BufferedWriter bw = new BufferedWriter(new FileWriter("C:\\Users\\jiangwei\\Desktop\\sql2.txt"));
        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }
}
