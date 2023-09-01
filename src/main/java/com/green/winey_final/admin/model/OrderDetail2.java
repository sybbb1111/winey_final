package com.green.winey_final.admin.model;

import lombok.Getter;

@Getter
public class OrderDetail2 {
    private int quantity;
    private int totalPrice; //총결제금액
    private int payment;
    private String storeNm; //픽업지점
    private String pickUpDate; //픽업날짜
    private String pickUpTime; //픽업시간
    private int orderStatus; //주문상태
}
