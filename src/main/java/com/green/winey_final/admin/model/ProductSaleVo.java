package com.green.winey_final.admin.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductSaleVo {
    private Long productId;
    private String nmKor;
    private int price;
    private int sale;
    private int salePrice;
    private int promotion;
    private int beginner;
    private int quantity;
    private int saleYn;
}
