package com.green.winey_final.cart.model;

import lombok.Data;


@Data
public class CartVo {
    private int cartId; //카트 pk
    private int productId; //제품 pk
    private int quantity; //수량
    private String pic; //사진
    private int salePrice; //할인가
    private int price; //정가
    private String nmKor; //한국어 이름
    private String nmEng;//영어 이름
}
