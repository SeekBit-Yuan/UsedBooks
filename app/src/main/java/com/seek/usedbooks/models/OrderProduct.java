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

public class OrderProduct {

    private String id;
    private Product product;
    private int price;
    private int quantity;
    private String customInfo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getCustomInfo() {
        return customInfo;
    }

    public void setCustomInfo(String customInfo) {
        this.customInfo = customInfo;
    }

    public static OrderProduct from(MLObject object) {
        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setId(object.getObjectId());
        orderProduct.setPrice(object.getInt("price"));
        orderProduct.setQuantity(object.getInt("quantity"));
        MLObject product = object.getMLObject("product");
        orderProduct.setProduct(new Product(product));
        return orderProduct;
    }
}
