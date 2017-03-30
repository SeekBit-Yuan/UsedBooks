/**
 * Copyright (c) 2015-present, MaxLeap.
 * All rights reserved.
 * ----
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.seek.usedbooks.models;

import com.maxleap.MLFile;
import com.maxleap.MLObject;
import com.maxleap.MLRelation;

public class ProductType {
    private String id;
    private String title;
    private String icon;
    private MLRelation products;
    private boolean recommend;
    private boolean onSales;

    public ProductType(MLObject object) {
        this.setId(object.getObjectId());
        this.setTitle(object.getString("title"));

        if (object.has("iconFile")) {
            MLFile file = object.getMLFile("iconFile");
            this.setIcon(file.getUrl());
        }

        this.setProducts(object.getRelation("products"));
        this.setRecommend(object.getBoolean("recommend"));
        this.setOnSales(object.getBoolean("on_sales"));
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isRecommend() {
        return recommend;
    }

    public void setRecommend(boolean recommend) {
        this.recommend = recommend;
    }

    public MLRelation getProducts() {
        return products;
    }

    public void setProducts(MLRelation products) {
        this.products = products;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public boolean isOnSales() {
        return onSales;
    }

    public void setOnSales(boolean onSales) {
        this.onSales = onSales;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ProductType{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", icon='" + icon + '\'' +
                ", products=" + products +
                ", recommend=" + recommend +
                ", onSales=" + onSales +
                '}';
    }
}
