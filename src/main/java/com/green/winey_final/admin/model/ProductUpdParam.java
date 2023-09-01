package com.green.winey_final.admin.model;

import lombok.Data;

import java.util.List;

@Data
public class ProductUpdParam {
    private int productId;
    private String nmKor;
    private String nmEng;
    private int price; //가격
    private int promotion; //
    private int beginner; //입문자 추천
    private int alcohol; //도수
    private int quantity; // 재고

    private int country; //원산지

    private int sweety; //당도
    private int acidity; //산도
    private int body; //바디

    private int category; //레드(1) 화이트(2) 스파클링(3) 기타(4) //

    private List<Integer> aroma; //향

    private int sale; //할인률
    private int salePrice; //할인가격
    private String startSale; //할인 시작일
    private String endSale; //할인 종료일
//    private String saleDate;
    private int saleYn; //할인여부

    //음식페어링
    private List<Integer> smallCategoryId; //1~12번까지 있음
}
