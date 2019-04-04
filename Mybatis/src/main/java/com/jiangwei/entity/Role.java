package com.jiangwei.entity;

import java.io.Serializable;

public class Role  implements Serializable  {


    private Integer rid;

    private Integer uid;

    private String name;

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Role() {
    }

    public Role(String name) {
        this.rid=1;
        this.uid=1;
        this.name = name;
    }

    public Role(Integer rid, Integer uid, String name) {
        this.rid = rid;
        this.uid = uid;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Role{" +
                "rid=" + rid +
                ", uid=" + uid +
                ", name='" + name + '\'' +
                '}';
    }
}