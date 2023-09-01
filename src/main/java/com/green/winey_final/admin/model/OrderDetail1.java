package com.green.winey_final.admin.model;

import lombok.Getter;

@Getter
public class OrderDetail1 {
    private Long orderId;
    private String orderDate;
    private String email;
    private String nmKor; //와인한글이름
    private int salePrice; //와인개별금액
    private int quantity; //와인개별수량
}
