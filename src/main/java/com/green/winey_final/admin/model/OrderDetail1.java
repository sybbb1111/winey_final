package com.green.winey_final.admin.model;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDetail1 {
    private Long orderId;
    private String orderDate;
    private String email;
    private String nmKor; //와인한글이름
    private int salePrice; //와인개별금액
    private int quantity; //와인개별수량

    @QueryProjection
    public OrderDetail1(Long orderId, String orderDate, String email, String nmKor, int salePrice, int quantity) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.email = email;
        this.nmKor = nmKor;
        this.salePrice = salePrice;
        this.quantity = quantity;
    }
}
