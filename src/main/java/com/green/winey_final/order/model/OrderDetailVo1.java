package com.green.winey_final.order.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderDetailVo1 {
    private Long orderDetailId;
    private String nmKor;
    private String nmEng;
    private Long salePrice;
    private Long quantity;
    private String pic;
    private Long price;
    private Long productId;
    private Long reviewYn;


}
