package com.jiangwei.entity.websocket;

/**
 * describe:
 *
 * @author lalio
 * @email jiangwei.wh@outlook.com
 * @date 2018/11/14
 */
public class ResponseMessage {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ResponseMessage{" +
                "name='" + name + '\'' +
                '}';
    }
}
