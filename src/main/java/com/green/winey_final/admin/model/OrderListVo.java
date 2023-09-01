package com.green.winey_final.admin.model;

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
}
