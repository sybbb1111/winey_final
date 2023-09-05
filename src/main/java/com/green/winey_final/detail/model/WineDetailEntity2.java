package com.green.winey_final.detail.model;

import lombok.Data;

@Data
public class WineDetailEntity2 {
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
    private String createdAt;
    private String updatedAt;
    private int promotion;
    private int beginner;

}
