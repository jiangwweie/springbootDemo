package com.jiangwei.entity.security;

import java.io.Serializable;
import java.util.List;

public class UrlConfig  implements Serializable {
    private Integer id;

    private String url;

    private String roles;

    private List<Role> roleList ;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles == null ? null : roles.trim();
    }
}