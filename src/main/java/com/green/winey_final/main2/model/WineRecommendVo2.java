package com.green.winey_final.main2.model;

import lombok.Data;

@Data
public class WineRecommendVo2 {
//    private Long userId;
    private Long productId;
    private Long categoryId;
    private Long featureId;
    private Long countryId;
    private Long aromaId;
    private String nmKor;
    private String nmEng;
    private int price;
    private int quantity;
    private String pic;
    private int promotion;
    private int beginner;
    private int alcohol;
    private int sale;
    private int salePrice;

}
