package com.example.springboot.entity;

import java.io.Serializable;

public class Userrole implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private Long uid;

    private Long rid;
    //角色ID
    private Long[] rids;
    //角色名称
    private String name;

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

    public Long[] getRids() {
        return rids;
    }

    public void setRids(Long[] rids) {
        this.rids = rids;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
