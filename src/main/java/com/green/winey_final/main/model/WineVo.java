package com.green.winey_final.main.model;

import lombok.Data;

@Data
public class WineVo {
    private Long productId;
    private String nmKor;
    private String nmEng;
    private int price;
    private String pic;
    private int promotion;
    private int beginner;
    private int sale;
    private int salePrice;
    private int saleYn;
}
