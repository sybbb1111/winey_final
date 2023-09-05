package com.green.winey_final.order2.model;

import lombok.Data;

@Data
public class OrderDetailVo2 {
    private String orderDate;
    private int payment;
    private String pickupTime;
    private int orderStatus;
    private int totalOrderPrice;
    private String storeNm;

}
