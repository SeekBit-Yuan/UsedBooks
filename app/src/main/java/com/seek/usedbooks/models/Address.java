package com.seek.usedbooks.models;

import com.maxleap.MLObject;

import java.io.Serializable;

/**
 * Created by 一丝不狗 on 2017/3/24.
 */

public class Address implements Serializable {
    private String id;
    private User user;
    private String name;
    private String street;
    private String tel;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public static Address from(MLObject object) {
        Address address = new Address();
        address.setId(object.getObjectId());
        address.setName(object.getString("name"));
        address.setTel(object.getString("tel"));
        address.setStreet(object.getString("street"));
        return address;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", street='" + street + '\'' +
                ", tel='" + tel + '\'' +
                '}';
    }
}
