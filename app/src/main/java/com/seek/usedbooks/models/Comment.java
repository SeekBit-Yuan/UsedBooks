package com.seek.usedbooks.models;

import com.maxleap.MLObject;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by 一丝不狗 on 2017/3/24.
 */

public class Comment implements Serializable {
    private int score;
    private String content;
    private User user;
    private Product product;
    private Date updateAt;

    public Comment() {

    }

    public Comment(MLObject object) {
        this.score = object.getInt("score");
        this.content = object.getString("content");
        if (object.has("user") && object.getMLUser("user") != null) {
            this.user = new User(object.getMLUser("user"));
        }
        this.product = new Product(object.getMLObject("product"));
        this.updateAt = object.getUpdatedAt();
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "score=" + score +
                ", content='" + content + '\'' +
                ", user=" + user +
                ", product=" + product +
                '}';
    }
}
