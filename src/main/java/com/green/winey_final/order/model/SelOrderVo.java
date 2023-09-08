package com.green.winey_final.order.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
public class SelOrderVo {
    private String orderDate;
    private Long orderId;
    private Long userId;
    private String nmKor;
    private Integer payment;
    private Integer totalOrderPrice;
    private String nm;
    private LocalDateTime pickupTime;
    private Integer orderStatus;
    private Long count;

}
