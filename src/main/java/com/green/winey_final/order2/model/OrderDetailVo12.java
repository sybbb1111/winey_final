package com.green.winey_final.order2.model;

import lombok.Data;

@Data
//@Getter
//@AllArgsConstructor
public class OrderDetailVo12 {
    private int orderDetailId;
    private String nmKor;
    private String nmEng;
    private int salePrice;
    private int quantity;
    private String pic;
    private String price;
    private int productId;

    private int reviewYn;


}
