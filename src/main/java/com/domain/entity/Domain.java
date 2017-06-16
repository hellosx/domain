package com.domain.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by sunxin(hysx8@sina.com) on 2017/6/6.
 */
public class Domain implements Serializable{

    private String domainName;

    private String domainLength;

    private Date createTime;

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public String getDomainLength() {
        return domainLength;
    }

    public void setDomainLength(String domainLength) {
        this.domainLength = domainLength;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
