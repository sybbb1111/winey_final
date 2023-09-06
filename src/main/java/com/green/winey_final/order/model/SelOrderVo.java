package com.green.winey_final.order.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
public class SelOrderVo {
    private LocalDateTime orderTime;
    private Long orderId;
    private Long userId;
    private String nmKor;
    private Long payment;
    private Long totalOrderPrice;
    private String nm;
    private LocalDateTime pickupTime;
    private Long orderStatus;
    private Long count;

}
