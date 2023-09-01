package com.green.winey_final.search.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
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
