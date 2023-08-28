package com.green.winey_final.main.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
public class WineTotalVo {
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
    private int saleYn;
}
