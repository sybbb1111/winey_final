package com.green.winey_final.admin.model;

import com.querydsl.core.annotations.QueryProjection;
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

    @QueryProjection
    public ProductSaleVo(Long productId, String nmKor, int price, int sale, int salePrice, int promotion, int beginner, int quantity, int saleYn) {
        this.productId = productId;
        this.nmKor = nmKor;
        this.price = price;
        this.sale = sale;
        this.salePrice = salePrice;
        this.promotion = promotion;
        this.beginner = beginner;
        this.quantity = quantity;
        this.saleYn = saleYn;
    }
}
