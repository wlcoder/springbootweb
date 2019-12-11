package com.example.springboot.entity;

import java.io.Serializable;

public class Rolepermission implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private Long rid;

    private Long pid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getRid() {
        return rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }
}
