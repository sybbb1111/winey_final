package com.green.winey_final.payment.model;

import lombok.Data;

@Data
public class CartProductVo {
    private long productId;
    private int quantity;
    private int price;
}
