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

public class Banner {

    private String url;
    private Product product;
    private int status;
    private int priority;

    public Banner(MLObject object) {
        this.setUrl(object.getString("url"));
        this.setStatus(object.getBoolean("status") ? 1 : 0);
        this.setPriority(object.getInt("priority"));
        Product product = new Product();
        product.setId(object.getMLObject("product").getObjectId());
        this.setProduct(product);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "Banner{" +
                "url='" + url + '\'' +
                ", product=" + product +
                ", status=" + status +
                ", priority=" + priority +
                '}';
    }
}
