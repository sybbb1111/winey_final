package com.green.winey_final.cart.model;

import lombok.Data;

@Data
public class CartInsDto2 {
    private int cartId; //카트pk
    private int quantity; //수량
    private int productId; //제품pk
    private Long userId; //유저pk
}
