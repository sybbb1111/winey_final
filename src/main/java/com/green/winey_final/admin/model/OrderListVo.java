package com.green.winey_final.admin.model;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class OrderListVo {
    private Long orderId;
    private String orderDate;
    private String email;
    private String nmKor;
    private int quantity;
    private int salePrice;
    private int totalPrice;
    private int payment;
    private String pickUpStore;
    private int orderStatus;
    private int count;

    @QueryProjection
    public OrderListVo(Long orderId, String orderDate, String email, String nmKor, int quantity, int salePrice, int totalPrice, int payment, String pickUpStore, int orderStatus, int count) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.email = email;
        this.nmKor = nmKor;
        this.quantity = quantity;
        this.salePrice = salePrice;
        this.totalPrice = totalPrice;
        this.payment = payment;
        this.pickUpStore = pickUpStore;
        this.orderStatus = orderStatus;
        this.count = count;
    }
}
