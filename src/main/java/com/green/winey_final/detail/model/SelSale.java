package com.green.winey_final.detail.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SelSale {
    private Long productId;
    private Integer sale;
    private Integer salePrice;
}
