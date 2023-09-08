package com.green.winey_final.admin.model;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDetail2 {
    private int quantity;
    private int totalPrice; //총결제금액
    private int payment;
    private String storeNm; //픽업지점
    private String pickUpDate; //픽업날짜
    private String pickUpTime; //픽업시간
    private int orderStatus; //주문상태

    @QueryProjection
    public OrderDetail2(int quantity, int totalPrice, int payment, String storeNm, String pickUpDate, String pickUpTime, int orderStatus) {
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.payment = payment;
        this.storeNm = storeNm;
        this.pickUpDate = pickUpDate;
        this.pickUpTime = pickUpTime;
        this.orderStatus = orderStatus;
    }
}
