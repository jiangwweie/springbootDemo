package com.jiangwei.test.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiangwei.entity.test.City;

import java.io.IOException;
import java.util.*;

/**
 * describe:
 *
 * @author lalio
 * @email jiangwei.wh@outlook.com
 * @date 2018/11/12
 */
public class JacksonTest {

    public static void main(String[] args) throws IOException {

        test2();


    }

    private static void test2() {
        List list = new ArrayList();
        Map map1 = new HashMap<>();
        Map map2 = new HashMap<>();
        Map map3 = new HashMap<>();
        map1.put(1, "zahngsan");
        map2.put(1, "yahngsan");
        map3.put(1, "xahngsan");
        list.add(map1);
        list.add(map1);
        list.add(map1);
        list.add(map2);
        list.add(map3);

        Set set = new HashSet(list);
        System.out.println(list.toString());
        System.out.println(set.toString());


    }

    private static void test1() {
        HashMap map = new HashMap();
        map.put(1, "张三");
        map.put(1, "李四");
        System.out.println(map.toString());
    }

    /**
     * objectMapper.readValue   将json字符串转换为pojo
     * objectMapper.writeValueAsString  将pojo转为json字符串
     *
     * @throws IOException
     */
    public static void test() throws IOException {
        String s = "{\"cityId\":\"1\", \"cityName\":\"武汉\",\"cityIntroduce\":\"wuhan\"}";
        ObjectMapper objectMapper = new ObjectMapper();
        City city = objectMapper.readValue(s, City.class);
        System.out.println(city.toString());

        String valueAsString = objectMapper.writeValueAsString(city);

        System.out.println(valueAsString.toString());
    }


}
