package com.iteye.baowp.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by baowp on 2015/7/12.
 */
@XmlRootElement(name="xml")
//@XmlAccessorType(XmlAccessType.FIELD)
public class APIResponce {

    private Integer userid;
    private Integer uuid;
    private UserFavour favour;

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getUuid() {
        return uuid;
    }

    public void setUuid(Integer uuid) {
        this.uuid = uuid;
    }

    public UserFavour getFavour() {
        return favour;
    }

    public void setFavour(UserFavour favour) {
        this.favour = favour;
    }
}