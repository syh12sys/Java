package com.example.demo.dto;

import java.util.Date;

public class OrderDTO {
    // 订单创建时间
    private Date createAt;

    // 商品编号
    private String commodityId;

    // 订单编号
    private String orderId;

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
