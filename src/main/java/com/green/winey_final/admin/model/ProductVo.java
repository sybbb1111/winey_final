package com.green.winey_final.admin.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductVo {
    private Long productId;
    private String nmKor;
    private int price;
    private int promotion;
    private int beginner;
    private int quantity;
    private int sale;
    private int salePrice;
}
