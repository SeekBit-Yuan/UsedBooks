package com.seek.usedbooks.models;

/**
 * Created by 一丝不狗 on 2017/3/24.
 */

import com.maxleap.MLRelation;
import com.maxleap.MLUser;

public class User {
    private String id;
    private String username;
    private String icon;
    private String nickname;
    private MLRelation favorites;

    public User() {
    }

    public User(MLUser object) {
        this.id = object.getObjectId();
        this.username = object.getUserName();
        this.icon = object.getString("icon");
        this.nickname = object.getString("nickname");
        this.favorites = object.getRelation("favorites");
        favorites.setTargetClass("Product");
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public MLRelation getFavorites() {
        return favorites;
    }

    public void setFavorites(MLRelation favorites) {
        this.favorites = favorites;
    }

    public static User from(MLUser mlUser) {
        User user = new User();
        user.setId(mlUser.getObjectId());
        user.setUsername(mlUser.getUserName());
        user.setIcon(mlUser.getString("icon"));
        user.setNickname(mlUser.getString("nickname"));
        user.setFavorites(mlUser.getRelation("favorites"));
        return user;
    }
}