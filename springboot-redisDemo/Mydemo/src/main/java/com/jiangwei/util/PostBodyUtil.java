package com.jiangwei.util;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * describe:
 *
 * @author lalio
 * @email jiangwei.wh@outlook.com
 * @date 2018/11/12
 */
public class PostBodyUtil {

    public JSONObject getRequestBody(HttpServletRequest request) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        StringBuilder content = new StringBuilder();
        byte[] b = new byte[1024];
        int length = -1;
        //每次读取1024个字节，并缓存到b中 如果到了流末尾  则read方法会返回-1
        while ((length = inputStream.read(b)) > 0) {
            content.append(new String(b, 0, length));
        }
        String inputDataStr = content.toString();// 内容
        if (inputDataStr.contains("\n")) {
            inputDataStr = inputDataStr.replaceAll("\n", "");
        }
        JSONObject inputDataJSON = JSONObject.parseObject(inputDataStr);
        return inputDataJSON;
    }

}
