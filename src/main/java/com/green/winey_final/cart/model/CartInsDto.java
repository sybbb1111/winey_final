package com.green.winey_final.cart.model;

import lombok.Data;

@Data
public class CartInsDto {
    private int quantity; //수량
    private long productId; //제품pk
}
