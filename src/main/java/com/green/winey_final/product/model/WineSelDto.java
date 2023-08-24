package com.green.winey_final.product.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class WineSelDto {
    private Long productId;
    private Long saleYn; //이거 확인해봐라
    private int startIdx;
    private int size;
}
