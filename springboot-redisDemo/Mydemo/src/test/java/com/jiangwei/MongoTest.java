package com.jiangwei;

import com.jiangwei.mongo.dao.DemoDao;
import com.jiangwei.mongo.entity.Demo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * describe:
 *
 * @author lalio
 * @email jiangwei.wh@outlook.com
 * @date 2018/11/14
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MongoTest {
    @Autowired
    DemoDao demoDao ;

    @Test
    public void test1(){
        List<Demo> demos = demoDao.findByAge(30);
        System.out.println(demos.toString());
    }

    @Test
    public void test2(){
        Demo demo = demoDao.findByName("张三");
        System.out.println(demo.toString());
    }

    @Test
    public void test3( ) {
        Demo demo = new Demo();
        demo.setName("yingying");
        demo.setAge(25);
        demoDao.insert(demo);

        Demo demo1 = demoDao.findByName("yingying");
        System.out.println(demo1.toString());
    }
}
