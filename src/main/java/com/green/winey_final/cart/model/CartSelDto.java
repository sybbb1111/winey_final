package com.green.winey_final.cart.model;

import lombok.Data;

@Data
public class CartSelDto {
    private int cartId; //카트pk
    private Long userId; //유저pk
    private int productId; //제품pk
    private String nmKor; //한국어 이름
    private String nmEng; //영어 이름
    private int price; //정가
    private int salePrice; //할인가
    private String pic; //사진
    private int quantity; //수량
}
