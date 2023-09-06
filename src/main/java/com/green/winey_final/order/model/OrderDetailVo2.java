package com.green.winey_final.order.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class OrderDetailVo2 {
    private LocalDateTime orderDate;
    private Integer payment;
    private LocalDateTime pickupTime;
    private Integer orderStatus;
    private Integer totalOrderPrice;
    private String storeNm;
}
