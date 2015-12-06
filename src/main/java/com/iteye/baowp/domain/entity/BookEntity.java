package com.iteye.baowp.domain.entity;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: baowp
 * Date: 11/27/13
 * Time: 2:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class BookEntity implements Serializable{

    private Long id;
    private String title;
    private Float price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }
}
