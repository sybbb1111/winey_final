package com.green.winey_final.order.model;

import lombok.Data;

@Data
public class OrderEntity2 {
    private String orderDate;
    private Long orderId;
    private Long userId;
    private String nmKor;
    private int payment;
    private int totalOrderPrice;
    private String storeNm;
    private String pickupTime;
    private int orderStatus;
    private int count;


}
