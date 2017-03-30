/**
 * Copyright (c) 2015-present, MaxLeap.
 * All rights reserved.
 * ----
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.seek.usedbooks.models;

import com.maxleap.MLObject;

import org.json.JSONObject;

import java.util.List;

public class Product {
    private String id;
    private String title;
    private List<String> icons;
    private int price;
    private int originalPrice;
    private String intro;
    private List<String> services;
    private String info;
    private String customInfo1;
    private String customInfo2;
    private String customInfo3;
    private JSONObject detail;
    private int quantity;

    public Product() {
    }

    public Product(String id) {
        this.id = id;
    }

    public Product(MLObject object) {
        this.id = object.getObjectId();
        this.title = object.getString("title");
        this.icons = object.getList("icons");
        this.price = object.getInt("price");
        this.originalPrice = object.getInt("original_price");
        this.intro = object.getString("intro");
        this.services = object.getList("services");
        this.info = object.getString("info");
        this.customInfo1 = object.getString("custom_info1");
        this.customInfo2 = object.getString("custom_info2");
        this.customInfo3 = object.getString("custom_info3");
        this.detail = object.getJSONObject("detail");
        this.quantity = object.getInt("quantity");
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getIcons() {
        return icons;
    }

    public void setIcons(List<String> icons) {
        this.icons = icons;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public List<String> getServices() {
        return services;
    }

    public void setServices(List<String> services) {
        this.services = services;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getCustomInfo1() {
        return customInfo1;
    }

    public void setCustomInfo1(String customInfo1) {
        this.customInfo1 = customInfo1;
    }

    public String getCustomInfo2() {
        return customInfo2;
    }

    public void setCustomInfo2(String customInfo2) {
        this.customInfo2 = customInfo2;
    }

    public String getCustomInfo3() {
        return customInfo3;
    }

    public void setCustomInfo3(String customInfo3) {
        this.customInfo3 = customInfo3;
    }


    public JSONObject getDetail() {
        return detail;
    }

    public void setDetail(JSONObject detail) {
        this.detail = detail;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(int originalPrice) {
        this.originalPrice = originalPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", icons=" + icons +
                ", price=" + price +
                ", originalPrice=" + originalPrice +
                ", intro='" + intro + '\'' +
                ", services=" + services +
                ", info=" + info +
                ", customInfo1=" + customInfo1 +
                ", customInfo2=" + customInfo2 +
                ", customInfo3=" + customInfo3 +
                ", detail=" + detail +
                ", quantity=" + quantity +
                '}';
    }
}
