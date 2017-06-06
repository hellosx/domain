package com.domain.entity;

import java.io.Serializable;

/**
 * Created by sunxin(hysx8@sina.com) on 2017/6/6.
 */
public class Domain implements Serializable{

    private int avail;

    private String name;

    private String tld;

    public int getAvail() {
        return avail;
    }

    public void setAvail(int avail) {
        this.avail = avail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTld() {
        return tld;
    }

    public void setTld(String tld) {
        this.tld = tld;
    }
}
