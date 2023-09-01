package com.green.winey_final.main2.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WineVo2 {
    private Long productId;
    private String nmKor;
    private String nmEng;
    private int price;
    private String pic;
    private int promotion;
    private int beginner;
    private Long sale;
    private Long salePrice;
    private Long saleYn;
}
