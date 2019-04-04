package com.jiangwei.sg;

import com.alibaba.druid.util.HttpClientUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.net.URLEncoder;

public class Test {
    public static void main(String[] args) {
        int[] ids = new int[]{1,2,3,4,5};
        JSONObject jo = new JSONObject();
        jo.put("ids",ids);
        String s = JSON.toJSONString(jo);
        HttpClientUtils.post("http://localhost:9999/test1",URLEncoder.encode(s),3000);
        System.out.println(s);
    }
}
