package com.example.springboot.entity;

import java.io.Serializable;

public class Permission implements Serializable {

    private static final long serialVersionUID = 1L;
    @ExcelColumn(value = "id", col = 1)
    private Long id;
    @ExcelColumn(value = "权限名称", col = 2)
    private String name;
    @ExcelColumn(value = "权限URL", col = 3)
    private String url;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }
}
