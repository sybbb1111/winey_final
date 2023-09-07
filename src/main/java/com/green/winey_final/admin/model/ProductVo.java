package com.green.winey_final.admin.model;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
//@AllArgsConstructor
public class ProductVo {
    private Long productId;
    private String nmKor;
    private int price;
    private int promotion;
    private int beginner;
    private int quantity;
    private int sale;
    private int salePrice;

    @QueryProjection
    public ProductVo(Long productId, String nmKor, int price, int promotion, int beginner, int quantity, int sale, int salePrice) {
        this.productId = productId;
        this.nmKor = nmKor;
        this.price = price;
        this.promotion = promotion;
        this.beginner = beginner;
        this.quantity = quantity;
        this.sale = sale;
        this.salePrice = salePrice;
    }
}
