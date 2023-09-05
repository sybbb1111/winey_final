package com.green.winey_final.detail.model;

import lombok.Data;

@Data
public class WineDetailVo {
    private Long productId;
    private String categoryNm;
    private int temp;
    private String countryNm;
    private String nmKor;
    private String nmEng;
    private int price;
    private int quantity;
    private String pic;

    private int promotion;
    private int beginner;

    private int alcohol;
    private int sweety;
    private int acidity;
    private int body;

}
