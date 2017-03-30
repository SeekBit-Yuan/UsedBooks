package com.seek.usedbooks.models;

import com.maxleap.MLObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by 一丝不狗 on 2017/3/24.
 */

public class Order {

    private String id;
    private int total;
    private Date createTime;
    private Address address;
    private User user;
    private String delivery;
    private String receiptType;
    private String receiptContent;
    private String receiptHeading;
    private String remarks;
    private String payMethod;
    private int orderStatus;
    private ArrayList<OrderProduct> orderProducts;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    public ArrayList<OrderProduct> getOrderProducts() {
        return orderProducts;
    }

    public void setOrderProducts(ArrayList<OrderProduct> orderProducts) {
        this.orderProducts = orderProducts;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

    public String getReceiptContent() {
        return receiptContent;
    }

    public void setReceiptContent(String receiptContent) {
        this.receiptContent = receiptContent;
    }

    public String getReceiptHeading() {
        return receiptHeading;
    }

    public void setReceiptHeading(String receiptHeading) {
        this.receiptHeading = receiptHeading;
    }

    public String getReceiptType() {
        return receiptType;
    }

    public void setReceiptType(String receiptType) {
        this.receiptType = receiptType;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public static Order from(MLObject object) {
        Order order = new Order();
        order.setId(object.getObjectId());
        order.setTotal(object.getInt("total"));
        order.setCreateTime(object.getCreatedAt());
        Address address = Address.from(object.getMLObject("address"));
        order.setAddress(address);
        List<MLObject> mlOrderProducts = object.getList("order_products");
        ArrayList<OrderProduct> orderProducts = new ArrayList<>();
        for (MLObject orderProduct : mlOrderProducts) {
            orderProducts.add(OrderProduct.from(orderProduct));
        }
        order.setOrderProducts(orderProducts);
        order.setDelivery(object.getString("delivery"));
        order.setReceiptType(object.getString("receipt_title"));
        order.setReceiptContent(object.getString("receipt_content"));
        order.setReceiptHeading(object.getString("receipt_info"));
        order.setRemarks(object.getString("remarks"));
        order.setPayMethod(object.getString("pay_method"));
        order.setOrderStatus(object.getInt("order_status"));
        return order;
    }
}