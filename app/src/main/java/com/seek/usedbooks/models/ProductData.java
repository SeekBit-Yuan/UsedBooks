/**
 * Copyright (c) 2015-present, MaxLeap.
 * All rights reserved.
 * ----
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.seek.usedbooks.models;

import java.io.Serializable;

public class ProductData implements Serializable {

    // product id, not order product id
    private String id;
    private String imageUrl;
    private String title;
    private int price;
    private int count;
    private String customInfo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getCustomInfo() {
        if (customInfo == null) {
            return "";
        }
        return customInfo;
    }

    public void setCustomInfo(String customInfo) {
        this.customInfo = customInfo;
    }

    @Override
    public String toString() {
        return "ProductData{" +
                "id='" + id + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", count=" + count +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductData that = (ProductData) o;

        if (!id.equals(that.id)) return false;
        return !(customInfo != null ? !customInfo.equals(that.customInfo) : that.customInfo != null);

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (customInfo != null ? customInfo.hashCode() : 0);
        return result;
    }
}
