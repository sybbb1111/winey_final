package com.green.winey_final.detail.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WineDetailVo {
    private Long productId;
    private String nmKor;
    private String nmEng;
    private Long price;
    private String pic;
    private Long promotion;
    private Long beginner;
    private Long alcohol;
    private Long quantity;
    private String categoryNm;
    private Long temp;
    private String countryNm;
    private Long sweety;
    private Long acidity;
    private Long body;
}
