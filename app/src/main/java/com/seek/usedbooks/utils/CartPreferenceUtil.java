/**
 * Copyright (c) 2015-present, MaxLeap.
 * All rights reserved.
 * ----
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.seek.usedbooks.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.seek.usedbooks.models.ProductData;

import java.util.ArrayList;
import java.util.List;

public class CartPreferenceUtil {

    public static final String DATA = "cart";
    public static final String KEY = "items";
    private static CartPreferenceUtil cartPreferenceUtil;
    private Context context;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private static Gson GSON = new Gson();

    private CartPreferenceUtil(Context context) {
        this.context = context;

        preferences = context.getSharedPreferences(DATA, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public static CartPreferenceUtil getComplexPreferences(Context context) {

        if (cartPreferenceUtil == null) {
            cartPreferenceUtil = new CartPreferenceUtil(context);
        }

        return cartPreferenceUtil;
    }

    private void putObject(String key, Object object) {
        if(object == null){
            throw new IllegalArgumentException("object is null");
        }

        if(key.equals("") || key == null){
            throw new IllegalArgumentException("key is empty or null");
        }

        editor.putString(key, GSON.toJson(object));
        commit();
    }

    private void commit() {
        editor.commit();
    }

    private <T> T getObject(String key, Class<T> a) {

        String gson = preferences.getString(key, null);
        if (gson == null) {
            return null;
        } else {
            try{
                return GSON.fromJson(gson, a);
            } catch (Exception e) {
                throw new IllegalArgumentException("Object storaged with key " + key + " is instanceof other class");
            }
        }
    }

    public void drop() {
        editor.clear();
        editor.commit();
    }

    public List<ProductData> getProductData() {
        CartList cartList = getObject(KEY, CartList.class);
        if (cartList == null) {
            return null;
        }
        return cartList.getList();
    }

    public boolean update(ProductData productData) {
        boolean result = false;
        CartList cartList = getObject(KEY, CartList.class);
        if (cartList == null) {
            return false;
        }
        for (ProductData data : cartList.getList()) {
            if (data.equals(productData)) {
                data.setId(productData.getId());
                data.setCount(productData.getCount());
                data.setCustomInfo(productData.getCustomInfo());
                data.setImageUrl(productData.getImageUrl());
                data.setPrice(productData.getPrice());
                data.setTitle(productData.getTitle());
                result = true;
                break;
            }
        }
        putObject(KEY, cartList);
        return result;
    }

    public boolean delete(ProductData productData) {
        boolean result = false;
        CartList cartList = getObject(KEY, CartList.class);
        if (cartList == null) {
            return false;
        }

        for (ProductData data : cartList.getList()) {
            if (data.equals(productData)) {
                cartList.getList().remove(data);
                result = true;
                break;
            }
        }
        putObject(KEY, cartList);
        return result;
    }

    public boolean add(ProductData productData) {
        CartList cartList = getObject(KEY, CartList.class);
        if (cartList == null) {
            List<ProductData> list = new ArrayList<>();
            cartList = new CartList();
            cartList.setList(list);
        }
        if (!cartList.getList().contains(productData)) {
            cartList.getList().add(productData);
            putObject(KEY, cartList);
            return true;
        }
        return false;
    }


    public class CartList {
        private List<ProductData> list;

        public List<ProductData> getList() {
            return list;
        }

        public void setList(List<ProductData> list) {
            this.list = list;
        }
    }
}