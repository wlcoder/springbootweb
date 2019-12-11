package com.example.springboot.entity;

import java.io.Serializable;

public class Userrole implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private Long uid;

    private Long rid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Long getRid() {
        return rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }
}
