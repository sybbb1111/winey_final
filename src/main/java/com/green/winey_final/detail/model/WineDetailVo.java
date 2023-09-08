package com.green.winey_final.detail.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WineDetailVo {
    private Long productId;
    private String nmKor;
    private String nmEng;
    private Long temp;
    private Integer price;
    private String pic;
    private Integer promotion;
    private Integer beginner;
    private Integer alcohol;
    private Integer quantity;
    private String categoryNm;
    private String countryNm;
    private Long sweety;
    private Long acidity;
    private Long body;
}
