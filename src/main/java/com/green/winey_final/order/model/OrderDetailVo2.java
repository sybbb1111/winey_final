package com.green.winey_final.order.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class OrderDetailVo2 {
    private LocalDateTime orderDate;
    private Long payment;
    private LocalDateTime pickupTime;
    private Long orderStatus;
    private Long totalOrderPrice;
    private String storeNm;

}
