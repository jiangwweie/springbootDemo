package com.jiangwei.test.algorithm;

import com.jiangwei.dao.redis.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class Algorithm01 {

    @Autowired
    RedisUtil redisUtil;

    @Test
    public void test1() {
        Set set = new HashSet<>();
        int n;
        long begin = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            n = (int) (Math.random() * 10 + i * 10);
            set.add(n);
        }
        long end = System.currentTimeMillis();
        System.out.println("this costs " + (end - begin) + "  mills");
        System.out.println(set.size());
        Object[] objects = set.toArray();
        redisUtil.sSet("10000nums", objects);

    }

    @Test
    public void test01() {
        Set<Object> set = redisUtil.sGet("10000nums");
        Iterator it = set.iterator();
        int[] array = new int[set.size()];
        int x = 0;
        while (it.hasNext()) {

            array[x] = (int) it.next();
            x++;
        }
        System.out.println(array.length);
        method1(array);

    }

    @Test
    public void test02() {
        Set<Object> set = redisUtil.sGet("10000nums");
        Iterator it = set.iterator();
        int[] array = new int[set.size()];
        int x = 0;
        while (it.hasNext()) {

            array[x] = (int) it.next();
            x++;
        }
        System.out.println(array.length);
        method2(array);

    }

    private void method1(int[] array) {
        long begin1 = System.currentTimeMillis();

        for (int m = 0; m < array.length; m++) {
            for (int n = m + 1; n < array.length - 1; n++) {
                int temp;
                if (array[m] > array[n]) {
                    temp = array[m];
                    array[m] = array[n];
                    array[n] = temp;
                }
            }
        }
        long end1 = System.currentTimeMillis();
        System.out.println(array.length);
        System.out.println("花费时间" + (end1 - begin1));


    }

    private void method2(int[] array) {
        long begin1 = System.currentTimeMillis();

        for (int m = 0; m < 5000; m++) {
            for (int n = m + 1; n < 5000 - 1; n++) {
                int temp;
                if (array[m] > array[n]) {
                    temp = array[m];
                    array[m] = array[n];
                    array[n] = temp;
                }
            }
        }
        long end1 = System.currentTimeMillis();
        System.out.println(array.length);
//        System.out.println(array[6358]);
        System.out.println("花费时间" + (end1 - begin1));


    }
}
