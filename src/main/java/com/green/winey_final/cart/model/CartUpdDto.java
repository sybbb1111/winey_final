package com.green.winey_final.cart.model;

import lombok.Data;

@Data
public class CartUpdDto {
    private long cartId; //카트pk
    private int quantity; //수량pk
}
