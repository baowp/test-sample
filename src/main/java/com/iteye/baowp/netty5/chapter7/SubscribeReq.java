package com.iteye.baowp.netty5.chapter7;

import java.io.Serializable;

/**
 * Created by baowp on 15-1-17.
 */
public class SubscribeReq implements Serializable {

    private int subReqId;
    private String userName;
    private String productName;
    private String phoneNumber;
    private String address;

    public int getSubReqId() {
        return subReqId;
    }

    public void setSubReqId(int subReqId) {
        this.subReqId = subReqId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String toString() {
        return "SubscribeReq  subReqId=" + subReqId + ", userName=" + userName + ", productName=" + productName
                + ", phoneNumber=" + phoneNumber + ", address=" + address + "]";
    }
}
